package eu.jailbreaker.lobby.protection.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.vehicle.*

class VehicleListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleCreate(event: VehicleCreateEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleDamage(event: VehicleDamageEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleDestroy(event: VehicleDestroyEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleEnter(event: VehicleEnterEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleEntityCollision(event: VehicleEntityCollisionEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVehicleExit(event: VehicleExitEvent) {
        event.isCancelled = true
    }
}