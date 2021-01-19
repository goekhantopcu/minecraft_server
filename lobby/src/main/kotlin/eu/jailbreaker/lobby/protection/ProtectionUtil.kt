package eu.jailbreaker.lobby.protection

import eu.jailbreaker.lobby.lobbyPlugin
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

fun Player.canBuild(): Boolean = buildMode() == BuildMode.BUILDING

fun Player.buildMode(): BuildMode {
    if (hasMetadata("build_mode")) {
        return if (gameMode == GameMode.CREATIVE) BuildMode.BUILDING else BuildMode.ENABLED
    }
    return BuildMode.DISABLED
}

fun Player.exitBuildMode() {
    allowFlight = false
    gameMode = GameMode.SURVIVAL
    removeMetadata("build_mode", lobbyPlugin)
}

fun Player.enterBuildMode() {
    allowFlight = true
    gameMode = GameMode.CREATIVE
    setMetadata("build_mode", FixedMetadataValue(lobbyPlugin, 0))
}

enum class BuildMode {
    BUILDING, ENABLED, DISABLED
}
