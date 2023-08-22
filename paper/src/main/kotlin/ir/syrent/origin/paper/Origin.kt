package ir.syrent.origin.paper

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class Origin : JavaPlugin() {
    companion object {
        private val recordedHasPluginSet = mutableSetOf<String>()

        @JvmStatic
        fun registerListener(listener: Listener) {
            Bukkit.getServer().pluginManager.registerEvents(listener, OriginPlugin.instance)
        }

        @JvmStatic
        fun runSync(runnable: Runnable): BukkitTask {
            return Bukkit.getScheduler().runTask(getPlugin(), runnable)
        }

        @JvmStatic
        fun runSync(runnable: Runnable, delay: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay)
        }

        @JvmStatic
        fun runSync(runnable: Runnable, delay: Long, period: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period)
        }

        @JvmStatic
        fun runAsync(runnable: Runnable): BukkitTask {
            return Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), runnable)
        }

        @JvmStatic
        fun runAsync(runnable: Runnable, delay: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), runnable, delay)
        }

        @JvmStatic
        fun runAsync(runnable: Runnable, delay: Long, period: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), runnable, delay, period)
        }

        @JvmStatic
        fun getPlugin(): OriginPlugin {
            return OriginPlugin.instance
        }

        @JvmStatic
        fun getServer(): Server {
            return OriginPlugin.instance.server
        }

        @JvmStatic
        fun getOnlinePlayers(): Collection<Player> {
            return getServer().onlinePlayers
        }

        @JvmStatic
        fun broadcast(message: String) {
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize(message))
        }

        @JvmStatic
        fun log(message: String) {
            Bukkit.getLogger().info(message)
        }

        @JvmStatic
        fun warn(message: String) {
            Bukkit.getLogger().warning(message)
        }

        @JvmStatic
        fun error(message: String) {
            Bukkit.getLogger().severe(message)
        }

        @JvmStatic
        fun hasPlugin(plugin: String): Boolean {
            return if (recordedHasPluginSet.contains(plugin)) true else {
                if (getServer().pluginManager.getPlugin(plugin) != null && getServer().pluginManager.isPluginEnabled(plugin)
                ) {
                    recordedHasPluginSet.add(plugin)
                    true
                } else false
            }
        }
    }

}
