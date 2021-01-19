package eu.jailbreaker.versus

import eu.jailbreaker.chat.player.ChatPlayer
import eu.jailbreaker.chat.player.extension.TablistEntry
import eu.jailbreaker.versus.game.VersusInvite
import eu.jailbreaker.versus.game.VersusPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack

class VersusPlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        VersusPlayer.find(player)?.let { versusPlayer ->
            if (!versusPlayer.game.arena.insideArena(player)) {
                player.velocity = player.location.direction.multiply(-1).setY(0.4)
                player.playSound(player.location, Sound.ENTITY_CAT_HISS, 10.0F, 10.0F)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val target = event.rightClicked as? Player ?: return
        if (player.inventory.itemInMainHand.type == Material.STICK) {
            if (VersusPlayer.find(player) == null) {
                VersusInvite.invitation(player, target).apply {
                    if (status) {
                        start()
                    }
                }
            } else {
                player.sendMessage("§cBist bereits in nem Spiel")
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.inventory.addItem(ItemStack(Material.STICK))
        Bukkit.getOnlinePlayers().mapNotNull { VersusPlayer.find(it) }.forEach { versusPlayer ->
            ChatPlayer.find(versusPlayer.player)?.addEntry(TablistEntry.of(event.player))
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return
        val damager = event.damager as? Player ?: return

        val first = VersusPlayer.find(player) ?: return
        val second = VersusPlayer.find(damager) ?: return

        event.isCancelled = first.game != second.game
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity as? Player ?: return
        VersusPlayer.find(player)?.let {
            player.sendMessage("§cDu bist gestorben!")
            it.game.stop()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        VersusPlayer.find(event.player)?.let { event.respawnLocation = it.team.spawn }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        VersusPlayer.find(event.player)?.game?.removePlayer(event.player)
    }

    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        VersusPlayer.find(event.player)?.game?.removePlayer(event.player)
    }
}