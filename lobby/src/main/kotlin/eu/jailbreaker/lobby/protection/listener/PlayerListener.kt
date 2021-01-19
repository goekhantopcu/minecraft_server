package eu.jailbreaker.lobby.protection.listener

import eu.jailbreaker.lobby.protection.canBuild
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.*

class PlayerListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerAnimation(event: PlayerAnimationEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerBucketEmpty(event: PlayerBucketEmptyEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerBucketFill(event: PlayerBucketFillEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerEditBook(event: PlayerEditBookEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerEggThrow(event: PlayerEggThrowEvent) {
        event.egg.remove()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerExpChange(event: PlayerExpChangeEvent) {
        event.amount = 0
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerFish(event: PlayerFishEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerGameModeChange(event: PlayerGameModeChangeEvent) {
        event.isCancelled = !event.player.hasPermission("protection.build")
    }

//    @EventHandler(priority = EventPriority.LOW)
//    fun onPlayerHarvestBlock(event: PlayerHarvestBlockEvent) {
//        event.isCancelled = true
//    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerInteractAtEntity(event: PlayerInteractAtEntityEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerItemMend(event: PlayerItemMendEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerPickupArrow(event: PlayerPickupArrowEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerPortal(event: PlayerPortalEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerRecipeDiscover(event: PlayerRecipeDiscoverEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerShearEntity(event: PlayerShearEntityEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerStatisticIncrement(event: PlayerStatisticIncrementEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerTakeLecternBook(event: PlayerTakeLecternBookEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerUnleashEntity(event: PlayerUnleashEntityEvent) {
        event.isCancelled = true
    }
}