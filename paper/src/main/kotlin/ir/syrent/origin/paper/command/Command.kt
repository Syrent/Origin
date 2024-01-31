package ir.syrent.origin.paper.command

import ir.syrent.origin.paper.Origin
import ir.syrent.origin.paper.command.interfaces.CommandExtension
import ir.syrent.origin.paper.command.interfaces.SenderExtension
import ir.syrent.origin.paper.utils.ComponentUtils.component
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.incendo.cloud.Command
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.minecraft.extras.MinecraftHelp
import org.incendo.cloud.paper.PaperCommandManager

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
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(originSenderMapper, backwardsMapper),
        )

        manager.createHelpHandler()
        manager.registerAsynchronousCompletions()
        manager.registerBrigadier()

        help = MinecraftHelp.create(
            "/${name} help",
            manager,
            audienceMapper
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