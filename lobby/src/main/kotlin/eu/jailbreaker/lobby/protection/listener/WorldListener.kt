package eu.jailbreaker.lobby.protection.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.event.world.PortalCreateEvent
import org.bukkit.event.world.StructureGrowEvent

class WorldListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onLootGenerate(event: LootGenerateEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPortalCreate(event: PortalCreateEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onStructureGrow(event: StructureGrowEvent) {
        event.isCancelled = true
    }
}