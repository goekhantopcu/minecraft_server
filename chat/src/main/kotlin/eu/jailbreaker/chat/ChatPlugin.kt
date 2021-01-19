package eu.jailbreaker.chat

import eu.jailbreaker.chat.listener.BukkitChatListener
import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.LoadOrder
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.Website
import org.bukkit.plugin.java.annotation.plugin.author.Author

lateinit var chatPlugin: ChatPlugin

@Author("JailBreaker")
@ApiVersion(ApiVersion.Target.v1_13)
@LoadOrder(PluginLoadOrder.POSTWORLD)
@Website("https://jailbreaker.eu")
@DependsOn(
    Dependency("PermissionsEx"),
    Dependency("PacketWrapper"),
    Dependency("ProtocolLib")
)
@Plugin(name = "Chat", version = "0.0.3")
class ChatPlugin : JavaPlugin() {

    private var timer = 0

    override fun onEnable() {
        chatPlugin = this
        server.pluginManager.registerEvents(BukkitChatListener(), this)
    }
}