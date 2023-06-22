package ir.syrent.origin.paper.placeholder

data class Placeholder(
    val from: String,
    val to: String
) {

    private val pattern = "%$from%"

    fun replace(content: String): String {
        return content.replace(Regex("(?<!%)$pattern(?!%)"), to)
    }

    companion object {
        fun String.placeholder(from: String, to: String): String {
            return Placeholder(from, to).replace(this)
        }

        fun String.compile(vararg placeholders: Placeholder): String {
            var newContent = this
            placeholders.forEach {
                newContent = it.replace(newContent)
            }
            return newContent
        }
    }

}