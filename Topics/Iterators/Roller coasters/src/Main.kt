fun checkHeight(iterator: Iterator<Int>) {
    // write your code here
    while (iterator.hasNext()) {
        val height = iterator.next()
        println( if (height < 145 || height > 210) "Sorry, not today" else "You can go!")
    }
}

fun main() {
    val list = readln().split(" ").map(Integer::parseInt).toList()
    checkHeight(list.iterator())
}