package eu.jailbreaker.lobby.dispenser

import eu.jailbreaker.lobby.lobbyPlugin
import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action.*
import org.bukkit.event.player.PlayerInteractEvent

class BukkitDispenserListener : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val action = event.action
        if ((action == RIGHT_CLICK_BLOCK || action == LEFT_CLICK_BLOCK) && event.player.hasMetadata("extra_dispenser")) {
            event.clickedBlock?.let { block ->
                if (block.type != Material.DISPENSER) {
                    return@let
                }
                event.isCancelled = true
                event.setUseItemInHand(Event.Result.DENY)
                event.setUseInteractedBlock(Event.Result.DENY)

                val nextId = Warp.WARPS.count { warp -> warp.name.startsWith("dispenser_") }
                Warp("dispenser_$nextId", block.location).save()

                event.player.removeMetadata("extra_dispenser", lobbyPlugin)
                event.player.sendMessage("${block.type.name} wird spucken")
            }
        }
    }
}