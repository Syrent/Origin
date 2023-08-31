package ir.syrent.origin.paper.command

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.bukkit.BukkitCommandManager
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler
import cloud.commandframework.minecraft.extras.MinecraftHelp
import cloud.commandframework.paper.PaperCommandManager
import ir.syrent.origin.paper.Origin
import ir.syrent.origin.paper.command.interfaces.ICommand
import ir.syrent.origin.paper.command.interfaces.ISender
import ir.syrent.origin.paper.utils.ComponentUtils.component
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

abstract class Command(
    val name: String,
    vararg val aliases: String
) : ICommand {

    var ERROR_PREFIX = "<dark_gray>[</dark_gray><dark_red><bold>✘</bold></dark_red><dark_gray>]</dark_gray><gradient:dark_red:red>"

    var manager: PaperCommandManager<ISender>
    var builder: Command.Builder<ISender>
    var help: MinecraftHelp<ISender>

    init {
        val senderMapper = { commandSender: CommandSender -> Sender(commandSender) }
        val backwardsMapper = { sayanSender: ISender -> sayanSender.getSender() }
        val audienceMapper = { sayanSender: ISender -> Audience.audience(sayanSender.getSender()) }

        manager = PaperCommandManager(
            Origin.getPlugin(),
            CommandExecutionCoordinator.simpleCoordinator(),
            senderMapper,
            backwardsMapper
        )

        manager.createCommandHelpHandler()

        try {
            manager.registerBrigadier()
        } catch (_: BukkitCommandManager.BrigadierFailureException) {
            Origin.warn("Failed to enable mojang brigadier commands.")
        }

        MinecraftExceptionHandler<ISender>()
            .withArgumentParsingHandler()
            .withInvalidSenderHandler()
            .withInvalidSyntaxHandler()
            .withNoPermissionHandler()
            .withCommandExecutionHandler()
            .withDecorator { message -> ERROR_PREFIX.component().append(Component.space()).append(message) }
            .apply(manager, audienceMapper)

        help = MinecraftHelp(
            "/${name} help",
            audienceMapper,
            manager
        )

        builder = manager.commandBuilder(name, *aliases).permission(getPermission(name))
    }

    fun addLiteral(name: String, description: ArgumentDescription? = null, vararg aliases: String): Command.Builder<ISender> {
        return builder.literal(name, description ?: ArgumentDescription.empty(), *aliases)
    }

    fun saveCommand(command: Command<ISender>) {
        manager.command(command)
        manager.commandRegistrationHandler().registerCommand(command)
    }

    fun saveCommand(commandBuilder: Command.Builder<ISender>) {
        saveCommand(commandBuilder.build())
    }

    override fun setErrorPrefix(prefix: String) {
        ERROR_PREFIX = prefix
    }
}