package ir.syrent.origin.paper.gui

import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

/**
 * Represents a simple GUI (graphical user interface) in a Paper plugin.
 * This class extends the GenericGUI class and provides a basic implementation of a GUI.
 *
 * @param title the title of the GUI, displayed to the player
 * @param type the type of inventory for the GUI (default: CHEST)
 * @param size the size of the inventory (default: 54 slots)
 * @param owner the inventory holder for the GUI (optional)
 */
abstract class SimpleGUI(
    title: Component,
    type: InventoryType = InventoryType.CHEST,
    size: Int = 54,
    owner: InventoryHolder? = null
) : GenericGUI<Void>(title, type, size, owner)