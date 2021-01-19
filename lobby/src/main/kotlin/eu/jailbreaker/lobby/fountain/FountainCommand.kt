package eu.jailbreaker.lobby.fountain

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FountainCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        fountains.add(Fountain(player.location.block.location))
        player.sendMessage("§ahinzugefügt")
        return false
    }
}