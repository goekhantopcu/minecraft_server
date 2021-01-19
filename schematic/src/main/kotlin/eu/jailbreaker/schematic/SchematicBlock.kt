package eu.jailbreaker.schematic

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import java.io.Serializable
import java.util.*

data class SchematicBlock(
    val type: Material,
    val x: Int,
    val y: Int,
    val z: Int,
    val properties: Properties
) : Serializable {
    constructor(axis: Location, block: Block) : this(
        block.type,
        block.x - axis.blockX,
        block.y - axis.blockY,
        block.z - axis.blockZ,
        Properties()
    )
}