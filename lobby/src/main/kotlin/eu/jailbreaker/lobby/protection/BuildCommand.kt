package eu.jailbreaker.lobby.protection

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BuildCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.canBuild()) {
                sender.exitBuildMode()
                sender.sendMessage("§cDu kannst nicht mehr bauen")
            } else {
                sender.enterBuildMode()
                sender.sendMessage("§aDu kannst wieder bauen")
            }
        }
        return true
    }
}