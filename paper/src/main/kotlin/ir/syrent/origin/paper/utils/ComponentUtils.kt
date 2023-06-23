package ir.syrent.origin.paper.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

object ComponentUtils {

    fun component(message: String): Component {
        return MiniMessage.miniMessage().deserialize(message)
    }

    @JvmName("componentExtension")
    fun String.component(): Component {
        return component(this)
    }

}