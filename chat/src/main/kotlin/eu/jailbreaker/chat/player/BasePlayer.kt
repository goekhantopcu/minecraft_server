package eu.jailbreaker.chat.player

import org.bukkit.entity.Player
import java.util.*

interface BasePlayer {
    fun self(): BasePlayer = this

    fun uniqueId(): UUID

    fun player(): Player

    fun name(): String
}