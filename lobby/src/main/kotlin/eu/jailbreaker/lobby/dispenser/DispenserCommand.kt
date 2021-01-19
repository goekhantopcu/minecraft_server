package eu.jailbreaker.lobby.dispenser

import eu.jailbreaker.lobby.lobbyPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

class DispenserCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        if (!player.hasMetadata("extra_dispenser")) {
            player.setMetadata("extra_dispenser", FixedMetadataValue(lobbyPlugin, 0))
        }
        player.sendMessage("Klicke einen Dispenser an, der spucken soll")
        return true
    }
}