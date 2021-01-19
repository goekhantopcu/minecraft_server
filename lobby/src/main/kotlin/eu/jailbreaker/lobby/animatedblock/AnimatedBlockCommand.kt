package eu.jailbreaker.lobby.animatedblock

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AnimatedBlockCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        when {
            args.size == 1 && args[0] == "list" -> {
                animatedBlocks.forEachIndexed { index, _ -> player.sendMessage("§7Index§8: §e$index") }
            }
            args.size == 1 && args[0] == "add" -> {
                animatedBlocks.add(AnimatedBlock(player.location.clone().add(0.0, -1.0, 0.0)))
                player.sendMessage("§aAnimatedBlock hinzugefügt")
            }
            args.size == 2 && args[0] == "remove" -> {
                player.sendMessage("§aAnimatedBlock §aentfernt")
            }
            else -> {
                player.sendMessage("Verwende: /animatedblock list")
                player.sendMessage("Verwende: /animatedblock add")
                player.sendMessage("Verwende: /animatedblock remove")
            }
        }
        return true
    }
}