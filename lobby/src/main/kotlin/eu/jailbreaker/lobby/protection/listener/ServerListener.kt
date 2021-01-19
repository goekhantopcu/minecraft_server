package eu.jailbreaker.lobby.protection.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.BroadcastMessageEvent

class ServerListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onBroadcastMessage(event: BroadcastMessageEvent) {
        event.isCancelled = false
    }
}