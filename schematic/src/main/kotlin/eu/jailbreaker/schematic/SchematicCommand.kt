package eu.jailbreaker.schematic

import com.google.common.base.Joiner
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.nio.file.Files
import java.nio.file.Paths

class SchematicCommand : CommandExecutor {

    private val helper = SchematicHelper()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }

        if (args.size == 1) {
            if (args[0] == "list") {
                sender.sendMessage(
                    "§8[§dSchematic§8] §7Liste: §d${
                        Joiner.on("§7, §d").join(helper.schematics.values.map { it.name }.toList())
                    }"
                )
            } else {
                sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic list")
                sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic save <Name>")
                sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic load <Name>")
                sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic paste <Name>")
                sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic remove <Name>")
            }
        } else if (args.size == 2) {
            when {
                args[0] == "save" -> {
                    if (!sender.hasMetadata("pos1") || !sender.hasMetadata("pos2")) {
                        sender.sendMessage("§8[§dSchematic§8] §cBitte markiere erst beide Ecken!")
                        return false
                    }

                    helper.save(
                        args[1],
                        sender.location,
                        sender.getMetadata("pos1").first().value() as Location,
                        sender.getMetadata("pos2").first().value() as Location
                    )
                    sender.sendMessage("§8[§dSchematic§8] §7Die Schematic §d${args[1]} §7wurde gespeichert")
                }
                args[0] == "load" -> {
                    if (helper.load(Paths.get("plugins", "Schematic", "${args[1]}.json"))) {
                        sender.sendMessage("§8[§dSchematic§8] §a${args[1]} wurde geladen!")
                    } else {
                        sender.sendMessage("§8[§dSchematic§8] §c${args[1]} wurde nicht gefunden!")
                    }
                }
                args[0] == "paste" -> {
                    helper.schematics[args[1].toLowerCase()]?.let {
                        val timestamp = System.currentTimeMillis()
                        sender.sendMessage("§8[§dSchematic§8] §7${args[1]} wird platziert!")
                        it.place(sender.location)
                        sender.sendMessage("§8[§dSchematic§8] §a${args[1]} wurde in §d${System.currentTimeMillis() - timestamp}ms §7platziert!")
                    } ?: sender.sendMessage("§8[§dSchematic§8] §cSchematic wurde nicht gefunden!")
                }
                args[0] == "remove" -> {
                    helper.schematics[args[1].toLowerCase()]?.let {
                        try {
                            Files.delete(Paths.get(schematicPlugin.dataFolder.toString(), "${args[1]}.json"))
                            sender.sendMessage("§8[§dSchematic§8] §a${args[1]} wurde gelöscht!")
                        } catch (t: Throwable) {
                            t.printStackTrace()
                            sender.sendMessage("§8[§dSchematic§8] §cSchematic konnte nicht gelöscht werden!")
                        }
                    } ?: sender.sendMessage("§8[§dSchematic§8] §cSchematic wurde nicht gefunden!")
                }
                else -> {
                    sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic list")
                    sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic save <Name>")
                    sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic load <Name>")
                    sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic paste <Name>")
                    sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic remove <Name>")
                }
            }
        } else {
            sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic list")
            sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic save <Name>")
            sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic load <Name>")
            sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic paste <Name>")
            sender.sendMessage("§8[§dSchematic§8] §7Verwende: /schematic remove <Name>")
        }
        return true
    }
}