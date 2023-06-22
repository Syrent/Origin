package ir.syrent.origin.paper.config.types

import ir.syrent.origin.paper.config.interfaces.Config
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ConfigYaml(private val file: File) : Config {
    private val configuration = YamlConfiguration.loadConfiguration(file)

    override fun getFile(): File {
        return file
    }

    override fun save(): Config {
        try {
            configuration.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    override fun update(): Config {
        return this
    }

    override fun reset(): Config {
        configuration.getKeys(false).forEach { key ->
            configuration.set(key, null)
        }
        save()
        return this
    }

    override fun delete(): Config {
        file.delete()
        return this
    }

    override fun has(path: String): Boolean {
        return configuration.contains(path)
    }

    override fun getKeys(deep: Boolean): List<String> {
        return configuration.getKeys(deep).toList()
    }

    override fun get(path: String): Any? {
        return configuration.get(path)
    }

    override fun set(path: String, any: Any, replace: Boolean): Config {
        if (get(path) != null && !replace) return this
        configuration.set(path, any)
        return this
    }

    override fun getSubsectionOrNull(path: String): Config? {
        if (!has(path)) {
            return null
        }
        val subsectionConfig = ConfigYaml(kotlin.io.path.createTempFile().toFile())
        val subsection = configuration.getConfigurationSection(path)
        subsection?.getKeys(true)?.forEach { key ->
            subsection.get(key)?.let {
                subsectionConfig.set("$path.$key", it)
            }
        }
        return subsectionConfig
    }

    override fun getStringOrNull(path: String): String? {
        return configuration.getString(path)
    }

    override fun getIntOrNull(path: String): Int {
        return configuration.getInt(path)
    }

    override fun getIntsOrNull(path: String): List<Int> {
        return configuration.getIntegerList(path)
    }

    override fun getDoubleOrNull(path: String): Double {
        return configuration.getDouble(path)
    }

    override fun getDoublesOrNull(path: String): List<Double> {
        return configuration.getDoubleList(path)
    }

    override fun getBooleanOrNull(path: String): Boolean {
        return configuration.getBoolean(path)
    }

    override fun getStringsOrNull(path: String): List<String> {
        return configuration.getStringList(path)
    }

    companion object {
        fun fromBukkit(config: YamlConfiguration): ConfigYaml {
            val file = File(config.currentPath)
            return ConfigYaml(file).apply {
                configuration.loadFromString(config.saveToString())
            }
        }
    }

}