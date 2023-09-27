package battleship

val SHIPS = mapOf(
    "Aircraft Carrier" to 5,
    "Battleship" to 4,
    "Submarine" to 3,
    "Cruiser" to 3,
    "Destroyer" to 2
)


fun main() {

    Field.display()


    for (ship in SHIPS) {
        Field.enterShip(ship)
        println()
        Field.display()
    }




}










object Field {
    val N = 10



    val field = Array(N) { Array(N) { '~' } }





    fun enterShip(ship: Map.Entry<String, Int>): Boolean  {

        println("\nEnter the coordinates of the ${ship.key} (${ship.value} cells):\n")

        out@while (true) {

            val (p0, p1) = readln().split(" ")

            // extract coordinates
            var x0 = p0[0] - 'A'
            var y0 = p0.substring(1).toInt() - 1
            var x1 = p1[0] - 'A'
            var y1 = p1.substring(1).toInt() - 1

            // swap, if needed
            if (x0 > x1) x0 = x1.also { x1 = x0 }
            if (y0 > y1) y0 = y1.also { y1 = y0 }

            // check if coordinates are valid
            if (x0 !in 0 until N ||
                y0 !in 0 until N ||
                x1 !in 0 until N ||
                y1 !in 0 until N ||
                (x0 != x1 && y1 != y0)
            ) {
                println("\nError! Wrong ship location! Try again:\n")
                continue@out
            }

            // check length
            if ((x0 == x1 && y1 - y0 + 1 != ship.value) ||
                (y0 == y1 && x1 - x0 + 1 != ship.value)
            ) {
                println("\nError! Wrong length of the ${ship.key}! Try again:\n")
                continue@out
            }

            // check if cells and their neighborhoods are free
            if (x0 == x1)  {
                for (y in y0..y1) {
                    if (!neighborhoodFree(x0, y)) {
                        println("\nError! You placed it too close to another one. Try again:\n")
                        continue@out
                    }
                }
            } else if (y0 == y1) {
                for (x in x0..x1) {
                    if (!neighborhoodFree(x, y0)) {
                        println("\nError! You placed it too close to another one. Try again:\n")
                        continue@out
                    }
                }
            }

            // write "~" into cells and their neighborhoods are free
            if (x0 == x1)  {
                for (y in y0..y1) {
                    field[x0][y] = 'O'
                }
            } else if (y0 == y1) {
                for (x in x0..x1) {
                    field[x][y0] = 'O'
                }
            }



            break
        }
        return true
    }


    fun neighborhoodFree(x: Int, y: Int): Boolean {
        for (i in -1..1) {
            for (j in -1..1) {
                if (x + i in 0 until N &&
                    y + j in 0 until N &&
                    field[x + i][y + j] != '~'
                ) {
                    //println("...... neighborhood is NOT free")
                    return false
                }
            }
        }
        return true
    }



    fun display() {
        println("  1 2 3 4 5 6 7 8 9 10")
        for (i in 0 until N) {
            print("${'A' + i} ")
            for (j in 0 until N) {
                print("${field[i][j]} ")
            }
            println()
        }
    }

}



