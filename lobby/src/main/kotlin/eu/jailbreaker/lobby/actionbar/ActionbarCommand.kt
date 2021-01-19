package eu.jailbreaker.lobby.actionbar

import com.google.common.base.Joiner
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ActionbarCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when {
            args.size == 1 && args[0] == "list" -> {
                actionbars.forEachIndexed { index, value ->
                    sender.sendMessage("§7Index§8: §e$index §7Value§8: '$value§8'")
                }
            }
            args.size >= 2 && args[0] == "add" -> {
                actionbars.add(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        Joiner.on(" ").join(args.copyOfRange(1, args.size))
                    )
                )
                sender.sendMessage("§aActionbar hinzugefügt")
            }
            args.size == 2 && args[0] == "remove" -> {
                sender.sendMessage("§aActionbar §8'${actionbars.removeAt(args[1].toInt())}§8' §aentfernt")
            }
            else -> {
                sender.sendMessage("Verwende: /actionbar list")
                sender.sendMessage("Verwende: /actionbar add <Actionbar>")
                sender.sendMessage("Verwende: /actionbar remove <Index>")
            }
        }
        return true
    }
}