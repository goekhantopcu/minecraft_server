package eu.jailbreaker.chat.player

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import eu.jailbreaker.chat.player.extension.ScoreboardEntry
import eu.jailbreaker.chat.player.extension.ScoreboardExtension
import eu.jailbreaker.chat.player.extension.TablistExtension
import org.bukkit.entity.Player
import java.util.*

class ChatPlayer private constructor(
    private val player: Player,
    override val entries: Multimap<String, ScoreboardEntry> = HashMultimap.create()
) : BasePlayer, ScoreboardExtension, TablistExtension {

    companion object {
        private val CHAT_PLAYERS: MutableMap<UUID, ChatPlayer> = mutableMapOf()

        fun find(player: Player): ChatPlayer? = CHAT_PLAYERS[player.uniqueId]

        fun initialize(player: Player): ChatPlayer {
            if (CHAT_PLAYERS.containsKey(player.uniqueId)) {
                return CHAT_PLAYERS[player.uniqueId]!!
            }
            val chatPlayer = ChatPlayer(player)
            CHAT_PLAYERS[player.uniqueId] = chatPlayer
            return chatPlayer
        }

        fun destroy(player: Player) = CHAT_PLAYERS.remove(player.uniqueId)
    }

    override fun player(): Player = player

    override fun name(): String = player.name

    override fun uniqueId(): UUID = player.uniqueId
}