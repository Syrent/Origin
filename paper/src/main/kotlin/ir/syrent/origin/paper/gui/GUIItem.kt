package ir.syrent.origin.paper.gui

import ir.syrent.origin.paper.Origin
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Consumer

/**
 * Represents an item in an inventory for a GUI.
 *
 * @param T the type of data associated with the item
 * @param slot the slot index of the item in the inventory
 * @param itemStack the item stack representing the item
 * @param data additional data associated with the item (optional)
 */
data class GUIItem<T>(
    val slot: Int,
    val itemStack: ItemStack?,
    val data: T? = null
) {

    var onClickConsumer: Consumer<GUIItem<T>>? = null

    fun onClick(consumer: Consumer<GUIItem<T>>): GUIItem<T> {
        onClickConsumer = consumer
        return this
    }


}
