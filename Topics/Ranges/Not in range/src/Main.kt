const val LOWER_BOUND = 1
const val UPPER_BOUND = 10

fun main() {
    val n = readln().toInt()
    println(n !in LOWER_BOUND..UPPER_BOUND)
}