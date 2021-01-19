package eu.jailbreaker.chat

import com.destroystokyo.paper.profile.PlayerProfile
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.tehkode.permissions.PermissionGroup
import ru.tehkode.permissions.bukkit.PermissionsEx
import java.util.concurrent.CompletableFuture

fun PlayerProfile.toGameProfile(): GameProfile {
    val profile = GameProfile(id, name)
    properties.forEach {
        profile.properties.put(it.name, Property(it.name, it.value, it.signature))
    }
    return profile
}

fun PermissionGroup.formattedName(): String = name.capitalize()

fun PermissionGroup.color(): ChatColor = ChatColor.getByChar(getOption("color")?.get(0) ?: '7') ?: ChatColor.GRAY

fun PermissionGroup.rankId(): Int = getOption("rankId")?.toInt() ?: 9

fun PermissionGroup.scoreboardName(): String = "${rankId()}-${formattedName()}"

fun Player.permissionGroup(): PermissionGroup = PermissionsEx.getPermissionManager().getUser(player).parents.first()

fun Player.displayName(): String = "${permissionGroup().color()}$name"

fun sync(run: () -> Unit) {
    if (!Bukkit.isPrimaryThread()) {
        Bukkit.getScheduler().runTask(chatPlugin, run)
        return
    }
    run()
}

fun execute(run: () -> Unit): CompletableFuture<Void> {
    if (!Bukkit.isPrimaryThread()) {
        run()
        return CompletableFuture.completedFuture(null)
    }
    return CompletableFuture.runAsync(run)
}