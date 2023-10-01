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


fun main() {
    Field.displayBackend()

    // setup ships
    for (ship in SHIPS) {
        Field.placeShip(ship)
        println()
        Field.displayBackend()
    }

    // play
    println("\nThe game starts!\n")
    Field.displayFrontend()
    println("\nTake a shot!\n")

    main@while (true) {
        // read point coordinates
        val p: Pair<Int, Int> = readln().let { Pair(it[0] - 'A', it.substring(1).toInt() - 1 ) }

        // check if coordinates are valid
        if (p.first !in 0 until Field.N ||
            p.second !in 0 until Field.N
        ) {
            println("\nError! You entered the wrong coordinates! Try again:\n")
            continue
        }

        println()


        val thereIsAShip = Field.backend[p.first][p.second] == 'O' || Field.backend[p.first][p.second] == 'X'


        Field.backend[p.first][p.second] = if (thereIsAShip) 'X' else 'M'
        Field.frontend[p.first][p.second] = if (thereIsAShip) 'X' else 'M'
        Field.displayFrontend()
        if (thereIsAShip) {
            for (entry in Field.ledger) {
                if (p in entry.value) {
                    entry.value.remove(p)
                    if (Field.ledgerIsEmpty()) {
                        println("\nYou sank the last ship. You won. Congratulations!\n")
                        break@main
                    }
                    if (entry.value.isEmpty()) {
                        println("\nYou sank a ship! Specify a new target:\n")
                        continue@main
                    }
                }
            }
        }
        println("\nYou ${if (thereIsAShip) "hit a ship" else "missed"}! Try again:\n")
        //Field.displayBackend()
    }
}










object Field {
    const val N = 10
    val backend = Array(N) { Array(N) { '~' } }
    val frontend = Array(N) { Array(N) { '~' } }
    val ledger = mutableMapOf<String, MutableSet<Pair<Int, Int>>>()



    fun placeShip(ship: Map.Entry<String, Int>): Boolean  {

        ledger[ship.key] = mutableSetOf()
        println("\nEnter the coordinates of the ${ship.key} (${ship.value} cells):\n")

        out@while (true) {

            // read endpoints of the ship
            var (p0, p1) = readln().split(" ")
                .let { listOf( Pair(it[0][0] - 'A', it[0].substring(1).toInt() - 1 ),
                               Pair(it[1][0] - 'A', it[1].substring(1).toInt() - 1 ) ) }

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
                p0 = p1 .also { p1 = p0 }
            }


            // check length
            if ((p0.first == p1.first  &&  p1.second - p0.second + 1 != ship.value) ||
                (p0.second == p1.second  &&  p1.first - p0.first + 1 != ship.value)
            ) {
                println("\nError! Wrong length of the ${ship.key}! Try again:\n")
                continue@out
            }


            // check if cells and their neighborhoods are free
            if (p0.first == p1.first)  {
                for (y in p0.second..p1.second) {
                    if (!neighborhoodFree(p0.first, y)) {
                        println("\nError! You placed it too close to another one. Try again:\n")
                        continue@out
                    }
                }
            } else if (p0.second == p1.second) {
                for (x in p0.first..p1.first) {
                    if (!neighborhoodFree(x, p0.second)) {
                        println("\nError! You placed it too close to another one. Try again:\n")
                        continue@out
                    }
                }
            }


            // write SYMBOL_SHIP into cells
            if (p0.first == p1.first)  {
                for (y in p0.second..p1.second) {
                    backend[p0.first][y] = SYMBOL_SHIP
                    ledger[ship.key]!!.add( Pair(p0.first, y) )
                }
            } else if (p0.second == p1.second) {
                for (x in p0.first..p1.first) {
                    backend[x][p0.second] = SYMBOL_SHIP
                    ledger[ship.key]!!.add( Pair(x, p0.second) )
                }
            }



            break
        }
        return true
    }


    private fun neighborhoodFree(x: Int, y: Int): Boolean {
        for (i in -1..1) {
            for (j in -1..1) {
                if (x + i in 0 until N &&
                    y + j in 0 until N &&
                    backend[x + i][y + j] != SYMBOL_EMPTY
                ) {
                    //println("...... neighborhood is NOT free")
                    return false
                }
            }
        }
        return true
    }



    fun displayFrontend() {
        display(true)
    }

    fun displayBackend() {
        display(false)
    }


    private fun display(invisible: Boolean = false) {
        println("  1 2 3 4 5 6 7 8 9 10")
        for (i in 0 until N) {
            print("${'A' + i} ")
            for (j in 0 until N) {
                print("${if (!invisible) backend[i][j] else frontend[i][j]} ")
            }
            println()
        }
    }


    fun ledgerIsEmpty(): Boolean {
        for (entry in ledger) {
            if (entry.value.isNotEmpty()) {
                return false
            }
        }
        return true
    }
}



