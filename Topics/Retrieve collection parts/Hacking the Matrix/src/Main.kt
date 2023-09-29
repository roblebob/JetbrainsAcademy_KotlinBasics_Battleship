fun main() {
    val list = readln().split(" ").map { it.toInt() }

    // write your code here
    val res = list.dropLastWhile { it % 2 == 0}

    println(res)
}