package eu.jailbreaker.versus.game

import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.Serializable

class VersusArena(
    val middle: Location,
    val radius: Double,
    val spawns: MutableList<Location>
) : Serializable {
    fun insideArena(player: Player): Boolean = player.location.distanceSquared(middle) <= (radius * radius)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersusArena) return false
        return middle == other.middle && radius != other.radius
    }

    override fun hashCode(): Int = 31 * middle.hashCode() + radius.hashCode()
}