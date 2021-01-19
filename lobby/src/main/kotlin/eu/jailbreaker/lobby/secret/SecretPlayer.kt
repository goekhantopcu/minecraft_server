package eu.jailbreaker.lobby.secret

import org.bukkit.Bukkit
import java.util.*

class SecretPlayer(
    val uniqueId: UUID,
    val selected: MutableList<Secret>
) {
    fun addSecret(secret: Secret) {
        selected.add(secret)
        Bukkit.getPlayer(uniqueId)?.sendMessage("Du hast ein Secret gefunden")
    }
}