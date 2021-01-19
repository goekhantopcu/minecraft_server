package eu.jailbreaker.chat.player.extension

import com.comphenix.packetwrapper.WrapperPlayServerPlayerListHeaderFooter
import com.comphenix.protocol.wrappers.WrappedChatComponent
import eu.jailbreaker.chat.player.ChatPlayer
import org.bukkit.Bukkit

interface TablistExtension : ScoreboardExtension {

    fun setHeader(header: String) {
        val packet = WrapperPlayServerPlayerListHeaderFooter()
        packet.header = WrappedChatComponent.fromText(header)
        packet.sendPacket(player())
    }

    fun setFooter(footer: String) {
        val packet = WrapperPlayServerPlayerListHeaderFooter()
        packet.footer = WrappedChatComponent.fromText(footer)
        packet.sendPacket(player())
    }

    fun setHeaderAndFooter(header: String, footer: String) {
        val packet = WrapperPlayServerPlayerListHeaderFooter()
        packet.header = WrappedChatComponent.fromText(header)
        packet.footer = WrappedChatComponent.fromText(footer)
        packet.sendPacket(player())
    }

    fun addEntries() {
        val entry = TablistEntry.of(player())
        Bukkit.getOnlinePlayers().forEach { player ->
            addEntry(TablistEntry.of(player))
            if (player.uniqueId != uniqueId()) {
                ChatPlayer.find(player)?.addEntry(entry)
            }
        }
    }

    fun removeEntries() {
        val entry = TablistEntry.of(player())
        Bukkit.getOnlinePlayers().forEach { player ->
            removeEntry(TablistEntry.of(player))
            if (player.uniqueId != uniqueId()) {
                ChatPlayer.find(player)?.removeEntry(entry)
            }
        }
    }

    fun addEntry(entry: TablistEntry) {
        addEntryToTeam(entry.name(), entry.team)
    }

    fun removeEntry(entry: TablistEntry) {
        removeEntryFromTeam(entry.name(), entry.team)
    }

    fun updateEntry(entry: TablistEntry) {
        removeEntry(entry)
        addEntry(entry)
    }
}