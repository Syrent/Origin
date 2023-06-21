package ir.syrent.origin.paper.gui.interfaces

import ir.syrent.origin.paper.gui.GUIItem
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer

/**
 * Represents a graphical user interface (GUI) in a Paper plugin.
 * This interface defines the contract for GUI implementations.
 * Implementing classes should provide the necessary functionality to create and manage GUIs.
 *
 * @param T the type of data associated with the GUI
 */
interface GUI<T> : InventoryHolder {

    /**
     * Updates the GUI by refreshing its contents.
     *
     * @return the updated GUI instance for method chaining
     */
    fun update(): GUI<T>

    /**
     * Sets an inventory item at the specified slot in the GUI.
     *
     * @param item the inventory item to set
     * @return the GUI instance for method chaining
     */
    fun setItem(item: GUIItem<T>): GUI<T>

    /**
     * Removes the inventory item at the specified slot from the GUI.
     *
     * @param slot the slot to remove the item from
     * @return the GUI instance for method chaining
     */
    fun removeItem(slot: Int): GUI<T>

    /**
     * Clears all inventory items from the GUI.
     *
     * @return the GUI instance for method chaining
     */
    fun clear(): GUI<T>

    /**
     * Fills a range of slots in the GUI with the specified item stack.
     *
     * @param item the item stack to fill the range with
     * @param range the range of slots to fill (default: all slots)
     * @return the GUI instance for method chaining
     */
    fun fill(item: ItemStack? = null, range: IntRange = 0 until inventory.size - 1): GUI<T>

    /**
     * Fills a range of slots in the GUI with the specified item stack.
     *
     * @param item the item stack to fill the range with
     * @param from the start of slots to fill (default: 0)
     * @param to the start of slots to fill (default: inventory size - 1)
     * @return the GUI instance for method chaining
     */
    fun fill(item: ItemStack? = null, from: Int = 0, to: Int = inventory.size - 1): GUI<T> {
        fill(item, IntRange(from, to))
        return this
    }

    /**
     * Sets a consumer function to be called when the GUI is opened by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onOpen(consumer: BiConsumer<Player?, Inventory>): GUI<T>

    /**
     * Sets a consumer function to be called when a player clicks on an item in the GUI.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onClick(consumer: BiConsumer<Player?, GUIItem<T>>): GUI<T>

    /**
     * Sets a consumer function to be called when the GUI is closed by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onClose(consumer: BiConsumer<Player?, Inventory>): GUI<T>

    /**
     * Disables or enables clicking on GUI items.
     *
     * @param disableClick true to disable clicking, false to enable it (default: true)
     * @return the GUI instance for method chaining
     */
    fun disableClick(disableClick: Boolean = true): GUI<T>

}