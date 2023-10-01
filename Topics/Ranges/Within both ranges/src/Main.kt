fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    val e = readln().toInt()
    val f = readln().toInt()
    val x = readln().toInt()
    println(x in a..b && x in e..f)
}