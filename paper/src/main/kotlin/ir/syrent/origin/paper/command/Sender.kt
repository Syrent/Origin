package ir.syrent.origin.paper.command

import ir.syrent.origin.paper.command.interfaces.ISender
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

open class Sender(
    private var commandSender: CommandSender
): ISender {

    var ONLY_PLAYERS_MESSAGE = MiniMessage.miniMessage().deserialize("<dark_gray>[</dark_gray><dark_red><bold>✘</bold></dark_red><dark_gray>]</dark_gray><gradient:dark_red:red> Only players can use this command.")

    override fun player(): Player? {
        if (commandSender is Player) return (commandSender as Player).player

        commandSender.sendMessage(ONLY_PLAYERS_MESSAGE)
        return null
    }

    override fun audience(): Audience {
        return Audience.audience(commandSender)
    }

    override fun setSender(sender: CommandSender) {
        commandSender = sender
    }

    override fun getSender(): CommandSender {
        return commandSender
    }

    override fun sentOnlyPlayersMessage(message: Component) {
        ONLY_PLAYERS_MESSAGE = message
    }

}