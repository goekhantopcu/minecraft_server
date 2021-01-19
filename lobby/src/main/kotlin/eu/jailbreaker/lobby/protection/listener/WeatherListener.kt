package eu.jailbreaker.lobby.protection.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.weather.LightningStrikeEvent
import org.bukkit.event.weather.ThunderChangeEvent
import org.bukkit.event.weather.WeatherChangeEvent

class WeatherListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onLightningStrike(event: LightningStrikeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onThunderChange(event: ThunderChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onWeatherChange(event: WeatherChangeEvent) {
        event.isCancelled = true
    }
}