package eu.jailbreaker.chat.listener

import com.github.benmanes.caffeine.cache.Caffeine
import eu.jailbreaker.chat.ChatMessage
import eu.jailbreaker.chat.execute
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BukkitChatListener : Listener {

    private val dateFormat = SimpleDateFormat("'§e'dd.MM.yyyy '§7um§e' HH:mm'§7Uhr'", Locale.GERMANY)
    private val chatDelay = Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build<UUID, Long>()
    private val lastMessage = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build<UUID, String>()

    @EventHandler(priority = EventPriority.LOWEST)
    fun onAsyncChat(event: AsyncPlayerChatEvent) {
        event.recipients.clear()
        event.isCancelled = true

        val player = event.player
        var message = event.message
        val bypass = player.hasPermission("chat.bypass")

        if (message.length <= 2 && !bypass) {
            player.sendMessage(ChatMessage.CHAT_TOO_SHORT)
            return
        }

        if (chatDelay.getIfPresent(player.uniqueId) != null && !bypass) {
            player.sendMessage(ChatMessage.CHAT_COOLDOWN)
            return
        }
        chatDelay.put(player.uniqueId, System.currentTimeMillis())

        if (message.toLowerCase() == lastMessage.getIfPresent(player.uniqueId) && !bypass) {
            player.sendMessage(ChatMessage.CHAT_REPEATING)
            return
        }
        lastMessage.put(player.uniqueId, message.toLowerCase())

        if (player.hasPermission("chat.color")) {
            message = ChatColor.translateAlternateColorCodes('&', message)
        }

        val timestamp = System.currentTimeMillis()

        val hover = HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            Text(
                arrayOf<BaseComponent>(
                    TextComponent(
                        *TextComponent.fromLegacyText(
                            "§7Nachricht abgeschickt am: §e§l${
                                dateFormat.format(
                                    timestamp
                                )
                            }"
                        )
                    )
                )
            )
        )

        execute {
            val builder = ComponentBuilder(player.displayName)
                .event(hover)
                .append(" §8» ")
                .event(hover)
                .color(ChatColor.DARK_GRAY)
                .append(message)
                .event(hover)
                .color(ChatColor.GRAY)
            val output = builder.create()
            Bukkit.broadcast(*output)
        }
    }
}