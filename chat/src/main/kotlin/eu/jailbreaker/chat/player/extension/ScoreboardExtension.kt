package eu.jailbreaker.chat.player.extension

import com.google.common.collect.Multimap
import eu.jailbreaker.chat.player.BasePlayer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Team

interface ScoreboardExtension : BasePlayer {

    val entries: Multimap<String, ScoreboardEntry>

    fun registerObjective(name: String, criteria: String, displayName: String) {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        unregisterObjective(name)
        val objective = scoreboard.registerNewObjective(name, criteria, displayName)
        objective.displaySlot = DisplaySlot.SIDEBAR
        player().scoreboard = scoreboard
    }

    fun addScore(team: String, value: String) {
        val pair = splitValue(value)
        val scoreboardTeam = registerTeam(team, ChatColor.RESET, null, pair.first, pair.second)
        val entry = ScoreboardEntry(team, scoreboardTeam)
        player().scoreboard.getObjective(DisplaySlot.SIDEBAR)?.let { objective ->
            val name = objective.name
            entries.put(name, entry)
            objective.getScore(entry.id).score = entries.get(name).size
        }
    }

    fun updateScore(team: String, value: String) {
        val pair = splitValue(value)
        team(team)?.let { scoreboardTeam ->
            scoreboardTeam.prefix = pair.first
            if (pair.second == null) {
                scoreboardTeam.suffix = ""
            } else {
                scoreboardTeam.suffix = pair.second!!
            }
        }
    }

    fun registerTeam(name: String, color: ChatColor, displayName: String?, prefix: String?, suffix: String?): Team {
        unregisterTeam(name)
        val team = player().scoreboard.registerNewTeam(name)
        team.color = color
        prefix?.let { team.prefix = it }
        suffix?.let { team.suffix = it }
        displayName?.let { team.displayName = it }
        return team
    }

    fun team(name: String): Team? = player().scoreboard.getTeam(name)

    fun unregisterObjective(name: String) {
        player().scoreboard.getObjective(name)?.let {
            entries.removeAll(name)
            it.unregister()
        }
    }

    fun unregisterTeam(name: String) = player().scoreboard.getTeam(name)?.unregister()

    fun addEntryToTeam(entry: String, team: String) = player().scoreboard.getTeam(team)?.addEntry(entry)

    fun removeEntryFromTeam(entry: String, team: String) = player().scoreboard.getTeam(team)?.removeEntry(entry)

    private fun splitValue(value: String): Pair<String, String?> {
        var prefix: String = value
        var suffix: String? = null
        if (value.length > 14) {
            prefix = value.substring(0, 14)
            suffix = value.substring(14)
            if (!suffix.startsWith("§")) {
                suffix = ChatColor.getLastColors(prefix) + suffix
            }
        }
        return Pair(prefix, suffix)
    }
}