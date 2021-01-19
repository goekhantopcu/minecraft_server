package eu.jailbreaker.versus.game

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

class VersusTeam(
    val name: String,
    val spawn: Location,
    val game: VersusGame,
    val color: ChatColor,
    val players: MutableSet<VersusPlayer> = mutableSetOf()
) {
    fun addPlayer(player: Player): VersusPlayer {
        val versusPlayer = VersusPlayer.create(player, this, game)
        players.add(versusPlayer)
        return versusPlayer
    }

    fun teleport() = players.forEach { it.player.teleport(spawn) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersusTeam) return false
        return name == other.name && spawn == other.spawn && game == other.game
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + spawn.hashCode()
        result = 31 * result + game.hashCode()
        return result
    }
}