package eu.jailbreaker.lobby

import org.bukkit.Bukkit
import java.util.concurrent.CompletableFuture

fun sync(run: () -> Unit) {
    if (!Bukkit.isPrimaryThread()) {
        Bukkit.getScheduler().runTask(lobbyPlugin, run)
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