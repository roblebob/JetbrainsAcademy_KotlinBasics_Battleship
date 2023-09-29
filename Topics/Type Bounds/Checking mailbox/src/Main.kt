// don't change Letter and CherryPie!
class Letter 
class CherryPie 

// correct the code
class MailBox<T : Letter>(var value: T) {

    fun sendLetter() {
        println("Letter is sent!")
    }
}