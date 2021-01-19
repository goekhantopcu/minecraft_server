package eu.jailbreaker.schematic

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Cuboid : Iterable<Block?>, Cloneable, ConfigurationSerializable {
    val world: String

    val lowerX: Int
    val lowerY: Int
    val lowerZ: Int
    val upperX: Int
    val upperY: Int
    val upperZ: Int

    constructor(first: Location, second: Location) {
        if (Objects.requireNonNull(first.world) != second.world) {
            throw IllegalArgumentException("locations must be on the same world")
        }
        world = first.world.name
        lowerX = min(first.blockX, second.blockX)
        lowerY = min(first.blockY, second.blockY)
        lowerZ = min(first.blockZ, second.blockZ)
        upperX = max(first.blockX, second.blockX)
        upperY = max(first.blockY, second.blockY)
        upperZ = max(first.blockZ, second.blockZ)
    }

    constructor(location: Location) : this(location, location)

    constructor(other: Cuboid) : this(
        other.getWorld().name,
        other.lowerX,
        other.lowerY,
        other.lowerZ,
        other.upperX,
        other.upperY,
        other.upperZ
    )

    constructor(world: World, x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int) {
        this.world = world.name
        lowerX = min(x1, x2)
        upperX = max(x1, x2)
        lowerY = min(y1, y2)
        upperY = max(y1, y2)
        lowerZ = min(z1, z2)
        upperZ = max(z1, z2)
    }

    private constructor(world: String, x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int) {
        this.world = world
        lowerX = min(x1, x2)
        upperX = max(x1, x2)
        lowerY = min(y1, y2)
        upperY = max(y1, y2)
        lowerZ = min(z1, z2)
        upperZ = max(z1, z2)
    }

    constructor(map: Map<String, Any>) {
        world = map["world"] as String
        lowerX = map["x1"] as Int
        upperX = map["x2"] as Int
        lowerY = map["y1"] as Int
        upperY = map["y2"] as Int
        lowerZ = map["z1"] as Int
        upperZ = map["z2"] as Int
    }

    override fun serialize(): Map<String, Any> {
        val map: MutableMap<String, Any> = Maps.newHashMap()
        map["world"] = world
        map["x1"] = lowerX
        map["y1"] = lowerY
        map["z1"] = lowerZ
        map["x2"] = upperX
        map["y2"] = upperY
        map["z2"] = upperZ
        return map
    }

    val blocks: List<Block>
        get() {
            val iterator: CuboidIterator = iterator()
            val blockList: MutableList<Block> = Lists.newArrayList()
            while (iterator.hasNext()) {
                blockList.add(iterator.next())
            }
            return blockList
        }
    val lowerNE: Location
        get() = Location(getWorld(), lowerX.toDouble(), lowerY.toDouble(), lowerZ.toDouble())
    val upperSW: Location
        get() = Location(getWorld(), upperX.toDouble(), upperY.toDouble(), upperZ.toDouble())
    val center: Location
        get() {
            val x1: Int = upperX + 1
            val y1: Int = upperY + 1
            val z1: Int = upperZ + 1
            return Location(
                getWorld(),
                lowerX + (x1 - lowerX) / 2.0,
                lowerY + (y1 - lowerY) / 2.0,
                lowerZ + (z1 - lowerZ) / 2.0
            )
        }

    fun getWorld(): World = Bukkit.getWorld(this.world) ?: error("world '" + this.world + "' is not loaded")

    val sizeX: Int
        get() = (upperX - lowerX) + 1
    val sizeY: Int
        get() = (upperY - lowerY) + 1
    val sizeZ: Int
        get() = (upperZ - lowerZ) + 1

    fun corners(): Array<Block> {
        val res: Array<Block> = arrayOf()
        val w: World = getWorld()
        res[0] = w.getBlockAt(lowerX, lowerY, lowerZ)
        res[0] = w.getBlockAt(lowerX, lowerY, lowerZ)
        res[1] = w.getBlockAt(lowerX, lowerY, upperZ)
        res[2] = w.getBlockAt(lowerX, upperY, lowerZ)
        res[3] = w.getBlockAt(lowerX, upperY, upperZ)
        res[4] = w.getBlockAt(upperX, lowerY, lowerZ)
        res[5] = w.getBlockAt(upperX, lowerY, upperZ)
        res[6] = w.getBlockAt(upperX, upperY, lowerZ)
        res[7] = w.getBlockAt(upperX, upperY, upperZ)
        return res
    }

    fun expand(dir: CuboidDirection, amount: Int): Cuboid = when (dir) {
        CuboidDirection.NORTH -> Cuboid(world, lowerX - amount, lowerY, lowerZ, upperX, upperY, upperZ)
        CuboidDirection.SOUTH -> Cuboid(world, lowerX, lowerY, lowerZ, upperX + amount, upperY, upperZ)
        CuboidDirection.EAST -> Cuboid(world, lowerX, lowerY, lowerZ - amount, upperX, upperY, upperZ)
        CuboidDirection.WEST -> Cuboid(world, lowerX, lowerY, lowerZ, upperX, upperY, upperZ + amount)
        CuboidDirection.DOWN -> Cuboid(world, lowerX, lowerY - amount, lowerZ, upperX, upperY, upperZ)
        CuboidDirection.UP -> Cuboid(world, lowerX, lowerY, lowerZ, upperX, upperY + amount, upperZ)
        else -> throw IllegalArgumentException("invalid direction $dir")
    }

    fun shift(dir: CuboidDirection, amount: Int): Cuboid = expand(dir, amount).expand(dir.opposite(), -amount)

    fun outset(dir: CuboidDirection, amount: Int): Cuboid = when (dir) {
        CuboidDirection.HORIZONTAL -> expand(CuboidDirection.NORTH, amount)
            .expand(CuboidDirection.SOUTH, amount)
            .expand(CuboidDirection.EAST, amount)
            .expand(CuboidDirection.WEST, amount)
        CuboidDirection.VERTICAL -> expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount)
        CuboidDirection.BOTH -> outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount)
        else -> throw IllegalArgumentException("invalid direction $dir")
    }

    fun inset(dir: CuboidDirection, amount: Int) = outset(dir, -amount)

    fun contains(x: Int, y: Int, z: Int) =
        (x >= lowerX) && (x <= upperX) && (y >= lowerY) && (y <= upperY) && (z >= lowerZ) && (z <= upperZ)

    fun contains(x: Int, z: Int) = (x >= lowerX) && (x <= upperX) && (z >= lowerZ) && (z <= upperZ)

    operator fun contains(block: Block) = contains(block.location)

    operator fun contains(location: Location): Boolean {
        if (location.world == null) {
            return false
        }
        return (world == location.world.name) && contains(location.blockX, location.blockY, location.blockZ)
    }

    fun volume() = sizeX * sizeY * sizeZ

    fun averageLightLevel(): Byte {
        var n = 0
        var total: Long = 0
        for (blocks: Block in this) {
            if (blocks.isEmpty) {
                total += blocks.lightLevel.toLong()
                ++n
            }
        }
        return if (n > 0) (total / n).toByte() else 0
    }

    fun contract() = this.contract(CuboidDirection.DOWN)
        .contract(CuboidDirection.SOUTH)
        .contract(CuboidDirection.EAST)
        .contract(CuboidDirection.UP)
        .contract(CuboidDirection.NORTH)
        .contract(CuboidDirection.WEST)

    fun contract(direction: CuboidDirection): Cuboid {
        var face: Cuboid = getFace(direction.opposite())
        when (direction) {
            CuboidDirection.DOWN -> {
                while (face.containsOnly(Material.AIR) && face.lowerY > lowerY) {
                    face = face.shift(CuboidDirection.DOWN, 1)
                }
                return Cuboid(world, lowerX, lowerY, lowerZ, upperX, face.upperY, upperZ)
            }
            CuboidDirection.UP -> {
                while (face.containsOnly(Material.AIR) && face.upperY < upperY) {
                    face = face.shift(CuboidDirection.UP, 1)
                }
                return Cuboid(world, lowerX, face.lowerY, lowerZ, upperX, upperY, upperZ)
            }
            CuboidDirection.NORTH -> {
                while (face.containsOnly(Material.AIR) && face.lowerX > lowerX) {
                    face = face.shift(CuboidDirection.NORTH, 1)
                }
                return Cuboid(world, lowerX, lowerY, lowerZ, face.upperX, upperY, upperZ)
            }
            CuboidDirection.SOUTH -> {
                while (face.containsOnly(Material.AIR) && face.upperX < upperX) {
                    face = face.shift(CuboidDirection.SOUTH, 1)
                }
                return Cuboid(world, face.lowerX, lowerY, lowerZ, upperX, upperY, upperZ)
            }
            CuboidDirection.EAST -> {
                while (face.containsOnly(Material.AIR) && face.lowerZ > lowerZ) {
                    face = face.shift(CuboidDirection.EAST, 1)
                }
                return Cuboid(world, lowerX, lowerY, lowerZ, upperX, upperY, face.upperZ)
            }
            CuboidDirection.WEST -> {
                while (face.containsOnly(Material.AIR) && face.upperZ < upperZ) {
                    face = face.shift(CuboidDirection.WEST, 1)
                }
                return Cuboid(world, lowerX, lowerY, face.lowerZ, upperX, upperY, upperZ)
            }
            else -> throw IllegalArgumentException("Invalid direction $direction")
        }
    }

    fun getFace(dir: CuboidDirection) = when (dir) {
        CuboidDirection.DOWN -> Cuboid(world, lowerX, lowerY, lowerZ, upperX, lowerY, upperZ)
        CuboidDirection.UP -> Cuboid(world, lowerX, upperY, lowerZ, upperX, upperY, upperZ)
        CuboidDirection.NORTH -> Cuboid(world, lowerX, lowerY, lowerZ, lowerX, upperY, upperZ)
        CuboidDirection.SOUTH -> Cuboid(world, upperX, lowerY, lowerZ, upperX, upperY, upperZ)
        CuboidDirection.EAST -> Cuboid(world, lowerX, lowerY, lowerZ, upperX, upperY, lowerZ)
        CuboidDirection.WEST -> Cuboid(world, lowerX, lowerY, upperZ, upperX, upperY, upperZ)
        else -> throw IllegalArgumentException("Invalid direction $dir")
    }

    fun containsOnly(material: Material): Boolean {
        for (blocks: Block in this) {
            if (blocks.type != material) {
                return false
            }
        }
        return true
    }

    fun getBoundingCuboid(other: Cuboid?): Cuboid {
        if (other == null) {
            return this
        }
        val xMin: Int = min(lowerX, other.lowerX)
        val yMin: Int = min(lowerY, other.lowerY)
        val zMin: Int = min(lowerZ, other.lowerZ)
        val xMax: Int = max(upperX, other.upperX)
        val yMax: Int = max(upperY, other.upperY)
        val zMax: Int = max(upperZ, other.upperZ)
        return Cuboid(world, xMin, yMin, zMin, xMax, yMax, zMax)
    }

    fun getRelativeBlock(x: Int, y: Int, z: Int) = getWorld().getBlockAt(lowerX + x, lowerY + y, lowerZ + z)

    fun getRelativeBlock(w: World, x: Int, y: Int, z: Int) = w.getBlockAt(lowerX + x, lowerY + y, lowerZ + z)

    val chunks: List<Chunk>
        get() {
            val res: MutableList<Chunk> = Lists.newArrayList()
            val world: World = getWorld()
            val x1: Int = lowerX and 0xf.inv()
            val x2: Int = upperX and 0xf.inv()
            val z1: Int = lowerZ and 0xf.inv()
            val z2: Int = upperZ and 0xf.inv()
            var x: Int = x1
            while (x <= x2) {
                var z: Int = z1
                while (z <= z2) {
                    res.add(world.getChunkAt(x shr 4, z shr 4))
                    z += 16
                }
                x += 16
            }
            return res
        }

    fun fill(material: Material) {
        for (blocks: Block in this) {
            blocks.type = material
        }
    }

    override fun iterator() = CuboidIterator(getWorld(), lowerX, lowerY, lowerZ, upperX, upperY, upperZ)

    public override fun clone() = Cuboid(this)

    override fun toString() = "Cuboid: $world,$lowerX,$lowerY,$lowerZ=>$upperX,$upperY,$upperZ"

    enum class CuboidDirection {
        NORTH, EAST, SOUTH, WEST, UP, DOWN, HORIZONTAL, VERTICAL, BOTH, UNKNOWN;

        fun opposite(): CuboidDirection {
            return when (this) {
                NORTH -> SOUTH
                EAST -> WEST
                SOUTH -> NORTH
                WEST -> EAST
                HORIZONTAL -> VERTICAL
                VERTICAL -> HORIZONTAL
                UP -> DOWN
                DOWN -> UP
                BOTH -> BOTH
                else -> UNKNOWN
            }
        }
    }

    class CuboidIterator(
        private val world: World,
        private val baseX: Int,
        private val baseY: Int,
        private val baseZ: Int,
        x2: Int,
        y2: Int,
        z2: Int
    ) : MutableIterator<Block> {
        private var x: Int
        private var y: Int
        private var z: Int = 0
        private val sizeX: Int = abs(x2 - baseX) + 1
        private val sizeY: Int = abs(y2 - baseY) + 1
        private val sizeZ: Int = abs(z2 - baseZ) + 1

        override fun hasNext() = (x < sizeX) && (y < sizeY) && (z < sizeZ)

        override fun next(): Block {
            val block: Block = world.getBlockAt(baseX + x, baseY + y, baseZ + z)
            if (++x >= sizeX) {
                x = 0
                if (++y >= sizeY) {
                    y = 0
                    ++z
                }
            }
            return block
        }

        override fun remove() {}

        init {
            y = z
            x = y
        }
    }
}