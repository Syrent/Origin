package ir.syrent.origin.paper

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask

class Origin {
    companion object {
        private val recordedHasPluginSet = mutableSetOf<String>()

        fun registerListener(listener: Listener) {
            Bukkit.getServer().pluginManager.registerEvents(listener, OriginPlugin.instance)
        }

        fun runSync(runnable: Runnable): BukkitTask {
            return Bukkit.getScheduler().runTask(getPlugin(), runnable)
        }

        fun runSync(runnable: Runnable, delay: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay)
        }

        fun runSync(runnable: Runnable, delay: Long, period: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period)
        }

        fun runAsync(runnable: Runnable): BukkitTask {
            return Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), runnable)
        }

        fun runAsync(runnable: Runnable, delay: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), runnable, delay)
        }

        fun runAsync(runnable: Runnable, delay: Long, period: Long): BukkitTask {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), runnable, delay, period)
        }

        fun getPlugin(): OriginPlugin {
            return OriginPlugin.instance
        }

        fun getServer(): Server {
            return OriginPlugin.instance.server
        }

        fun getOnlinePlayers(): Collection<Player> {
            return getServer().onlinePlayers
        }

        fun broadcast(message: String) {
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize(message))
        }

        fun log(message: String) {
            Bukkit.getLogger().info(message)
        }

        fun warn(message: String) {
            Bukkit.getLogger().warning(message)
        }

        fun error(message: String) {
            Bukkit.getLogger().severe(message)
        }

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
