package eu.jailbreaker.lobby.player.scoreboard

import eu.jailbreaker.chat.player.ChatPlayer
import org.bukkit.Bukkit

class ScoreboardAnimation : Runnable {

    private val title: ScoreboardList = ScoreboardList(
        "            Twitter             TeamSpeak     ",
        14
    )
    private val subtitle: ScoreboardList = ScoreboardList(
        "            @Server             JailBreaker.eu",
        14
    )

    override fun run() {
        val first = title.value
        val second = subtitle.value
        Bukkit.getOnlinePlayers()
            .mapNotNull { ChatPlayer.find(it) }
            .forEach { chatPlayer ->
                chatPlayer.updateScore("advertise_title", "§3✆ §8┃ §7$first")
                chatPlayer.updateScore("advertise_value", " §8➥ §b$second")
            }
        title.increaseOffset(1)
        subtitle.increaseOffset(1)
    }
}