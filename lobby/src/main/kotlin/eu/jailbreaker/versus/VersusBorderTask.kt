package eu.jailbreaker.versus

import eu.jailbreaker.versus.game.VersusGame
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle
import kotlin.math.cos
import kotlin.math.sin

class VersusBorderTask(val game: VersusGame) : Runnable {
    private val radius = game.arena.radius

    override fun run() {
        var phi = 0.0
        while (phi <= Math.PI) {
            phi += Math.PI / 25
            var theta = 0.0
            while (theta <= 2 * Math.PI) {
                val max = 30
                val min = 5
                val j = min + ((max - min) / (Math.PI / 2)) * phi
                theta += Math.PI / j
                val r = radius
                val x = r * cos(theta) * sin(phi)
                val y = r * cos(phi) + 1.5
                val z = r * sin(theta) * sin(phi)

                val loc = game.arena.middle.clone().add(x, y, z)
                Bukkit.getOnlinePlayers().forEach { player ->
                    player.spawnParticle(
                        Particle.REDSTONE,
                        loc.x,
                        loc.y,
                        loc.z,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        Particle.DustOptions(Color.GRAY, 2F)
                    )
                }
            }
        }
    }
}