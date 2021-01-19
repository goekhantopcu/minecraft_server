package eu.jailbreaker.schematic

import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.LoadOrder
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.Website
import org.bukkit.plugin.java.annotation.plugin.author.Author

lateinit var schematicPlugin: SchematicPlugin

@Author("JailBreaker")
@ApiVersion(ApiVersion.Target.v1_13)
@LoadOrder(PluginLoadOrder.POSTWORLD)
@Website("https://jailbreaker.eu")
@Commands(Command(name = "schematic"))
@Plugin(name = "Schematic", version = "0.0.2")
class SchematicPlugin : JavaPlugin() {

    override fun onEnable() {
        dataFolder.mkdirs()
        schematicPlugin = this
        server.pluginManager.registerEvents(SchematicListener(), this)
        getCommand("schematic")?.setExecutor(SchematicCommand())
    }
}