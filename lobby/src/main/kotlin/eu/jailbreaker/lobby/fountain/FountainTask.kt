package eu.jailbreaker.lobby.fountain

import org.bukkit.Bukkit

val fountains: MutableList<Fountain> = mutableListOf()

class FountainTask : Runnable {
    override fun run() {
//        if (!isDay()) {
//            return
//        }
        fountains.forEach { it.spawn() }
    }

    fun isDay(): Boolean {
        val time: Long = Bukkit.getWorld("world")?.time ?: 0
        return time < 12300 || time > 23850
    }
}