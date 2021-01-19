package eu.jailbreaker.lobby.listener

import eu.jailbreaker.lobby.player.destroyScoreboard
import eu.jailbreaker.lobby.player.initializeScoreboard
import eu.jailbreaker.lobby.protection.exitBuildMode
import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.spigotmc.event.player.PlayerSpawnLocationEvent

class BukkitPlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerSpawnLocation(event: PlayerSpawnLocationEvent) {
        event.spawnLocation = Warp.find("spawn")?.location ?: Bukkit.getWorlds().first().spawnLocation
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage = null
        event.player.initializeScoreboard()
        player.inventory.heldItemSlot = 4

        player.activePotionEffects.forEach { potionEffect -> player.removePotionEffect(potionEffect.type) }
        player.fireTicks = 0
        player.health = 20.0
        player.foodLevel = 20
        player.gameMode = GameMode.SURVIVAL

        player.allowFlight = false
        player.isFlying = false

        player.level = 0
        player.exp = 0.0f
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = null
        event.player.exitBuildMode()
        event.player.destroyScoreboard()
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerKick(event: PlayerKickEvent) {
        event.leaveMessage = ""
        event.player.exitBuildMode()
        event.player.destroyScoreboard()
    }
}