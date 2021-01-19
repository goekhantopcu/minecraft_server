package eu.jailbreaker.versus.game

import eu.jailbreaker.chat.player.ChatPlayer
import eu.jailbreaker.chat.player.extension.TablistEntry
import eu.jailbreaker.registerGroupTeams
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class VersusPlayer private constructor(
    val player: Player,
    val team: VersusTeam,
    val game: VersusGame,
    var points: Int = 0,
    var kills: Int = 0,
    var deaths: Int = 0
) {
    companion object {
        private val PLAYERS: MutableMap<Player, VersusPlayer> = mutableMapOf()

        fun find(player: Player) = PLAYERS[player]

        fun create(player: Player, team: VersusTeam, game: VersusGame): VersusPlayer {
            val versusPlayer = VersusPlayer(player, team, game)
            PLAYERS[player] = versusPlayer
            return versusPlayer
        }
    }

    fun initializeScoreboard(target: VersusPlayer) {
        ChatPlayer.find(player)?.let {
            it.unregisterObjective("lobby_objective")
            it.registerObjective("versus_objective", "versus", "§e§lVERSUS")

            it.registerGroupTeams()

            Bukkit.getOnlinePlayers().forEach { onlinePlayer ->
                it.addEntry(TablistEntry.of(onlinePlayer))
            }

            it.registerTeam(team.name, team.color, null, "${team.color}${team.name} §8| ${team.color}", null)
            it.registerTeam(
                target.team.name,
                target.team.color,
                null,
                "${target.team.color}${target.team.name} §8| ${target.team.color}",
                null
            )

            it.addEntryToTeam(player.name, team.name)
            it.addEntryToTeam(target.player.name, target.team.name)

            it.addScore("color_1", " ")
            it.addScore("target_value", " -> §e§l${target.player.name}")
            it.addScore("target_name", "§aGegner")
            it.addScore("color_2", " ")
        }
    }

    fun destroyScoreboard() {
        ChatPlayer.find(player)?.unregisterObjective("versus_objective")
    }

    fun destroy() {
        PLAYERS.remove(player)
        team.players.remove(this)
        if (team.players.isEmpty()) {
            game.teams.remove(team)
        }
        destroyScoreboard()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersusPlayer) return false
        return player.uniqueId == other.player.uniqueId && team == other.team
    }

    override fun hashCode(): Int {
        var result = player.uniqueId.hashCode()
        result = 31 * result + team.hashCode()
        return result
    }
}