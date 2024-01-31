package ir.syrent.origin.paper.command

import ir.syrent.origin.paper.command.interfaces.SenderExtension
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

open class OriginSenderExtension(
    private var commandSender: CommandSender
): SenderExtension {

    private var onlinePlayersMessage = Component.text("Only players can use this command.")
        .color(TextColor.color(192, 32, 16))
        .asComponent()

    override fun player(): Player? {
        if (commandSender is Player) return (commandSender as Player).player

        commandSender.sendMessage(onlinePlayersMessage)
        return null
    }

    override fun audience(): Audience {
        return Audience.audience(commandSender)
    }

    override fun sender(sender: CommandSender) {
        commandSender = sender
    }

    override fun sender(): CommandSender {
        return commandSender
    }

    override fun sentOnlyPlayersMessage(message: Component) {
        onlinePlayersMessage = message
    }

}