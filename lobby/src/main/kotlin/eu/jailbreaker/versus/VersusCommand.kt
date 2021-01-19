package eu.jailbreaker.versus

import eu.jailbreaker.versus.game.VersusKit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VersusCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false

        /*
        /versus createArena <Name> <Radius>
        /versus setTeam <Arena>
        /versus invite <Player>
        /versus accept <Player>
        /versus saveKit <Name>
         */

        when {
            args.size == 3 && args[0] == "createArena" -> {
            }
            args.size == 2 && args[0] == "setTeam" -> {
            }
            args.size == 2 && args[0] == "invite" -> {

            }
            args.size == 2 && args[0] == "accept" -> {

            }
            args.size == 2 && args[0] == "saveKit" -> {
                val name = args[1]
                val content = player.inventory.contents
                val armor = player.inventory.armorContents
                val kit = VersusKit(name, content, armor)
                kit.save()
            }
            else -> {
                player.sendMessage("§8[§61vs1§8] §7Verwende§8: §e/versus invite <Spieler>")
                player.sendMessage("§8[§61vs1§8] §7Verwende§8: §e/versus accept <Spieler>")
                if (player.hasPermission("versus.admin")) {
                    player.sendMessage("§8[§61vs1§8] §7Verwende§8: §e/versus saveKit <Name>")
                    player.sendMessage("§8[§61vs1§8] §7Verwende§8: §e/versus setTeam <Name> <Radius>")
                    player.sendMessage("§8[§61vs1§8] §7Verwende§8: §e/versus createArena <Name> <Radius>")
                }
            }
        }

        return true
    }
}