package eu.jailbreaker.lobby.fountain

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData

class Fountain(
    val location: Location
) {
    companion object {
        val BLOCKDATAS: List<BlockData> = Material.values()
            .filter { it.name.endsWith("_TERRACOTTA") && !it.name.contains("GLAZED") }
            .map { it.createBlockData() }
            .toList()
    }

    fun spawn() {
        val fallingblock = location.world.spawnFallingBlock(location, BLOCKDATAS.random())
        val vector = location.direction.multiply(0.2).setY(0.75)
        fallingblock.velocity = vector
        fallingblock.dropItem = false
    }
}