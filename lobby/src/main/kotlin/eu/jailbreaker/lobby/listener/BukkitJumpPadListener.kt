package eu.jailbreaker.lobby.listener

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleSneakEvent

class BukkitJumpPadListener : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action == Action.PHYSICAL) {
            event.clickedBlock?.let { block ->
                if (block.type == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                    player.velocity = player.location.add(0.0, 1.5, 0.0).direction.multiply(5).setY(1.2)
                    player.fallDistance = -100.0F
                    player.playSound(player.location, Sound.ENTITY_MAGMA_CUBE_SQUISH, 10.0F, 10.0F)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        if (event.to.block.isLiquid) {
            player.playSound(player.location, Sound.ENTITY_CHICKEN_EGG, 10.0F, 10.0F)
            player.velocity = player.location.direction.multiply(3.0).setY(1.2)
        }
    }

    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        if (player.passengers.isEmpty()) {
            return
        }
        player.passengers.forEach { passenger ->
            passenger.leaveVehicle()
            passenger.velocity = player.location.direction.multiply(1.5).setY(1.0)
            if (passenger is Player) {
                passenger.playSound(passenger.location, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 10F, 10F)
            }
        }
    }
}