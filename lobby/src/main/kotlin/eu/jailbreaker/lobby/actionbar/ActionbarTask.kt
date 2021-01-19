package eu.jailbreaker.lobby.actionbar

import org.bukkit.Bukkit

val actionbars: MutableList<String> = mutableListOf()

class ActionbarTask : Runnable {

    private var ticker = 0
    private var index = 0

    override fun run() {
        when {
            actionbars.size == 1 -> sendActionBar(0)
            actionbars.size > 1 -> {
                ticker++
                if (ticker >= 10) {
                    index++
                    ticker = 0
                }
                if (index >= actionbars.size) {
                    index = 0
                }
                sendActionBar(index)
            }
        }
    }

    private fun sendActionBar(index: Int) {
        val display = actionbars[index]
        Bukkit.getOnlinePlayers().forEach { it.sendActionBar(display) }
    }
}