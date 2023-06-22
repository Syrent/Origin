package ir.syrent.origin.paper.item.interfaces

import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 * Interface for a custom item in the Origin plugin.
 *
 * @param T the type of data associated with the item
 */
interface IItem<T> {

    /**
     * Sets a consumer function to be called when the item is used by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the OriginItemInterface instance for method chaining
     */
    fun onUse(consumer: Consumer<ItemStack>): IItem<T>

    /**
     * Sets a consumer function to be called when the item is used by a player.
     * The consumer function receives both the item stack and additional data associated with the item.
     *
     * @param consumer the consumer function to handle the event
     * @return the OriginItemInterface instance for method chaining
     */
    fun onUse(consumer: BiConsumer<ItemStack, T?>): IItem<T>

    /**
     * Sets a consumer function to be called when a specific action is performed on the item by a player.
     *
     * @param action the action performed on the item
     * @param consumer the consumer function to handle the event
     * @return the OriginItemInterface instance for method chaining
     */
    fun onAction(action: Action, consumer: Consumer<ItemStack>): IItem<T>

    /**
     * Sets a consumer function to be called when a specific action is performed on the item by a player.
     * The consumer function receives both the item stack and additional data associated with the item.
     *
     * @param action the action performed on the item
     * @param consumer the consumer function to handle the event
     * @return the OriginItemInterface instance for method chaining
     */
    fun onAction(action: Action, consumer: BiConsumer<ItemStack, T?>): IItem<T>

}