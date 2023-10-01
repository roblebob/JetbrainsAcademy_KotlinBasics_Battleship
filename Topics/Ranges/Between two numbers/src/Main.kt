fun main() {
    val n = readln().toInt()
    val n1 = readln().toInt()
    val n2 = readln().toInt()

    println(n in n1..n2 && n1 < n2)
}