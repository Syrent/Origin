package ir.syrent.origin.paper.item

import ir.syrent.origin.paper.Origin
import ir.syrent.origin.paper.item.interfaces.IItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
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
open class Item<T>(
    val itemStack: ItemStack,
    val data: T? = null
) : IItem<T>, Listener {

    /**
     * Initializes the Origin item by registering the necessary event listener.
     */
    init {
        Origin.registerListener(this)
    }

    // Event consumers for item use
    private var onUseConsumer: Consumer<ItemStack>? = null
    private var onUseBiConsumer: BiConsumer<ItemStack, T?>? = null
    private var onActionConsumer: Pair<Action, Consumer<ItemStack>>? = null
    private var onActionBiConsumer: Pair<Action, BiConsumer<ItemStack, T?>>? = null

    override fun onUse(consumer: Consumer<ItemStack>): Item<T> {
        onUseConsumer = consumer
        return this
    }

    override fun onUse(consumer: BiConsumer<ItemStack, T?>): Item<T> {
        onUseBiConsumer = consumer
        return this
    }

    override fun onAction(action: Action, consumer: Consumer<ItemStack>): Item<T> {
        onActionConsumer = Pair(action, consumer)
        return this
    }

    override fun onAction(action: Action, consumer: BiConsumer<ItemStack, T?>): Item<T> {
        onActionBiConsumer = Pair(action, consumer)
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
                onUseConsumer?.accept(it)
                onUseBiConsumer?.accept(it, data)
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
        fun <T> ItemStack.toOriginItem(): Item<T> {
            return Item(this)
        }
    }
}
