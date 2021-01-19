package eu.jailbreaker.schematic

import org.bukkit.Location
import java.util.*

class Schematic(
    val name: String,
    val blocks: MutableSet<SchematicBlock>
) {
    private val lastPlaced: Queue<Set<PlacedBlock>> = ArrayDeque()

    fun place(axis: Location): Set<PlacedBlock> {
        val output = mutableSetOf<PlacedBlock>()
        blocks.forEach {
            val block = axis.world.getBlockAt(axis.blockX + it.x, axis.blockY + it.y, axis.blockZ + it.z)
            output.add(PlacedBlock(it.type, block))
            block.type = it.type
        }
        lastPlaced.add(output)
        return output
    }

    fun undo() = lastPlaced.poll().forEach { it.block.type = it.before }
}