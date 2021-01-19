package eu.jailbreaker.antiwdl

import eu.jailbreaker.telegram.Telegram
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRegisterChannelEvent
import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.LoadOrder
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.Website
import org.bukkit.plugin.java.annotation.plugin.author.Author
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.plugin.messaging.PluginMessageListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@Author("JailBreaker")
@ApiVersion(ApiVersion.Target.v1_13)
@LoadOrder(PluginLoadOrder.POSTWORLD)
@Website("https://jailbreaker.eu")
@Plugin(name = "AntiWDL", version = "0.0.2")
class AntiWDL : JavaPlugin(), PluginMessageListener, Listener {

    private val kickMessage = "§8§m--------------\n" +
            "\n" +
            "§cDein Client wird nicht von unserem Netzwerk unterstützt§8.\n" +
            "§cSollte das ein Fehler sein§8, §cso melde dich bitte auf unserem TeamSpeak-Server§8.\n" +
            "\n" +
            "§8§m--------------"

    private var lastOnline = 0
    private val telegram = Telegram()
    private lateinit var scheduledFuture: ScheduledFuture<*>
    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private val dateformat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    private val blockedChannel: MutableSet<String> = TreeSet(String.CASE_INSENSITIVE_ORDER)

    init {
        blockedChannel.addAll(
            arrayOf(
                "WDL", "wdl:init", "wdl:control", "WDL|INIT", "WDL|CONTROL",
                "WDL|REQUEST", "wdl:control", "wdl:request",
                "schematica",
//                "FML|HS", "FML", "mcp", "Forge",
                "LiteLoader", "liteloader",
                "SERVERUI|S0", "SERVERUI|C0"
            )
        )
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        val messenger: Messenger = server.messenger
        blockedChannel.filter { it.contains(":") }.forEach { channel ->
            messenger.registerIncomingPluginChannel(this, channel, this)
            messenger.registerOutgoingPluginChannel(this, channel)
        }

        telegram.connect().whenComplete { _, _ ->
            scheduledFuture = executorService.scheduleAtFixedRate({ updateChatTitle() }, 3L, 3L, TimeUnit.SECONDS)
        }
    }

    override fun onDisable() {
        telegram.disconnect().whenComplete { _, _ -> scheduledFuture.cancel(true) }
    }

    @EventHandler
    fun onPlayerRegisterChannel(event: PlayerRegisterChannelEvent) {
        if (isBlocked(event.channel)) {
            kickPlayer(event.player, event.channel)
        }
    }

    override fun onPluginMessageReceived(channel: String, player: Player, bytes: ByteArray) {
        if (isBlocked(channel)) {
            kickPlayer(player, channel)
        }
    }

    private fun isBlocked(channel: String): Boolean = blockedChannel.contains(channel)

    private fun kickPlayer(player: Player, channel: String) {
        player.kickPlayer(kickMessage)
        val timestamp = dateformat.format(LocalDateTime.now())
        telegram.sendMessage(
            "-498375374",
            "\nTimestamp: $timestamp\nUUID: ${player.uniqueId}\nName: ${player.name}\nChannel: $channel\n"
        )
    }

    private fun updateChatTitle() {
        Bukkit.getOnlinePlayers().size.apply {
            if (lastOnline != this) {
                lastOnline = this
                telegram.changeChatTitle("-498375374", "Server ($lastOnline)")
            }
        }
    }
}