fun main() {
    val domains = readln().split(" ")

    val map = with(domains) {
        println(this.size)
        this.map { it.lowercase() }
    }.associateWith { mutableListOf(it.length) }
        .let(::println)
}