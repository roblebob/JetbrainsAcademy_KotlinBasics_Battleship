fun doubleFormat(value: Double, width: Int, precision: Int): String {
    // write your code here
    return String.format("%${width}.${precision}f", value)
}