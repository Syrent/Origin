package ir.syrent.origin.paper

import org.bukkit.Bukkit
import org.bukkit.event.Listener

class Origin : OriginPlugin() {

    override fun onEnable() {
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        fun registerListener(listener: Listener) {
            Bukkit.getServer().pluginManager.registerEvents(listener, instance)
        }
    }

}
