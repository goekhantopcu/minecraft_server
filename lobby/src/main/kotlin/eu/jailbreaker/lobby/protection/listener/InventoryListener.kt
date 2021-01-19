package eu.jailbreaker.lobby.protection.listener

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*

class InventoryListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onBrew(event: BrewEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBrewingStandFuel(event: BrewingStandFuelEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onCraftItem(event: CraftItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onFurnaceBurn(event: FurnaceBurnEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onFurnaceSmelt(event: FurnaceSmeltEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onInventoryClick(event: InventoryClickEvent) {
        event.isCancelled = true
        if (event.whoClicked is Player) {
            event.isCancelled = event.whoClicked.gameMode != GameMode.CREATIVE
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onInventoryDrag(event: InventoryDragEvent) {
        event.isCancelled = true
        if (event.whoClicked is Player) {
            event.isCancelled = event.whoClicked.gameMode != GameMode.CREATIVE
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onInventoryMoveItem(event: InventoryMoveItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onInventoryPickupItem(event: InventoryPickupItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        event.result = null
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onTradeSelect(event: TradeSelectEvent) {
        event.isCancelled = true
    }
}