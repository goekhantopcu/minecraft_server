package eu.jailbreaker.lobby.secret

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SecretCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        when {
            args.size == 1 && args[0] == "reload" -> {

            }
            else -> {
                val create = Secret.create(player.location)
                player.sendMessage("Secret mit id ${create.id} erstellt")
            }
        }
        return true
    }
}