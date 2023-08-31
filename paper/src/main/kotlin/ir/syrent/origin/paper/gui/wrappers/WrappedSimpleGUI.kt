package ir.syrent.origin.paper.gui.wrappers

import ir.syrent.origin.paper.gui.GUIItem
import ir.syrent.origin.paper.gui.SimpleGUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

/**
 * Represents a wrapped simple GUI (graphical user interface) in a Paper plugin.
 * This class extends the GenericGUI class and provides a basic implementation of a GUI.
 *
 * @param title the title of the GUI, displayed to the player
 * @param type the type of inventory for the GUI (default: CHEST)
 * @param size the size of the inventory (default: 54 slots)
 * @param owner the inventory holder for the GUI (optional)
 */
open class WrappedSimpleGUI(
    title: String,
    type: InventoryType = InventoryType.CHEST,
    size: Int = 54,
    owner: InventoryHolder? = null
) : SimpleGUI(title, type, size, owner) {
    override fun onOpen(player: Player, inventory: Inventory) {}

    override fun onClick(player: Player, item: GUIItem<ItemStack>, clickType: ClickType): Boolean {
        return false
    }

    override fun onClose(player: Player, inventory: Inventory) {}

}