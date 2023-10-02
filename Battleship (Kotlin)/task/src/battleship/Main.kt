package battleship

const val SYMBOL_EMPTY = '~'
const val SYMBOL_SHIP = 'O'

val SHIPS = mapOf(
    "Aircraft Carrier" to 5,
    "Battleship" to 4,
    "Submarine" to 3,
    "Cruiser" to 3,
    "Destroyer" to 2
)

val PLAYERS  = listOf("Player 1", "Player 2")

fun main() {

    Field.placeShips()

    println("\nThe game starts!\n")



    main@while (true) {

        for (player in PLAYERS) {

            inner@while(true) {

                println("Press Enter and pass the move to another player")
                readln()

                val theOtherPlayer = PLAYERS.toMutableList().apply {remove(player)}.random()

                Field.displayFrontend(theOtherPlayer)
                println("-".repeat(21))
                Field.displayBackend(player)
                println("\n$player, it's your turn:\n")

                // read point coordinates
                val p: Pair<Int, Int> = readln().let { Pair(it[0] - 'A', it.substring(1).toInt() - 1 ) }

                // check if coordinates are valid
                if (p.first !in 0 until Field.N ||
                    p.second !in 0 until Field.N
                ) {
                    println("\nError! You entered the wrong coordinates! Try again:\n")
                    continue@inner
                }

                val thereIsAShip = Field.backend[theOtherPlayer]!![p.first][p.second] in setOf('O', 'X')

                Field.backend[theOtherPlayer]!![p.first][p.second] = if (thereIsAShip) 'X' else 'M'
                Field.frontend[theOtherPlayer]!![p.first][p.second] = if (thereIsAShip) 'X' else 'M'

                if (thereIsAShip) {
                    for (entry in Field.ledger[theOtherPlayer]!!) {
                        if (p in entry.value) {

                            val pSet = entry.value.toMutableSet()
                            pSet.remove(p)

                            Field.ledger[theOtherPlayer]!![entry.key] = pSet

                            if (Field.ledgerIsEmpty(theOtherPlayer)) {
                                println("\nYou sank the last ship. You won. Congratulations!\n")
                                break@inner
                            }
                            if (pSet.isEmpty()) {
                                println("\nYou sank a ship!\n")
                                continue@inner
                            }

                            println("\nYou hit a ship!\n")
                            break@inner
                        }

                    }
                }

                println("\nYou missed!\n")
                break@inner
            }
        }
    }
}










object Field {
    const val N = 10
    val backend  = mutableMapOf<String, Array<Array<Char>>>()
    val frontend = mutableMapOf<String, Array<Array<Char>>>()
    val ledger   = mutableMapOf<String, MutableMap<String, MutableSet<Pair<Int, Int>>>>()


    fun placeShips(): Boolean  {

        for (player in PLAYERS) {

            backend[player] = Array(N) { Array(N) { '~' } }
            frontend[player] = Array(N) { Array(N) { '~' } }
            println("$player, place your ships on the game field\n")
            displayBackend(player)

            ledger[player] = mutableMapOf()

            for (ship in SHIPS) {

                ledger[player]!![ship.key] = mutableSetOf()

                println("\nEnter the coordinates of the ${ship.key} (${ship.value} cells):\n")

                out@ while (true) {

                    // read endpoints of the ship
                    var (p0, p1) = readln().split(" ")
                        .let {
                            listOf(
                                Pair(it[0][0] - 'A', it[0].substring(1).toInt() - 1),
                                Pair(it[1][0] - 'A', it[1].substring(1).toInt() - 1)
                            )
                        }

                    // check if coordinates are valid
                    if (p0.first !in 0 until N ||
                        p0.second !in 0 until N ||
                        p1.first !in 0 until N ||
                        p1.second !in 0 until N ||
                        (p0.first != p1.first && p0.second != p1.second) ||
                        (p0.first == p1.first && p0.second == p1.second)
                    ) {
                        println("\nError! Wrong ship location! Try again:\n")
                        continue@out
                    }

                    // swap, if needed
                    if (p0.first > p1.first || p0.second > p1.second) {
                        p0 = p1.also { p1 = p0 }
                    }


                    // check length
                    if ((p0.first == p1.first && p1.second - p0.second + 1 != ship.value) ||
                        (p0.second == p1.second && p1.first - p0.first + 1 != ship.value)
                    ) {
                        println("\nError! Wrong length of the ${ship.key}! Try again:\n")
                        continue@out
                    }


                    // check if cells and their neighborhoods are free
                    if (p0.first == p1.first) {
                        for (y in p0.second..p1.second) {
                            if (!neighborhoodFree(player, p0.first, y)) {
                                println("\nError! You placed it too close to another one. Try again:\n")
                                continue@out
                            }
                        }
                    } else if (p0.second == p1.second) {
                        for (x in p0.first..p1.first) {
                            if (!neighborhoodFree(player, x, p0.second)) {
                                println("\nError! You placed it too close to another one. Try again:\n")
                                continue@out
                            }
                        }
                    }


                    // write SYMBOL_SHIP into cells
                    if (p0.first == p1.first) {
                        for (y in p0.second..p1.second) {
                            backend[player]?.get(p0.first)?.set(y, SYMBOL_SHIP)
                            ledger[player]!![ship.key]!!.add(Pair(p0.first, y))
                        }
                    } else if (p0.second == p1.second) {
                        for (x in p0.first..p1.first) {
                            backend[player]?.get(x)?.set(p0.second, SYMBOL_SHIP)
                            //ledger[player]?.get(ship.key)!!.add(Pair(x, p0.second))
                            ledger[player]!![ship.key]!!.add(Pair(x, p0.second))
                        }
                    }

                    break
                }
                println()
                displayBackend(player)

            }

            if (player != PLAYERS.last()) {
                println("\nPress Enter and pass the move to another player\n")
                readln()
            }
        }

        return true
    }


    private fun neighborhoodFree(player: String, x: Int, y: Int): Boolean {
        for (i in -1..1) {
            for (j in -1..1) {
                if (x + i in 0 until N &&
                    y + j in 0 until N &&
                    backend[player]?.get(x + i)?.get(y + j) != SYMBOL_EMPTY
                ) {
                    //println("...... neighborhood is NOT free")
                    return false
                }
            }
        }
        return true
    }



    fun displayFrontend(player: String) {
        display(player, true)
    }

    fun displayBackend(player: String) {
        display(player,false)
    }


    private fun display(player: String, invisible: Boolean = false) {
        println("  1 2 3 4 5 6 7 8 9 10")
        for (i in 0 until N) {
            print("${'A' + i} ")
            for (j in 0 until N) {
                print("${if (!invisible) backend[player]?.get(i)?.get(j) else frontend[player]?.get(i)?.get(j)} ")
            }
            println()
        }
    }


    fun ledgerIsEmpty(player: String): Boolean {
        for (entry in ledger[player]!!) {
            if (entry.value.isNotEmpty()) {
                return false
            }
        }
        return true
    }
}



