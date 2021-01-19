package eu.jailbreaker.lobby.animatedblock

import org.bukkit.Material
import org.bukkit.block.data.BlockData

val animatedBlocks: MutableList<AnimatedBlock> = mutableListOf()

class AnimatedBlockTask : Runnable {

    private var counter = 0

    private val blockDatas: List<BlockData> = Material.values()
        .filter { it.name.endsWith("_TERRACOTTA") && !it.name.contains("GLAZED") }
        .map { it.createBlockData() }
        .toList()
    private var entry: BlockData = blockDatas.first()

    override fun run() {
        counter++

        if (counter > 7) {
            counter = 0
            entry = blockDatas.random()
        }

        animatedBlocks.forEach { block ->
            block.nearbyPlayers().forEach { player -> player.sendBlockChange(block.locations[counter], entry) }
        }
    }
}