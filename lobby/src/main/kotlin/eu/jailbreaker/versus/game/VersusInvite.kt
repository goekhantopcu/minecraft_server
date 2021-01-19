package eu.jailbreaker.versus.game

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

class VersusInvite(
    val inviter: Player,
    val invited: Player,
    var status: Boolean = false
) {
    companion object {
        private val INVITES: MutableList<VersusInvite> = mutableListOf()

        fun invitation(first: Player, second: Player): VersusInvite {
            if (INVITES.any { it.inviter == first && it.invited == second }) {
                first.sendMessage("§cBereits herausgefordert")
                return INVITES.first { it.inviter == first && it.invited == second }
            }
            if (INVITES.any { it.inviter == second && it.invited == first }) {
                first.sendMessage("§aAngenommen")
                second.sendMessage("§a${first.name} hat deine Anfrage angenommen")
                val versusInvite = INVITES.first { it.inviter == second && it.invited == first }
                versusInvite.status = true
                return versusInvite
            }
            val versusInvite = VersusInvite(first, second)
            INVITES.add(versusInvite)
            first.sendMessage("§aDu hast ${second.name} herausgefordert")
            second.sendMessage("§aDu wurdest von ${first.name} herausgefordert")
            return versusInvite
        }
    }

    fun start() {
        val arena = VersusArena(
            Location(Bukkit.getWorld("lobby"), 57.481, 45.0, 177.495),
            30.0,
            mutableListOf(
                Location(Bukkit.getWorld("lobby"), 62.179, 46.0, 162.139, 0.0F, 0.0F),
                Location(Bukkit.getWorld("lobby"), 48.819, 45.0, 196.040, 0.0F, 0.0F)
            )
        )
        val game = VersusGame.create(arena)
        val firstTeam = game.addTeam("Rot", arena.spawns[0], ChatColor.RED)
        val secondTeam = game.addTeam("Blau", arena.spawns[1], ChatColor.BLUE)
        val firstPlayer = firstTeam.addPlayer(inviter)
        val secondPlayer = secondTeam.addPlayer(invited)
        firstPlayer.initializeScoreboard(secondPlayer)
        secondPlayer.initializeScoreboard(firstPlayer)
        game.start()

        INVITES.remove(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersusInvite) return false
        return inviter.uniqueId == other.inviter.uniqueId &&
                invited.uniqueId == other.invited.uniqueId &&
                status == other.status
    }

    override fun hashCode(): Int {
        var result = inviter.uniqueId.hashCode()
        result = 31 * result + invited.uniqueId.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }
}