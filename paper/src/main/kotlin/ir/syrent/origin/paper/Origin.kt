package ir.syrent.origin.paper

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class Origin : OriginPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic
        registerListener(this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        fun registerListener(listener: Listener) {
            Bukkit.getServer().pluginManager.registerEvents(listener, instance)
        }

        fun runSync(runnable: Runnable, delay: Long = 0) {
            if (delay.toInt() != 0) {
                Bukkit.getScheduler().runTaskLater(instance, runnable, delay)
            } else {
                Bukkit.getScheduler().runTask(instance, runnable)
            }
        }

        fun broadcast(message: String) {
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize(message))
        }
    }

}
