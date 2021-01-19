package eu.jailbreaker.lobby

import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemBuilder(
    private var item: ItemStack = ItemStack(Material.STONE)
) {
    constructor(material: Material) : this(ItemStack(material))

    fun displayName(displayName: String): ItemBuilder {
        val meta = item.itemMeta
        meta.setDisplayName(displayName)
        item.itemMeta = meta
        return this
    }

    fun displayName(vararg displayName: BaseComponent): ItemBuilder {
        val meta = item.itemMeta
        meta.setDisplayNameComponent(displayName)
        item.itemMeta = meta
        return this
    }

    fun unbreakable(): ItemBuilder {
        val meta = item.itemMeta
        meta.isUnbreakable = true
        item.itemMeta = meta
        return this
    }

    fun breakable(): ItemBuilder {
        val meta = item.itemMeta
        meta.isUnbreakable = false
        item.itemMeta = meta
        return this
    }

    fun itemFlags(vararg itemFlag: ItemFlag): ItemBuilder {
        val meta = item.itemMeta
        meta.addItemFlags(*itemFlag)
        item.itemMeta = meta
        return this
    }

    fun enchant(enchantment: Enchantment, level: Int): ItemBuilder {
        val meta = item.itemMeta
        try {
            item.addEnchantment(enchantment, level)
        } catch (throwable: Throwable) {
            item.addUnsafeEnchantment(enchantment, level)
        }
        item.itemMeta = meta
        return this
    }

    fun enchant(enchantments: Map<Enchantment, Int>): ItemBuilder {
        enchantments.forEach { (enchantment, level) -> enchant(enchantment, level) }
        return this
    }

    fun create(): ItemStack = this.item
}