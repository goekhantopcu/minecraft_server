package eu.jailbreaker.schematic

import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue

class SchematicListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockBreak(event: BlockBreakEvent) {
        event.player.inventory.itemInMainHand.let {
            if (it.type == Material.WOODEN_AXE) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        event.item?.let { item ->
            if (item.type == Material.WOODEN_AXE) {
                event.clickedBlock?.let { block ->
                    if (event.action == Action.RIGHT_CLICK_BLOCK) {
                        event.isCancelled = true
                        event.setUseItemInHand(Event.Result.DENY)
                        event.setUseInteractedBlock(Event.Result.DENY)

                        event.player.removeMetadata("pos1", schematicPlugin)
                        event.player.setMetadata("pos1", FixedMetadataValue(schematicPlugin, block.location))
                        event.player.sendMessage("§8[§dSchematic§8] §7Du hast §ePos1 §7markiert§8.")
                        return
                    }
                    if (event.action == Action.LEFT_CLICK_BLOCK) {
                        event.isCancelled = true
                        event.setUseItemInHand(Event.Result.DENY)
                        event.setUseInteractedBlock(Event.Result.DENY)

                        event.player.removeMetadata("pos2", schematicPlugin)
                        event.player.setMetadata("pos2", FixedMetadataValue(schematicPlugin, block.location))
                        event.player.sendMessage("§8[§dSchematic§8] §7Du hast §ePos2 §7markiert§8.")
                    }
                }
            }
        }
    }
}