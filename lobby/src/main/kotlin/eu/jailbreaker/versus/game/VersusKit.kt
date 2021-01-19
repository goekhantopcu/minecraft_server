package eu.jailbreaker.versus.game

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.nio.file.Files
import java.nio.file.Paths

class VersusKit(
    val name: String,
    val content: Array<ItemStack>,
    val armor: Array<ItemStack>
) {
    fun give(player: Player) {
        player.inventory.contents = content
        player.inventory.setArmorContents(armor)
        player.updateInventory()
    }

    fun save() {
        val path = Paths.get("plugins", "Versus", "kits.json")
        if (Files.notExists(path)) {
            javaClass.classLoader.getResourceAsStream("kits.json")?.let { Files.copy(it, path) }
        }
    }
}