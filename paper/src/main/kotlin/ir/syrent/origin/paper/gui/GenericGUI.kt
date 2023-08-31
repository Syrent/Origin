package ir.syrent.origin.paper.gui

import ir.syrent.origin.paper.Origin
import ir.syrent.origin.paper.gui.interfaces.ExtendableGUI
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
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
abstract class GenericGUI<T>(
    val title: Component,
    val type: InventoryType = InventoryType.CHEST,
    val size: Int = 54,
    val owner: InventoryHolder? = null
) : ExtendableGUI<T> {

    // The underlying inventory object
    private val inventory = Bukkit.createInventory(owner, size, title)

    // A list of inventory items associated with the GUI
    private val items = mutableListOf<GUIItem<T>>()

    // Flag to determine whether clicking on GUI items is disabled
    protected var disableClick = false

    /**
     * Initializes the GUI by registering the necessary event listeners.
     */
    init {
        registerListener()
    }

    override fun update(): GenericGUI<T> {
        items.forEach { item ->
            inventory.setItem(item.slot, item.itemStack)
        }
        return this
    }

    override fun setItem(item: GUIItem<T>): GenericGUI<T> {
        inventory.setItem(item.slot, item.itemStack)
        items.add(item)
        return this
    }

    override fun removeItem(slot: Int): GenericGUI<T> {
        inventory.setItem(slot, null)
        items.removeIf { it.slot == slot }
        return this
    }

    override fun clear(): GenericGUI<T> {
        items.clear()
        inventory.clear()
        return this
    }

    override fun fill(item: ItemStack?, range: IntRange): GenericGUI<T> {
        range.forEach {
            setItem(GUIItem(it, item))
        }
        return this
    }

    // Event consumers for GUI events
    private var onOpenConsumer: BiConsumer<Player?, Inventory>? = null
    private var onClickConsumer: BiConsumer<Player?, GUIItem<T>>? = null
    private var onCloseConsumer: BiConsumer<Player?, Inventory>? = null

    override fun onOpen(consumer: BiConsumer<Player?, Inventory>): GenericGUI<T> {
        onOpenConsumer = consumer
        return this
    }

    override fun onClick(consumer: BiConsumer<Player?, GUIItem<T>>): GenericGUI<T> {
        onClickConsumer = consumer
        return this
    }

    override fun onClose(consumer: BiConsumer<Player?, Inventory>): GenericGUI<T> {
        onCloseConsumer = consumer
        return this
    }

    override fun disableClick(disableClick: Boolean): GenericGUI<T> {
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
                if (event.inventory != inventory) return
                if (event.slot < 0) return


                var onClick = false
                items.find { it.slot == event.slot }?.let { inventoryItem ->
                    onClickConsumer?.accept(event.whoClicked as? Player, inventoryItem)
                    onClick = onClick(event.whoClicked as Player, inventoryItem, event.click)
                }

                event.isCancelled = disableClick || onClick
            }

            @EventHandler
            private fun onInventoryItemClick(event: InventoryClickEvent) {
                if (event.inventory != inventory) return
                if (event.slot < 0) return


                items.find { it.slot == event.slot }?.let { inventoryItem ->
                    inventoryItem.onClickConsumer?.accept(inventoryItem)
                }

                event.isCancelled = disableClick
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
        return inventory
    }
}
