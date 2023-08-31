package ir.syrent.origin.paper.gui.wrappers

import ir.syrent.origin.paper.gui.GUIItem
import ir.syrent.origin.paper.gui.GenericGUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * Represents a wrapped graphical user interface (GUI) in a Paper plugin.
 * This class provides utility methods for creating and managing GUIs.
 *
 * @param T the type of data associated with the GUI
 * @param title the title of the GUI, displayed to the player
 * @param type the type of inventory for the GUI (default: CHEST)
 * @param size the size of the inventory (default: 54 slots)
 * @param owner the inventory holder for the GUI (optional)
 */
open class WrappedGenericGUI<T>(
    title: String,
    type: InventoryType = InventoryType.CHEST,
    size: Int = 54,
    owner: InventoryHolder? = null
) : GenericGUI<T>(title, type, size, owner) {
    override fun onOpen(player: Player, inventory: Inventory) {}

    override fun onClick(player: Player, item: GUIItem<T>, clickType: ClickType): Boolean {
        return false
    }

    override fun onClose(player: Player, inventory: Inventory) {}
}