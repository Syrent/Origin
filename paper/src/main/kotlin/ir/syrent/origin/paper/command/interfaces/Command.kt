package ir.syrent.origin.paper.command.interfaces

import ir.syrent.origin.paper.Origin
import net.kyori.adventure.text.Component

/**
 * Interface for defining commands.
 */
interface CommandExtension {

    /**
     * Retrieves the permission associated with the specified command literal.
     *
     * @param literal The command literal.
     * @return The permission string.
     */
    fun constructBasePermission(literal: String): String {
        return "${Origin.getPlugin().name}.commands.$literal"
    }

    fun errorPrefix(): Component

    /**
     * Sets the prefix for error messages related to this command.
     *
     * @param prefix The error prefix to set.
     */
    fun errorPrefix(prefix: Component)

}
