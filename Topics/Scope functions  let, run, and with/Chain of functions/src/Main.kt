data class Musician(var name: String = "", var band: String = "", var role: String = "")

fun main() {
    val musician = Musician()

    musician.apply {
        name = readln()
        band = readln()
        role = readln()
    }

    // Write your code here
    val num: Int = musician.run {
        println(musician)
        this.name.length
    }.let {
        val x = it / 4
        println(x)
        x
    }.let {
        val y = it + 10
        println(y)
        y
    }
}