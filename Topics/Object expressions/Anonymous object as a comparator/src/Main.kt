class Sorted{
    data class Person(val name: String, val age: Int)

    val people = listOf(
        Person("Alice", 25),
        Person("Bob", 20),
        Person("Charlie", 30)
    )

    val sortedByAge = people.sortedWith( object : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int = p1.age.compareTo(p2.age)
    })
}