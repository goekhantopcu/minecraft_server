package eu.jailbreaker.lobby.protection.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.raid.RaidTriggerEvent

class RaidListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onRaidFinish(event: RaidTriggerEvent) {
        event.isCancelled = true
    }
}