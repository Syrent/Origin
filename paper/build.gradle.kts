import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import java.net.URL
import java.util.concurrent.Executors

plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "ir.syrent"
version = rootProject.version
description = "An experimental API for my minecraft stuff"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = rootProject.name

            artifact(rootProject.tasks.shadowJar.get().archiveFile)
        }
    }

    publishing {
        repositories {
            maven {
                name = "syrent"
                url = uri("https://jitpack.io")
            }
        }
    }

    tasks.withType<PublishToMavenLocal> {
        dependsOn(":paper:shadowJar")
    }
}

tasks.register("prepareKotlinBuildScriptModel") {}

val extraDependencies = emptyMap<String, String>()

tasks {
    runServer {
        minecraftVersion("1.20.1")
    }

    assemble {
        dependsOn(reobfJar)
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
        dependsOn(publishToMavenLocal)
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
