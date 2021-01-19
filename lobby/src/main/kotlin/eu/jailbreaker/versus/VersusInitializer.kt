package eu.jailbreaker.versus

import eu.jailbreaker.versus.game.VersusGame
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class VersusInitializer {

    companion object {
        val EXECUTOR_SERVICE: ScheduledExecutorService = Executors.newScheduledThreadPool(3)

        fun initialize(plugin: JavaPlugin) {
            plugin.getCommand("versus")?.setExecutor(VersusCommand())
            plugin.server.pluginManager.registerEvents(VersusPlayerListener(), plugin)
        }

        fun destroy() {
            VersusGame.GAMES.forEach { versusGame -> versusGame.stop() }
        }
    }
}