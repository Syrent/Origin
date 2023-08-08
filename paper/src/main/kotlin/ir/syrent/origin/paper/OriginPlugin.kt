package ir.syrent.origin.paper

import org.bukkit.plugin.java.JavaPlugin

open class OriginPlugin : JavaPlugin() {

    init {
        instance = this
    }

    companion object {
        @JvmStatic
        lateinit var instance: OriginPlugin
    }
}