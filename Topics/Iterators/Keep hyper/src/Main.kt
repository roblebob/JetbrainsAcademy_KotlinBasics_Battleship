fun processIterator(list: List<String>): List<String> {
    // write your code
    val mList = list.toMutableList()
    val iterator = mList.iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (next.length < "hyper".length || next.substring(0, "hyper".length) != "hyper") {
            iterator.remove()
        }
    }
    return mList.reversed().toList()
}

fun main() {
    var list = processIterator(readln().split(" "))
    // output the list
    println(list.joinToString("\n"))
}