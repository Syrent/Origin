package ir.syrent.origin.paper

import ir.syrent.origin.paper.utils.ComponentUtils.component
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask

class Origin {
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
        fun getTypedServer(): Server {
            return OriginPlugin.instance.server
        }

        @JvmStatic
        fun getOnlinePlayers(): Collection<Player> {
            return getTypedServer().onlinePlayers
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
        fun log(message: Component) {
            Audience.audience(Bukkit.getConsoleSender()).sendMessage("<gray>[${getPlugin().name}] ".component().append("[INFO] ".component()).append(message))
        }

        @JvmStatic
        fun warn(message: String) {
            Bukkit.getLogger().warning(message)
        }

        @JvmStatic
        fun warn(message: Component) {
            Audience.audience(Bukkit.getConsoleSender()).sendMessage("<yellow>[${getPlugin().name}] ".component().append("[WARN] ".component()).append(message))
        }

        @JvmStatic
        fun error(message: String) {
            Bukkit.getLogger().severe(message)
        }

        @JvmStatic
        fun error(message: Component) {
            Audience.audience(Bukkit.getConsoleSender()).sendMessage("<dark_red>[${getPlugin().name}] ".component().append("[ERROR] ".component()).append(message))
        }

        @JvmStatic
        fun hasPlugin(plugin: String): Boolean {
            return if (recordedHasPluginSet.contains(plugin)) true else {
                if (getTypedServer().pluginManager.getPlugin(plugin) != null && getTypedServer().pluginManager.isPluginEnabled(plugin)
                ) {
                    recordedHasPluginSet.add(plugin)
                    true
                } else false
            }
        }
    }

}
