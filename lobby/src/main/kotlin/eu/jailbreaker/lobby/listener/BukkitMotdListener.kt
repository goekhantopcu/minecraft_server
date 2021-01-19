package eu.jailbreaker.lobby.listener

import eu.jailbreaker.lobby.LobbyMessage
import eu.jailbreaker.lobby.lobbyPlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.util.CachedServerIcon
import java.nio.file.Files
import java.nio.file.Paths

class BukkitMotdListener : Listener {

    private val serverIcon: CachedServerIcon? = loadCachedServerIcon()
    private var motd = LobbyMessage.MOTD_HEADER + System.lineSeparator() + LobbyMessage.MOTD_FOOTER

    @EventHandler
    fun onServerListPing(event: ServerListPingEvent) {
        event.motd = motd
        serverIcon?.let { event.setServerIcon(it) }
        event.maxPlayers = Bukkit.getOnlinePlayers().size + 1
    }

    private fun loadCachedServerIcon(): CachedServerIcon? = try {
        val path = Paths.get(lobbyPlugin.dataFolder.toString(), "icon.png")
        if (Files.notExists(path)) {
            lobbyPlugin::class.java.classLoader.getResourceAsStream("icon.png")?.let { Files.copy(it, path) }
        }
        Bukkit.loadServerIcon(path.toFile())
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        null
    }
}