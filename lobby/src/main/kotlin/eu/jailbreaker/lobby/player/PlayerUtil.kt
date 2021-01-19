package eu.jailbreaker.lobby.player

import eu.jailbreaker.chat.color
import eu.jailbreaker.chat.displayName
import eu.jailbreaker.chat.formattedName
import eu.jailbreaker.chat.permissionGroup
import eu.jailbreaker.chat.player.ChatPlayer
import eu.jailbreaker.lobby.LobbyMessage.TABLIST_FOOTER
import eu.jailbreaker.lobby.LobbyMessage.TABLIST_HEADER
import eu.jailbreaker.registerGroupTeams
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun Player.initializeScoreboard() {
    setDisplayName(displayName())
    val chatPlayer = ChatPlayer.initialize(this)
    initializeObjective()
    chatPlayer.registerGroupTeams()
    chatPlayer.addEntries()

    Bukkit.getOnlinePlayers().mapNotNull { ChatPlayer.find(it) }.forEach {
        it.setHeaderAndFooter(
            String.format(TABLIST_HEADER, displayName),
            String.format(TABLIST_FOOTER, Bukkit.getOnlinePlayers().size)
        )
    }
}

fun Player.destroyScoreboard() {
    ChatPlayer.destroy(this)?.removeEntries()
    Bukkit.getOnlinePlayers().mapNotNull { ChatPlayer.find(it) }.forEach {
        it.setHeaderAndFooter(
            String.format(TABLIST_HEADER, displayName),
            String.format(TABLIST_FOOTER, Bukkit.getOnlinePlayers().size)
        )
    }
}

fun Player.initializeObjective() {
    ChatPlayer.find(this)?.let { chatPlayer ->
        chatPlayer.registerGroupTeams()
        chatPlayer.addEntries()

        chatPlayer.registerObjective("lobby_objective", "dummy", "§8▸ $displayName §8◂")
        chatPlayer.addScore("color_1", "                          ")
        chatPlayer.addScore("advertise_value", " §8➥ §bJailBreaker.eu")
        chatPlayer.addScore("advertise_title", "§3✆ §8┃ §7Teamspeak")
        chatPlayer.addScore("color_2", " ")
        chatPlayer.addScore("taler_value", " §8➥ §e1000")
        chatPlayer.addScore("taler_title", "§6✪ §8┃ §7Taler")
        chatPlayer.addScore("color_3", " ")
        chatPlayer.addScore("rank_name", " §8➥ ${permissionGroup().color()}${permissionGroup().formattedName()}")
        chatPlayer.addScore("rank_color", "${permissionGroup().color()}● §8┃ §7Rang")
        chatPlayer.addScore("color_4", "                          ")
    }
}

fun Player.destroyObjective() {
    ChatPlayer.find(this)?.unregisterObjective("lobby_objective")
}