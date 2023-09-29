fun main() {
    val list = readln().split(" ")
    // write your code here
    val res = list.windowed(2, 2, true)

    println(res)
}