package eu.jailbreaker.lobby.animatedblock

import com.google.common.collect.Lists
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

class AnimatedBlock(private val center: Location) {
    val locations: List<Location> = findLocations()

    fun nearbyPlayers(): List<Player> = center.getNearbyEntities(10.0, 10.0, 10.0).filterIsInstance<Player>()

    private fun findLocations(): List<Location> {
        val locations: MutableList<Location> = Lists.newArrayList()
        locations.add(findLowestLocation(center.clone().add(-1.0, 0.0, 1.0)))
        locations.add(findLowestLocation(center.clone().add(0.0, 0.0, 1.0)))
        locations.add(findLowestLocation(center.clone().add(1.0, 0.0, 1.0)))
        locations.add(findLowestLocation(center.clone().add(1.0, 0.0, 0.0)))
        locations.add(findLowestLocation(center.clone().add(1.0, 0.0, -1.0)))
        locations.add(findLowestLocation(center.clone().add(0.0, 0.0, -1.0)))
        locations.add(findLowestLocation(center.clone().add(-1.0, 0.0, -1.0)))
        locations.add(findLowestLocation(center.clone().add(-1.0, 0.0, 0.0)))
        return locations
    }

    private fun findLowestLocation(location: Location): Location {
        var tmp: Location = location.clone()
        while (tmp.block.type == Material.AIR) {
            tmp = tmp.clone().add(0.0, -1.0, 0.0)
        }
        return tmp
    }
}