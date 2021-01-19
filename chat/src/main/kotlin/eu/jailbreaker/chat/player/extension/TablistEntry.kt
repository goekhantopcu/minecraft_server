package eu.jailbreaker.chat.player.extension

import com.destroystokyo.paper.profile.PlayerProfile
import eu.jailbreaker.chat.permissionGroup
import eu.jailbreaker.chat.scoreboardName
import org.bukkit.entity.Player

data class TablistEntry(
    val profile: PlayerProfile,
    var team: String
) {
    companion object {
        fun of(player: Player) = TablistEntry(
            player.playerProfile,
            player.permissionGroup().scoreboardName()
        )
    }

    fun name(): String = profile.name ?: error("Name in PlayerProfile is null")
}