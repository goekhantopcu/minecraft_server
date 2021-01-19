package eu.jailbreaker.lobby.protection.listener

import eu.jailbreaker.lobby.protection.canBuild
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent

class EnchantmentListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onEnchantItem(event: EnchantItemEvent) {
        event.isCancelled = !event.enchanter.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPrepareItemEnchant(event: PrepareItemEnchantEvent) {
        event.isCancelled = !event.enchanter.canBuild()
    }
}