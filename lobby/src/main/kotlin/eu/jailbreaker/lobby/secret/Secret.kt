package eu.jailbreaker.lobby.secret

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.lang.reflect.Field
import java.util.*

class Secret private constructor(
    val id: Int,
    val location: Location
) {
    companion object {
        val SECRETS: MutableList<Secret> =
            Warp.WARPS.filter { it.name.startsWith("secret_") }.map { Secret(it) }.toMutableList()

        fun create(location: Location): Secret {
            val secret = Secret(SECRETS.size + 1, location)
            SECRETS.add(secret)
            val properties = Properties()
            properties.setProperty("id", secret.id.toString())
            Warp("secret_$secret", location, properties).save()
            return secret
        }

        fun remove(id: Int) {
            if (SECRETS.removeIf { it.id == id }) {
                Warp.remove("secret_$id")
            }
        }
    }

    private constructor(warp: Warp) : this(
        warp.properties.getProperty("id", "-1").toInt(),
        warp.location
    )

    var armorStand: ArmorStand = spawnArmorStand()

    private fun spawnArmorStand(): ArmorStand {
        val loc = location.clone().add(0.0, 0.75, 0.0)
        val armorStand = loc.world.spawnEntity(loc, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.customName = "secret_$id"
        armorStand.isCustomNameVisible = true
        armorStand.isVisible = false
        armorStand.isSmall = true
        armorStand.isCollidable = false
        armorStand.setCanTick(false)
        armorStand.setGravity(false)
        armorStand.setItem(
            EquipmentSlot.HEAD,
            getSkull("http://textures.minecraft.net/texture/f80641f8823d838474ed272bea8bb709c15149ae9aacb238d86a5155cc2450af")
        )
        return armorStand
    }

    fun rotate(yaw: Double) {
        armorStand.setRotation(Math.toRadians(yaw).toFloat(), 0F)
    }

    fun getSkull(url: String): ItemStack {
        val head = ItemStack(Material.PLAYER_HEAD)
        if (url.isEmpty()) {
            return head
        }

        val headMeta = head.itemMeta as SkullMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        val encodedData: ByteArray =
            Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).toByteArray())

        profile.properties.put("textures", Property("textures", String(encodedData)))
        val profileField: Field?
        try {
            profileField = headMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(headMeta, profile)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        head.itemMeta = headMeta
        return head
    }
}