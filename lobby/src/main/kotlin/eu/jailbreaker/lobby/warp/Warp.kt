package eu.jailbreaker.lobby.warp

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import eu.jailbreaker.lobby.lobbyPlugin
import org.bukkit.Location
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

data class Warp(
    val name: String,
    val location: Location,
    val properties: Properties = Properties()
) {
    companion object {
        val WARPS: MutableSet<Warp> = mutableSetOf()
        private val BASE_DIR = Paths.get("plugins", "Lobby", "warps.json")
        private val GSON = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Location::class.java, JsonLocationAdapter())
            .create()

        init {
            if (Files.notExists(BASE_DIR)) {
                lobbyPlugin::class.java.classLoader.getResourceAsStream("warps.json")?.let { Files.copy(it, BASE_DIR) }
            } else {
                val reader = BASE_DIR.toFile().reader(StandardCharsets.UTF_8)
                val warps = GSON.fromJson(reader, JsonArray::class.java)
                WARPS.addAll(
                    StreamSupport.stream(warps.spliterator(), false)
                        .map { GSON.fromJson(it, Warp::class.java) }
                        .collect(Collectors.toList())
                )
                reader.close()
            }
        }

        fun remove(name: String) = find(name)?.let {
            WARPS.remove(it)
            updateFile()
        }

        fun find(name: String): Warp? = WARPS.firstOrNull { it.name == name }

        private fun updateFile() = BASE_DIR.toFile().writeText(GSON.toJson(WARPS), StandardCharsets.UTF_8)
    }

    fun save() {
        WARPS.add(this)
        updateFile()
    }
}