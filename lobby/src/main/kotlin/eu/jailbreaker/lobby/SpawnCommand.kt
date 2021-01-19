package eu.jailbreaker.lobby

import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        if (args.size == 1 && player.hasPermission("lobby.setup")) {
            Warp("spawn", player.location).save()
            player.sendMessage("Spawn markiert")
            return true
        }
        player.teleport(Warp.find("spawn")?.location ?: Bukkit.getWorlds().first().spawnLocation)
        return true
    }
}