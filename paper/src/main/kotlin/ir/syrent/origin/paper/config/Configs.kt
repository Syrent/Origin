package ir.syrent.origin.paper.config

import com.google.gson.Gson
import ir.syrent.origin.paper.config.interfaces.Config
import ir.syrent.origin.paper.config.types.ConfigJson
import ir.syrent.origin.paper.config.types.ConfigYaml
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStream

object Configs {
    private val gson = Gson()

    fun fromBukkit(config: YamlConfiguration): ConfigYaml {
        val file = File(config.currentPath)
        return ConfigYaml.fromBukkit(config)
    }

    fun fromStream(inputStream: InputStream, format: ConfigType): Config {
        val file = createTempFile(format)
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return if (format == ConfigType.JSON) {
            ConfigJson(file)
        } else {
            ConfigYaml(file)
        }
    }

    fun fromFile(file: File): Config {
        return if (file.extension.equals("json", true)) {
            ConfigJson(file)
        } else {
            ConfigYaml(file)
        }
    }

    fun fromMap(map: Map<String, Any>, format: ConfigType): Config {
        val file = createTempFile(format)
        val json = gson.toJson(map)
        file.writeText(json)
        return if (format == ConfigType.JSON) {
            ConfigJson(file)
        } else {
            ConfigYaml(file)
        }
    }

    fun empty(): Config {
        val file = createTempFile(ConfigType.JSON)
        return ConfigJson(file)
    }

    fun empty(format: ConfigType): Config {
        val file = createTempFile(format)
        return if (format == ConfigType.JSON) {
            ConfigJson(file)
        } else {
            ConfigYaml(file)
        }
    }

    fun fromString(json: String, format: ConfigType): Config {
        val file = createTempFile(format)
        file.writeText(json)
        return if (format == ConfigType.JSON) {
            ConfigJson(file)
        } else {
            ConfigYaml(file)
        }
    }

    private fun createTempFile(format: ConfigType): File {
        return File.createTempFile("config", "." + format.extension)
    }
}