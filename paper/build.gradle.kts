import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import java.net.URL
import java.util.concurrent.Executors

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "ir.syrent.paper"
version = "1.0.0"
description = "An experimental API for my minecraft stuff"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


val extraDependencies = emptyMap<String, String>()

tasks {
    runServer {
        minecraftVersion("1.20.1")
    }

    assemble {
        dependsOn(reobfJar)
    }

    kotlin {
        jvmToolchain(17)
    }

    jar {
        enabled = false
    }

    shadowJar {
        archiveClassifier.set("")
        exclude("META-INF/**")
        minimize()
        destinationDirectory.set(file("run/plugins"))
//        archiveFileName.set("OriginPaper-${this.archiveVersion}.jar")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand("version" to version)
            expand("description" to description)
        }
    }

    val extraDeps = register("downloadExtraDependencies") {
        val libsDir = File("libs")
        libsDir.mkdirs()
        val ex = Executors.newCachedThreadPool()
        for (entry in extraDependencies) {
            val file = File(libsDir, entry.key)
            if (file.exists())
                continue
            ex.submit {
                println("Downloading ${entry.key} from ${entry.value}")
                URL(entry.value).openStream().use { s -> file.outputStream().use { it.write(s.readBytes()) } }
                println("Successfully downloaded ${entry.key} to ${file.path}")
            }
        }
        ex.shutdown()
        ex.awaitTermination(10, TimeUnit.SECONDS)
    }

    build {
        dependsOn(extraDeps)
        dependsOn(shadowJar)
    }
}

paper {
    name = "Origin-Paper"
    main = "ir.syrent.origin.paper.Origin"
    hasOpenClassloader = false
    generateLibrariesJson = true
    foliaSupported = true
    apiVersion = "1.20"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("Syrent")
    prefix = "Origin-Paper"
    defaultPermission = BukkitPluginDescription.Permission.Default.OP
}
