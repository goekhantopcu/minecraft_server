package eu.jailbreaker.lobby

import eu.jailbreaker.lobby.actionbar.ActionbarCommand
import eu.jailbreaker.lobby.actionbar.ActionbarTask
import eu.jailbreaker.lobby.animatedblock.AnimatedBlockCommand
import eu.jailbreaker.lobby.animatedblock.AnimatedBlockTask
import eu.jailbreaker.lobby.dispenser.BukkitDispenserListener
import eu.jailbreaker.lobby.dispenser.DispenserCommand
import eu.jailbreaker.lobby.dispenser.DispenserTask
import eu.jailbreaker.lobby.fountain.FountainCommand
import eu.jailbreaker.lobby.fountain.FountainTask
import eu.jailbreaker.lobby.listener.BukkitJumpPadListener
import eu.jailbreaker.lobby.listener.BukkitMotdListener
import eu.jailbreaker.lobby.listener.BukkitPlayerListener
import eu.jailbreaker.lobby.player.destroyScoreboard
import eu.jailbreaker.lobby.player.initializeScoreboard
import eu.jailbreaker.lobby.player.scoreboard.ScoreboardAnimation
import eu.jailbreaker.lobby.protection.BuildCommand
import eu.jailbreaker.lobby.protection.exitBuildMode
import eu.jailbreaker.lobby.protection.listener.*
import eu.jailbreaker.lobby.secret.Secret
import eu.jailbreaker.lobby.secret.SecretCommand
import eu.jailbreaker.lobby.secret.SecretTask
import eu.jailbreaker.versus.VersusInitializer
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.LoadOrder
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.Website
import org.bukkit.plugin.java.annotation.plugin.author.Author
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

lateinit var lobbyPlugin: LobbyPlugin
val whitelistedEntities: Array<EntityType> = arrayOf(
    EntityType.FALLING_BLOCK,
    EntityType.ARMOR_STAND,
    EntityType.PLAYER,
    EntityType.FIREWORK,
    EntityType.ZOMBIE
)
val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(3)

@Author("JailBreaker")
@ApiVersion(ApiVersion.Target.v1_13)
@DependsOn(
    Dependency("Chat"),
    Dependency("PermissionsEx"),
    Dependency("PacketWrapper"),
    Dependency("ProtocolLib")
)
@Commands(
    Command(name = "dispenser"),
    Command(name = "build"),
    Command(name = "actionbar"),
    Command(name = "animatedblock"),
    Command(name = "fountain"),
    Command(name = "spawn"),
    Command(name = "secret"),
    Command(name = "versus")
)
@LoadOrder(PluginLoadOrder.POSTWORLD)
@Website("https://jailbreaker.eu")
@Plugin(name = "Lobby", version = "0.0.4")
class LobbyPlugin : JavaPlugin() {

    private lateinit var secretTask: ScheduledFuture<*>

    override fun onEnable() {
        lobbyPlugin = this

        getCommand("build")?.setExecutor(BuildCommand())
        getCommand("fountain")?.setExecutor(FountainCommand())
        getCommand("actionbar")?.setExecutor(ActionbarCommand())
        getCommand("dispenser")?.setExecutor(DispenserCommand())
        getCommand("animatedblock")?.setExecutor(AnimatedBlockCommand())
        getCommand("spawn")?.setExecutor(SpawnCommand())
        getCommand("secret")?.setExecutor(SecretCommand())

        // Custom
        server.pluginManager.registerEvents(BukkitPlayerListener(), this)
        server.pluginManager.registerEvents(BukkitJumpPadListener(), this)
        server.pluginManager.registerEvents(BukkitDispenserListener(), this)
        server.pluginManager.registerEvents(BukkitMotdListener(), this)

        VersusInitializer.initialize(this)

        server.scheduler.runTaskTimer(this, ScoreboardAnimation(), 0L, 2L)
        server.scheduler.runTaskTimer(this, DispenserTask(), 0L, 6000)
        server.scheduler.runTaskTimer(this, ActionbarTask(), 0L, 20L)
        server.scheduler.runTaskTimer(this, AnimatedBlockTask(), 0L, 2L)
        server.scheduler.runTaskTimer(this, FountainTask(), 0L, 15L)

        // Protection
        server.pluginManager.registerEvents(BlockListener(), this)
        server.pluginManager.registerEvents(EnchantmentListener(), this)
        server.pluginManager.registerEvents(EntityListener(), this)
        server.pluginManager.registerEvents(HangingListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(RaidListener(), this)
        server.pluginManager.registerEvents(ServerListener(), this)
        server.pluginManager.registerEvents(VehicleListener(), this)
        server.pluginManager.registerEvents(WeatherListener(), this)
        server.pluginManager.registerEvents(WorldListener(), this)

        server.worlds.forEach { world ->
            world.time = 1000
            world.isThundering = false
            world.setStorm(false)
            world.entities.filter { !whitelistedEntities.contains(it.type) || it.type == EntityType.ZOMBIE }
                .forEach { it.remove() }
        }

        server.onlinePlayers.forEach { player -> player.initializeScoreboard() }

        Bukkit.getConsoleSender().sendMessage("Loaded ${Secret.SECRETS.size} secrets")
        secretTask = scheduledExecutorService.scheduleAtFixedRate(SecretTask(), 0L, 25L, TimeUnit.MILLISECONDS)
    }

    override fun onDisable() {
        secretTask.cancel(true)
        server.onlinePlayers.forEach { player ->
            player.exitBuildMode()
            player.destroyScoreboard()
        }

        VersusInitializer.destroy()
        Secret.SECRETS.forEach { secret -> secret.armorStand.remove() }
    }
}