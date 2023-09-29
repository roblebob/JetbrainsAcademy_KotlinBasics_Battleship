fun solution(numbers1: Set<Int>, numbers2: Set<Int>): Int {
    // put your code here
    return if (numbers1.size > numbers2.size) numbers1.minus(numbers2).size else numbers2.minus(numbers1).size
}