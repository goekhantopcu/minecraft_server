package eu.jailbreaker.schematic

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Location
import org.bukkit.Material
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.logging.Level

class SchematicHelper {

    val schematics: MutableMap<String, Schematic> = mutableMapOf()
    private val gson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    fun load(path: Path): Boolean {
        if (Files.notExists(path)) {
            return false
        }

        return try {
            val reader = FileReader(path.toFile())
            val schematic = gson.fromJson(reader, Schematic::class.java)
            schematics[schematic.name.toLowerCase()] = schematic
            reader.close()
            true
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }

    fun save(name: String, axis: Location, pos1: Location, pos2: Location) {
        try {
            val schematic = create(name, axis, pos1, pos2)
            val path = Paths.get(schematicPlugin.dataFolder.toString(), "$name.json")
            if (Files.notExists(path)) {
                Files.createFile(path)
            }
            Files.copy(
                gson.toJson(schematic).byteInputStream(StandardCharsets.UTF_8),
                path,
                StandardCopyOption.REPLACE_EXISTING
            )
            schematicPlugin.logger.log(Level.INFO, "Saved schematic $name")
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun create(name: String, axis: Location, pos1: Location, pos2: Location): Schematic {
        return Schematic(
            name,
            Cuboid(pos1, pos2).blocks.filter { it.type != Material.AIR }.map { SchematicBlock(axis, it) }.toMutableSet()
        )
    }
}