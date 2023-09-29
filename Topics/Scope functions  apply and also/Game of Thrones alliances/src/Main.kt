fun main() {
    val heroesList = mutableListOf("John Snow", "Daenerys Targaryen")
        .apply { println("Old heroes list: ${this.joinToString()}") }
        .also { it.add(readln()) }
        .apply { println("New heroes list: ${this.joinToString()}") }
}