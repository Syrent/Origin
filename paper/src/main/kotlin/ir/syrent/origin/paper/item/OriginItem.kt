package ir.syrent.origin.paper.item

import ir.syrent.origin.paper.Origin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 * Represents a custom item in the Origin plugin.
 *
 * @param T the type of data associated with the item
 * @param itemStack the item stack representing the item
 * @param data additional data associated with the item (optional)
 */
class OriginItem<T>(
    val itemStack: ItemStack,
    val data: T? = null
) : Listener {

    /**
     * Initializes the Origin item by registering the necessary event listener.
     */
    init {
        Origin.registerListener(this)
    }

    // Event consumers for item use
    private var useConsumer: Consumer<ItemStack>? = null
    private var useBiConsumer: BiConsumer<ItemStack, T?>? = null

    /**
     * Sets a consumer function to be called when the item is used by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the OriginItem instance for method chaining
     */
    fun onUse(consumer: Consumer<ItemStack>): OriginItem<T> {
        useConsumer = consumer
        return this
    }

    /**
     * Sets a consumer function to be called when the item is used by a player.
     * The consumer function receives both the item stack and additional data associated with the item.
     *
     * @param consumer the consumer function to handle the event
     * @return the OriginItem instance for method chaining
     */
    fun onUse(consumer: BiConsumer<ItemStack, T?>): OriginItem<T> {
        useBiConsumer = consumer
        return this
    }

    /**
     * Handles the event when the item is used by a player.
     * Calls the registered consumer functions with the item stack and associated data (if any).
     *
     * @param event the PlayerInteractEvent triggered by the item use
     */
    @EventHandler
    private fun onItemUse(event: PlayerInteractEvent) {
        event.item?.let {
            if (it == itemStack) {
                useConsumer?.accept(it)
                useBiConsumer?.accept(it, data)
            }
        }
    }

    companion object {
        /**
         * Converts an ItemStack to an OriginItem.
         *
         * @param T the type of data associated with the item
         * @receiver the ItemStack to convert
         * @return the created OriginItem instance
         */
        fun <T> ItemStack.toOriginItem(): OriginItem<T> {
            return OriginItem(this)
        }
    }
}
