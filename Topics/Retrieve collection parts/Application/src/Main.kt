data class Ship(val name: String, val ammunition: Int)

fun main() {
    val ships = readln().split(" ")
    val shipsList = ships.map { Ship(it.split("-")[0], it.split("-")[1].toInt()) }

    // write your code here
    println(
        mutableListOf(
            shipsList.takeWhile { it.name.contains("Wing") }.mapNotNull { it.ammunition }.average().toInt(),
            shipsList.dropWhile { it.name.contains("Wing") }.mapNotNull { it.ammunition }.average().toInt()
        ).takeWhile { it != 0 }
    )
}