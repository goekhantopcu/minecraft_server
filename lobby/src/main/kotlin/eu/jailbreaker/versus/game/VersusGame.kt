package eu.jailbreaker.versus.game

import eu.jailbreaker.lobby.player.initializeObjective
import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

class VersusGame private constructor(
    val id: Int,
    val arena: VersusArena,
    val teams: MutableSet<VersusTeam> = mutableSetOf()
) {
    companion object {
        val GAMES = mutableListOf<VersusGame>()

        fun create(arena: VersusArena): VersusGame {
            val game = VersusGame(GAMES.size + 1, arena)
            GAMES.add(game)
            return game
        }
    }

    fun start() {
        teams.forEach { it.teleport() }
        broadcast("§8[§61vs1§8] §aDas Spiel gestartet")
    }

    fun stop() {
        val team = teams.iterator().next()
        if (team.players.size == 1) {
            Bukkit.broadcastMessage(
                "§8[§61vs1§8] §7${
                    team.players.iterator().next().player.name
                } hat einen Kampf gewonnen"
            )
        } else {
            Bukkit.broadcastMessage("§8[§61vs1§8] §7${team.name} hat einen Kampf gewonnen")
        }
        players().forEach { player ->
            player.destroy()
            Warp.find("spawn")?.let { warp -> player.player.teleport(warp.location) }
            player.player.initializeObjective()
        }
        GAMES.remove(this)
    }

    fun addTeam(name: String, spawn: Location, color: ChatColor): VersusTeam {
        val versusTeam = VersusTeam(name, spawn, this, color)
        teams.add(versusTeam)
        return versusTeam
    }

    fun removePlayer(player: Player) {
        VersusPlayer.find(player)?.let {
            it.destroy()
            Warp.find("spawn")?.let { warp -> player.teleport(warp.location) }
            player.initializeObjective()
            if (teams.size == 1 || teams.isEmpty()) {
                stop()
            }
        }
    }

    fun players(): List<VersusPlayer> = teams.flatMap { it.players }.toList()

    fun broadcast(message: String) = players().forEach { it.player.sendMessage(message) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersusGame) return false
        return id == other.id && arena != other.arena
    }

    override fun hashCode(): Int = 31 * id + arena.hashCode()
}