package ir.syrent.origin.paper.gui

import ir.syrent.origin.paper.Origin
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer

/**
 * Represents a graphical user interface (GUI) in a Bukkit plugin.
 * This class provides utility methods for creating and managing GUIs.
 *
 * @param T the type of data associated with the GUI
 * @param title the title of the GUI, displayed to the player
 * @param type the type of inventory for the GUI (default: CHEST)
 * @param size the size of the inventory (default: 54 slots)
 * @param owner the inventory holder for the GUI (optional)
 */
open class GUI<T>(
    val title: Component,
    val type: InventoryType = InventoryType.CHEST,
    val size: Int = 54,
    val owner: InventoryHolder? = null
) : InventoryHolder {

    // The underlying inventory object
    private val inventory =
        if (type == InventoryType.CHEST) {
            Bukkit.createInventory(owner, size, title)
        } else {
            Bukkit.createInventory(owner, type, title)
        }

    // A list of inventory items associated with the GUI
    private val items = mutableListOf<InventoryItem<T>>()

    // Flag to determine whether clicking on GUI items is disabled
    private var disableClick = false

    /**
     * Initializes the GUI by registering the necessary event listeners.
     */
    init {
        registerListener()
    }

    /**
     * Updates the GUI by setting the item stacks for all inventory items.
     *
     * @return the GUI instance for method chaining
     */
    fun update(): GUI<T> {
        items.forEach { item ->
            inventory.setItem(item.slot, item.itemStack)
        }
        return this
    }

    /**
     * Sets an inventory item at the specified slot in the GUI.
     *
     * @param item the inventory item to set
     * @return the GUI instance for method chaining
     */
    fun setItem(item: InventoryItem<T>): GUI<T> {
        inventory.setItem(item.slot, item.itemStack)
        items.add(item)
        return this
    }

    /**
     * Removes the inventory item at the specified slot from the GUI.
     *
     * @param slot the slot to remove the item from
     * @return the GUI instance for method chaining
     */
    fun removeItem(slot: Int): GUI<T> {
        inventory.setItem(slot, null)
        items.removeIf { it.slot == slot }
        return this
    }

    /**
     * Clears all inventory items from the GUI.
     *
     * @return the GUI instance for method chaining
     */
    fun clear(): GUI<T> {
        items.clear()
        inventory.clear()
        return this
    }

    /**
     * Fills a range of slots in the GUI with the specified item stack.
     *
     * @param item the item stack to fill the range with
     * @param range the range of slots to fill (default: all slots)
     * @return the GUI instance for method chaining
     */
    fun fill(item: ItemStack? = null, range: IntRange = 0 until inventory.size): GUI<T> {
        range.forEach {
            setItem(InventoryItem(it, item))
        }
        return this
    }

    // Event consumers for GUI events
    private var onOpenConsumer: BiConsumer<Player?, Inventory>? = null
    private var onClickConsumer: BiConsumer<Player?, InventoryItem<T>>? = null
    private var onCloseConsumer: BiConsumer<Player?, Inventory>? = null

    /**
     * Sets a consumer function to be called when the GUI is opened by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onOpen(consumer: BiConsumer<Player?, Inventory>): GUI<T> {
        onOpenConsumer = consumer
        return this
    }

    /**
     * Sets a consumer function to be called when a player clicks on an item in the GUI.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onClick(consumer: BiConsumer<Player?, InventoryItem<T>>): GUI<T> {
        onClickConsumer = consumer
        return this
    }

    /**
     * Sets a consumer function to be called when the GUI is closed by a player.
     *
     * @param consumer the consumer function to handle the event
     * @return the GUI instance for method chaining
     */
    fun onClose(consumer: BiConsumer<Player?, Inventory>): GUI<T> {
        onCloseConsumer = consumer
        return this
    }

    /**
     * Called when the GUI is opened by a player.
     * Override this method to add custom behavior.
     *
     * @param player the player who opened the GUI
     * @param inventory the inventory associated with the GUI
     */
    open fun onOpen(player: Player, inventory: Inventory) {
        // Add custom behavior here
    }

    /**
     * Called when a player clicks on an item in the GUI.
     * Override this method to add custom behavior.
     *
     * @param player the player who clicked on the item
     * @param item the inventory item that was clicked
     * @param clickType the type of click event
     * @return true if the event should be canceled, false otherwise
     */
    open fun onClick(player: Player, item: InventoryItem<T>, clickType: ClickType): Boolean {
        // Add custom behavior here
        return disableClick
    }

    /**
     * Called when the GUI is closed by a player.
     * Override this method to add custom behavior.
     *
     * @param player the player who closed the GUI
     * @param inventory the inventory associated with the GUI
     */
    open fun onClose(player: Player, inventory: Inventory) {
        // Add custom behavior here
    }

    /**
     * Disables or enables clicking on GUI items.
     *
     * @param disableClick true to disable clicking, false to enable it (default: true)
     * @return the GUI instance for method chaining
     */
    fun disableClick(disableClick: Boolean = true): GUI<T> {
        this.disableClick = disableClick
        return this
    }

    /**
     * Registers the necessary event listeners for the GUI.
     */
    private fun registerListener() {
        val listener = object : Listener {
            @EventHandler
            private fun onInventoryClick(event: InventoryClickEvent) {
                if (event.inventory != getInventory()) return
                if (event.slot < 0) return

                var onClick = false
                items.getOrNull(event.slot)?.let { inventoryItem ->
                    onClickConsumer?.accept(event.whoClicked as? Player, inventoryItem)
                    onClick = onClick(event.whoClicked as Player, inventoryItem, event.click)
                }

                event.isCancelled = disableClick && onClick
            }

            @EventHandler
            private fun onInventoryOpen(event: InventoryOpenEvent) {
                if (event.inventory != getInventory()) return
                onOpenConsumer?.accept(event.player as? Player, event.inventory)
                onOpen(event.player as Player, event.inventory)
            }

            @EventHandler
            private fun onInventoryClose(event: InventoryCloseEvent) {
                if (event.inventory != getInventory()) return
                onCloseConsumer?.accept(event.player as? Player, event.inventory)
                onClose(event.player as Player, event.inventory)
            }
        }
        Origin.registerListener(listener)
    }

    override fun getInventory(): Inventory {
        return this.inventory
    }
}
