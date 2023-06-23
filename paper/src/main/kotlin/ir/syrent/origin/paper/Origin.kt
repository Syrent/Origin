package ir.syrent.origin.paper

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitTask

class Origin : OriginPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic
        registerListener(object : Listener {
            @EventHandler
            private fun onPlayerJoin(event: PlayerJoinEvent) {
                val player = event.player
            }
        })
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {

        private val recordedHasPluginSet = mutableSetOf<String>()

        fun registerListener(listener: Listener) {
            Bukkit.getServer().pluginManager.registerEvents(listener, instance)
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
            return instance
        }

        fun getServer(): Server {
            return instance.server
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
