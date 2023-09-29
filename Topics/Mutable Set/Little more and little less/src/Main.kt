fun solution(newSet: MutableSet<String>, oldSet: Set<String>): MutableSet<String> {
    // write here
    for (item in oldSet) {
        if (item[0]  == 'a' || item[0] == 'A') {
            newSet.add(item)
        }
    }
    return newSet
}