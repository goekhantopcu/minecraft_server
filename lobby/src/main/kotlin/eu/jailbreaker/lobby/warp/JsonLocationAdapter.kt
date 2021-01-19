package eu.jailbreaker.lobby.warp

import com.google.gson.*
import org.bukkit.Bukkit
import org.bukkit.Location
import java.lang.reflect.Type

class JsonLocationAdapter : JsonSerializer<Location>, JsonDeserializer<Location> {
    override fun serialize(location: Location, type: Type, context: JsonSerializationContext): JsonElement {
        val obj = JsonObject()
        obj.addProperty("world", location.world.name)
        obj.addProperty("x", location.x)
        obj.addProperty("y", location.y)
        obj.addProperty("z", location.z)
        obj.addProperty("yaw", location.yaw)
        obj.addProperty("pitch", location.pitch)
        return obj
    }

    override fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Location {
        val obj = element.asJsonObject
        val worldName = obj.get("world").asString
        val x = obj.get("x").asDouble
        val y = obj.get("y").asDouble
        val z = obj.get("z").asDouble
        val yaw = obj.get("yaw").asFloat
        val pitch = obj.get("pitch").asFloat
        val world = Bukkit.getWorld(worldName) ?: return Bukkit.getWorlds().first().spawnLocation
        return Location(world, x, y, z, yaw, pitch)
    }
}