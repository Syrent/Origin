package ir.syrent.origin.paper.config.types

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.syrent.origin.paper.config.interfaces.Config
import java.io.File

class ConfigJson(private val file: File) : Config {
    private val gson = Gson().newBuilder().setPrettyPrinting().create()
    private var configData: MutableMap<String, Any> = mutableMapOf()

    init {
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableMap<String, Any>>() {}.type
            configData = gson.fromJson(json, type)
        }
    }

    override fun getFile(): File {
        return file
    }

    override fun save(): Config {
        val json = gson.toJson(configData)
        file.writeText(json)
        return this
    }

    override fun update(): Config {
        return this
    }

    override fun reset(): Config {
        configData.clear()
        save()
        return this
    }

    override fun delete(): Config {
        file.delete()
        return this
    }

    override fun has(path: String): Boolean {
        return configData.containsKey(path)
    }

    override fun getKeys(deep: Boolean): List<String> {
        return configData.keys.toList()
    }

    override fun get(path: String): Any? {
        return configData[path]
    }

    override fun set(path: String, any: Any, replace: Boolean): Config {
        if (get(path) != null && !replace) return this
        configData[path] = any
        return this
    }

    override fun getSubsectionOrNull(path: String): Config? {
        if (!has(path)) {
            return null
        }
        val subsectionConfig = ConfigJson(kotlin.io.path.createTempFile().toFile())
        val subsection = gson.fromJson(gson.toJson(configData[path]), MutableMap::class.java)
        subsection?.forEach { (key, value) ->
            value?.let {
                subsectionConfig.set("$path.$key", value)
            }
        }
        return subsectionConfig
    }

    override fun getStringOrNull(path: String): String? {
        return configData[path] as? String
    }

    override fun getIntOrNull(path: String): Int? {
        return configData[path]?.toString()?.toIntOrNull()
    }

    override fun getIntsOrNull(path: String): List<Int>? {
        val json = gson.toJson(configData[path])
        return gson.fromJson(json, object : TypeToken<List<Int>?>() {}.type)
    }

    override fun getDoubleOrNull(path: String): Double? {
        return configData[path]?.toString()?.toDoubleOrNull()
    }

    override fun getDoublesOrNull(path: String): List<Double>? {
        val json = gson.toJson(configData[path])
        return gson.fromJson(json, object : TypeToken<List<Double>?>() {}.type)
    }

    override fun getBooleanOrNull(path: String): Boolean? {
        return configData[path] as? Boolean
    }

    override fun getStringsOrNull(path: String): List<String>? {
        val json = gson.toJson(configData[path])
        return gson.fromJson(json, object : TypeToken<List<String>?>() {}.type)
    }
}