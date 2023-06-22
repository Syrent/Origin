package ir.syrent.origin.paper.config.interfaces

import ir.syrent.origin.paper.config.Configs
import ir.syrent.origin.paper.placeholder.Placeholder
import ir.syrent.origin.paper.placeholder.Placeholder.Companion.compile
import redempt.crunch.Crunch
import java.io.File

/**
 * Represents a configuration file.
 */
interface Config {

    /**
     * Returns the file associated with this configuration.
     */
    fun getFile(): File

    /**
     * Saves the configuration to the file.
     *
     * @return The updated Config instance.
     */
    fun save(): Config

    /**
     * Updates the configuration.
     *
     * @return The updated Config instance.
     */
    fun update(): Config

    /**
     * Resets the configuration, clearing all data.
     *
     * @return The updated Config instance.
     */
    fun reset(): Config

    /**
     * Deletes the configuration file.
     *
     * @return The updated Config instance.
     */
    fun delete(): Config

    /**
     * Checks if the configuration contains a value at the specified path.
     *
     * @param path The path to check.
     * @return `true` if the configuration contains a value at the specified path, `false` otherwise.
     */
    fun has(path: String): Boolean

    /**
     * Returns a list of keys in the configuration.
     *
     * @param deep Determines whether to include keys from nested subsections.
     * @return A list of keys in the configuration.
     */
    fun getKeys(deep: Boolean): List<String>

    /**
     * Recursively retrieves keys in the configuration.
     *
     * @param found The set of keys found so far.
     * @param root The root path for the recursion.
     * @return A list of recursively found keys in the configuration.
     */
    fun recurseKeys(found: Set<String>, root: String): List<String> {
        return emptyList()
    }

    /**
     * Retrieves the value at the specified path in the configuration.
     *
     * @param path The path of the value to retrieve.
     * @return The value at the specified path, or `null` if not found.
     */
    fun get(path: String): Any?

    /**
     * Sets the value at the specified path in the configuration.
     *
     * @param path The path where the value should be set.
     * @param any The value to set.
     * @param replace Determines whether to replace the existing value at the path if present.
     * @return The updated Config instance.
     */
    fun set(path: String, any: Any, replace: Boolean = false): Config

    /**
     * Retrieves a subsection of the configuration at the specified path.
     *
     * @param path The path of the subsection.
     * @return The subsection as a Config instance, or an empty Config if not found.
     */
    fun getSubsection(path: String): Config {
        return getSubsectionOrNull(path) ?: Configs.empty()
    }

    /**
     * Retrieves a subsection of the configuration at the specified path, or null if not found.
     *
     * @param path The path of the subsection.
     * @return The subsection as a Config instance, or null if not found.
     */
    fun getSubsectionOrNull(path: String): Config?

    /**
     * Retrieves a string value at the specified path in the configuration.
     *
     * @param path The path of the string value.
     * @return The string value at the specified path, or null if not found.
     */
    fun getStringOrNull(path: String): String?

    /**
     * Retrieves a string value at the specified path in the configuration.
     * If the value is null, an empty string is returned.
     *
     * @param path The path of the string value.
     * @return The string value at the specified path, or an empty string if not found.
     */
    fun getString(path: String): String {
        return getStringOrNull(path) ?: ""
    }

    /**
     * Retrieves an integer value at the specified path in the configuration.
     *
     * @param path The path of the integer value.
     * @return The integer value at the specified path, or 0 if not found or not an integer.
     */
    fun getIntOrNull(path: String): Int?

    /**
     * Retrieves an integer value at the specified path in the configuration.
     * If the value is null or not an integer, 0 is returned.
     *
     * @param path The path of the integer value.
     * @return The integer value at the specified path, or 0 if not found or not an integer.
     */
    fun getInt(path: String): Int {
        return getIntOrNull(path) ?: 0
    }

    /**
     * Retrieves a list of integer values at the specified path in the configuration.
     *
     * @param path The path of the list of integer values.
     * @return The list of integer values at the specified path, or an empty list if not found or not a list of integers.
     */
    fun getInts(path: String): List<Int> {
        return getIntsOrNull(path) ?: emptyList()
    }

    /**
     * Retrieves a double value at the specified path in the configuration.
     *
     * @param path The path of the double value.
     * @return The double value at the specified path, or 0.0 if not found or not a double.
     */
    fun getDouble(path: String): Double {
        return getDoubleOrNull(path) ?: 0.0
    }

    /**
     * Retrieves a double value at the specified path in the configuration.
     * If the value is null or not a double, 0.0 is returned.
     *
     * @param path The path of the double value.
     * @return The double value at the specified path, or 0.0 if not found or not a double.
     */
    fun getDoubleOrNull(path: String): Double?

    /**
     * Retrieves a list of double values at the specified path in the configuration.
     *
     * @param path The path of the list of double values.
     * @return The list of double values at the specified path, or an empty list if not found or not a list of doubles.
     */
    fun getDoubles(path: String): List<Double> {
        return getDoublesOrNull(path) ?: emptyList()
    }

    /**
     * Retrieves a list of double values at the specified path in the configuration.
     *
     * @param path The path of the list of double values.
     * @return The list of double values at the specified path, or null if not found or not a list of doubles.
     */
    fun getDoublesOrNull(path: String): List<Double>?

    /**
     * Retrieves a list of integer values at the specified path in the configuration.
     *
     * @param path The path of the list of integer values.
     * @return The list of integer values at the specified path, or null if not found or not a list of integers.
     */
    fun getIntsOrNull(path: String): List<Int>?

    /**
     * Retrieves a boolean value at the specified path in the configuration.
     *
     * @param path The path of the boolean value.
     * @return The boolean value at the specified path, or false if not found or not a boolean.
     */
    fun getBoolean(path: String): Boolean {
        return getBooleanOrNull(path) ?: false
    }

    /**
     * Retrieves a boolean value at the specified path in the configuration.
     * If the value is null or not a boolean, false is returned.
     *
     * @param path The path of the boolean value.
     * @return The boolean value at the specified path, or false if not found or not a boolean.
     */
    fun getBooleanOrNull(path: String): Boolean?

    /**
     * Retrieves a list of string values at the specified path in the configuration.
     *
     * @param path The path of the list of string values.
     * @return The list of string values at the specified path, or an empty list if not found or not a list of strings.
     */
    fun getStrings(path: String): List<String> {
        return getStringsOrNull(path) ?: emptyList()
    }

    /**
     * Retrieves a list of string values at the specified path in the configuration.
     *
     * @param path The path of the list of string values.
     * @return The list of string values at the specified path, or null if not found or not a list of strings.
     */
    fun getStringsOrNull(path: String): List<String>?

    /**
     * Retrieves an integer value from an expression at the specified path in the configuration.
     *
     * @param path The path of the expression.
     * @param placeholders The placeholders to be applied to the expression.
     * @return The evaluated integer value of the expression at the specified path.
     */
    fun getIntFromExpression(path: String, vararg placeholders: Placeholder): Int {
        return getDoubleFromExpression(getString(path), *placeholders).toInt()
    }

    /**
     * Retrieves a double value from an expression at the specified path in the configuration.
     *
     * @param path The path of the expression.
     * @param placeholders The placeholders to be applied to the expression.
     * @return The evaluated double value of the expression at the specified path.
     */
    fun getDoubleFromExpression(path: String, vararg placeholders: Placeholder): Double {
        return Crunch.compileExpression(getString(path).compile(*placeholders)).evaluate()
    }
}
