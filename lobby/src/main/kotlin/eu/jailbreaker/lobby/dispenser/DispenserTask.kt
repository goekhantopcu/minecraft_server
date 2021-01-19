package eu.jailbreaker.lobby.dispenser

import eu.jailbreaker.lobby.warp.Warp
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.inventory.meta.FireworkMeta
import java.util.concurrent.ThreadLocalRandom

class DispenserTask : Runnable {

    private var dispensers: List<Warp> = Warp.WARPS.filter { it.name.startsWith("dispenser_") }
    private var updateTicker = 0

    override fun run() {
        updateTicker++
        if (updateTicker >= 10) {
            dispensers = Warp.WARPS.filter { it.name.startsWith("dispenser_") }
        }
        if (dispensers.isEmpty()) {
            return
        }
        dispensers.forEach { dispenser -> spawnFirework(dispenser.location.clone().add(0.5, 1.0, 0.5)) }
    }

    fun spawnFirework(location: Location): Firework {
        val colors = randomColors()
        val firework = location.world.spawnEntity(location, EntityType.FIREWORK) as Firework
        val meta: FireworkMeta = firework.fireworkMeta
        val effect = FireworkEffect.builder()
            .flicker(true)
            .withColor(colors.first)
            .withFade(colors.second)
            .with(FireworkEffect.Type.values().random())
            .trail(true)
            .build()
        meta.addEffect(effect)
        meta.power = 1
        firework.fireworkMeta = meta
        return firework
    }

    private fun randomColors(): Pair<Color, Color> {
        val color = Color.fromRGB(
            ThreadLocalRandom.current().nextInt(255),
            ThreadLocalRandom.current().nextInt(255),
            ThreadLocalRandom.current().nextInt(255)
        )
        return Pair(color, shadeColor(color, 40))
    }

    private fun shadeColor(color: Color, percent: Int) = Color.fromRGB(
        normalizeColor(color.red, percent),
        normalizeColor(color.green, percent),
        normalizeColor(color.blue, percent)
    )

    private fun normalizeColor(input: Int, percent: Int): Int {
        val value = (input * (100 + percent) / 100)
        return if (value < 255) value else 255
    }
}