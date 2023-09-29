// implement your function here
fun <T> countItem(list: List<T>, item: T): Int {
    var count = 0
    list.count {
        if (it == item) {
            count++
            true
        } else {
            false
        }
    }
    return count
}