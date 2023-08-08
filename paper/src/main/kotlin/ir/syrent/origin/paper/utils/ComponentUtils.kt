package ir.syrent.origin.paper.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

object ComponentUtils {

    @JvmStatic
    fun component(message: String): Component {
        return MiniMessage.miniMessage().deserialize(message)
    }

    @JvmStatic
    fun sendComponent(player: Player, component: Component) {
        return player.sendMessage(component)
    }

    @JvmStatic
    @JvmName("componentExtension")
    fun String.component(): Component {
        return component(this)
    }

}