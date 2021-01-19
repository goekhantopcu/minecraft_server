package eu.jailbreaker.lobby.player.scoreboard

class ScoreboardList(
    content: String,
    val maxLength: Int,
) : ArrayList<Char>(content.toCharArray().toList()) {

    val value: String
        get() = valueAtIndex()

    private var offset = 0

    override fun get(index: Int): Char {
        var i = index + offset
        if (i >= size) {
            i -= size
        }
        return super.get(i)
    }

    fun increaseOffset(value: Int) {
        offset += value
        if (offset >= (size - 1)) {
            offset = 0
        }
    }

    private fun valueAtIndex(): String {
        var index = 0
        val buffer = StringBuilder()
        while (index <= maxLength) {
            when {
                index > 0 && get(index - 1) == '§' -> {
                    buffer.append("§")
                    buffer.append(get(index))
                    buffer.append(get(index + 1))
                    index += 2
                }
                get(index) == '§' -> {
                    buffer.append("§")
                    buffer.append(get(index + 1))
                    buffer.append(get(index + 2))
                    index += 3
                }
                else -> {
                    buffer.append(get(index))
                    index++
                }
            }
        }
        return buffer.toString()
    }
}

//fun main() {
//    val list = ScoreboardList("§3✆ §8┃ §7Teamspeak")
//    println(list.value)
//    while (true) {
//        list.increaseOffset(1)
//        println(list.value)
//    }
//}