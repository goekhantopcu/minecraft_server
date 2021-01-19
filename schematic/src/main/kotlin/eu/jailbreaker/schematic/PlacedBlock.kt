package eu.jailbreaker.schematic

import org.bukkit.Material
import org.bukkit.block.Block

data class PlacedBlock(
    val before: Material,
    val block: Block
)