package ir.syrent.origin.paper.gui.interfaces

import ir.syrent.origin.paper.gui.GUIItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory

/**
 * Represents an extendable graphical user interface (GUI) in a Paper plugin.
 * This interface extends the base GUI interface and allows for additional customization.
 * Implementing classes should provide the necessary functionality to handle GUI events.
 *
 * @param T the type of data associated with the GUI
 */
interface ExtendableGUI<T> : GUI<T> {

    /**
     * Called when the GUI is opened by a player.
     * Override this method to add custom behavior.
     *
     * @param player the player who opened the GUI
     * @param inventory the inventory associated with the GUI
     */
    fun onOpen(player: Player, inventory: Inventory)

    /**
     * Called when a player clicks on an item in the GUI.
     * Override this method to add custom behavior.
     *
     * @param player the player who clicked on the item
     * @param item the inventory item that was clicked
     * @param clickType the type of click event
     * @return true if the event should be canceled, false otherwise
     */
    fun onClick(player: Player, item: GUIItem<T>, clickType: ClickType): Boolean

    /**
     * Called when the GUI is closed by a player.
     * Override this method to add custom behavior.
     *
     * @param player the player who closed the GUI
     * @param inventory the inventory associated with the GUI
     */
    fun onClose(player: Player, inventory: Inventory)

}