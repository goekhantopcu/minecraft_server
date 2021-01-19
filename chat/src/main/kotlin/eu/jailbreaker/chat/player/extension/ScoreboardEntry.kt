package eu.jailbreaker.chat.player.extension

import org.bukkit.ChatColor
import org.bukkit.scoreboard.Team

class ScoreboardEntry(
    val name: String,
    val team: Team,
) {
    val id: String = uniqueEntry()

    init {
        team.addEntry(id)
    }

    fun uniqueEntry(): String {
        val id = "${color()}${color()}${color()}"
        val scoreboard = team.scoreboard ?: throw NullPointerException("scoreboard must not be null")
        return if (scoreboard.teams.any { it.hasEntry(id) }) uniqueEntry() else id
    }

    fun color(): ChatColor {
        val color = ChatColor.values().random()
        if (
            color == ChatColor.UNDERLINE ||
            color == ChatColor.STRIKETHROUGH ||
            color == ChatColor.MAGIC ||
            color == ChatColor.BOLD ||
            color == ChatColor.ITALIC
        ) {
            return color()
        }
        return color
    }
}