package eu.jailbreaker

import eu.jailbreaker.chat.color
import eu.jailbreaker.chat.formattedName
import eu.jailbreaker.chat.player.ChatPlayer
import eu.jailbreaker.chat.scoreboardName
import ru.tehkode.permissions.bukkit.PermissionsEx

fun ChatPlayer.registerGroupTeams() {
    PermissionsEx.getPermissionManager().groupList.forEach { group ->
        registerTeam(
            group.scoreboardName(),
            group.color(),
            null,
            "${group.color()}${group.formattedName()} §8| ",
            null
        )
    }
}