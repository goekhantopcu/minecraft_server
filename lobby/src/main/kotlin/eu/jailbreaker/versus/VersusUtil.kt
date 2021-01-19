package eu.jailbreaker.versus

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.inventory.ItemStack

fun ItemStack.toJsonObject(): JsonObject {
    val obj = JsonObject()

    obj.addProperty("type", type.name)
    obj.addProperty("amount", amount)

    val enchantments = JsonArray()
    this.enchantments.forEach { (ench, level) ->
        val enchantmentObj = JsonObject()
        enchantmentObj.addProperty("enchantment", ench.key.key)
        enchantmentObj.addProperty("level", level)
        enchantments.add(enchantmentObj)
    }
    obj.add("enchantments", enchantments)

    val flags = JsonArray()
    itemFlags.forEach { flags.add(it.name) }
    obj.add("flags", flags)

    lore?.let {
        val lore = JsonArray()
        it.forEach { line -> lore.add(line) }
        obj.add("lore", lore)
    }

    obj.addProperty("unbreakable", itemMeta.isUnbreakable)
    obj.addProperty("display", itemMeta.displayName)
    return obj
}