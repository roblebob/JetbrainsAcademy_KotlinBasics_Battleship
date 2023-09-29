data class TextField(
    var text: String = "Hello!",
    var textSize: Int = 12,
    var fontFamily: String = "Roboto"
)

fun main() {
    val textField = TextField()

    val valuesMap = mapOf<String, Int>(
        "Cancel" to 12,
        "Button" to 14,
        "Submit" to 13
    )

    val label = "Button"

    textField
        .apply {
            text = readln()
            textSize = valuesMap[text] ?: 12
        }
        .also { println("New settings: $it") }
}