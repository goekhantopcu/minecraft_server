package eu.jailbreaker.lobby.protection.listener

import eu.jailbreaker.lobby.protection.canBuild
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.hanging.HangingPlaceEvent

class HangingListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onHangingBreakByEntity(event: HangingBreakByEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onHangingBreak(event: HangingBreakEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onHangingPlace(event: HangingPlaceEvent) {
        event.isCancelled = true
        event.player?.let { event.isCancelled = !it.canBuild() }
    }
}