package ir.syrent.origin.paper.item

import org.bukkit.inventory.ItemStack

/**
 * Represents a simple custom item in the Origin plugin.
 *
 * @param itemStack the item stack representing the item
 */
class SimpleItem(
    itemStack: ItemStack,
) : Item<ItemStack>(itemStack, null)