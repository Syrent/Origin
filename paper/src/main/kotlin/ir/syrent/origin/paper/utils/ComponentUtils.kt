package ir.syrent.origin.paper.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

object ComponentUtils {

    fun component(message: String): Component {
        return MiniMessage.miniMessage().deserialize(message)
    }

    fun sendComponent(player: Player, component: Component) {
        return player.sendMessage(component)
    }

    @JvmName("componentExtension")
    fun String.component(): Component {
        return component(this)
    }

}