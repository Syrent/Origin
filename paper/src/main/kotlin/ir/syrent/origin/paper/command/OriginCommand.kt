package ir.syrent.origin.paper.command

import cloud.commandframework.Command
import cloud.commandframework.bukkit.BukkitCommandManager
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler
import cloud.commandframework.minecraft.extras.MinecraftHelp
import cloud.commandframework.paper.PaperCommandManager
import ir.syrent.origin.paper.Origin
import ir.syrent.origin.paper.command.interfaces.CommandExtension
import ir.syrent.origin.paper.command.interfaces.SenderExtension
import ir.syrent.origin.paper.utils.ComponentUtils.component
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

abstract class OriginCommand(
    val name: String,
    vararg val aliases: String
) : CommandExtension {

    private var errorPrefix = "<dark_gray>[</dark_gray><dark_red><bold>✘</bold></dark_red><dark_gray>]</dark_gray><gradient:dark_red:red>".component()

    var manager: PaperCommandManager<SenderExtension>
    var builder: Command.Builder<SenderExtension>
    var help: MinecraftHelp<SenderExtension>

    init {
        val originSenderMapper = { commandSender: CommandSender -> OriginSenderExtension(commandSender) }
        val backwardsMapper = { sayanSenderExtension: SenderExtension -> sayanSenderExtension.sender() }
        val audienceMapper = { sayanSenderExtension: SenderExtension -> Audience.audience(sayanSenderExtension.sender()) }

        manager = PaperCommandManager(
            Origin.getPlugin(),
            CommandExecutionCoordinator.simpleCoordinator(),
            originSenderMapper,
            backwardsMapper
        )

        manager.createCommandHelpHandler()
        manager.registerAsynchronousCompletions()

        try {
            manager.registerBrigadier()
        } catch (_: BukkitCommandManager.BrigadierFailureException) {
            Origin.warn("Failed to enable mojang brigadier commands.")
        }

        MinecraftExceptionHandler<SenderExtension>()
            .withArgumentParsingHandler()
            .withInvalidSenderHandler()
            .withInvalidSyntaxHandler()
            .withNoPermissionHandler()
            .withCommandExecutionHandler()
            .withDecorator { message -> errorPrefix.append(Component.space()).append(message) }
            .apply(manager, audienceMapper)

        help = MinecraftHelp(
            "/${name} help",
            audienceMapper,
            manager
        )

        builder = manager.commandBuilder(name, *aliases).permission(constructBasePermission(name))
    }

    override fun errorPrefix(): Component {
        return errorPrefix
    }

    override fun errorPrefix(prefix: Component) {
        errorPrefix = prefix
    }
}