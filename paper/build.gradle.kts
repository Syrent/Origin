import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
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
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")


    implementation("cloud.commandframework:cloud-paper:1.9.0-SNAPSHOT")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.9.0-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = rootProject.name

            artifact(tasks.shadowJar.get().archiveFile)
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
    val relocate = task<ConfigureShadowRelocation>("relocateShadowJar") {
        target = shadowJar.get()
        prefix = rootProject.group.toString()
        this.target.apply {
            relocate("net.kyori.", "net.kyori.")
        }
    }

    shadowJar {
        dependsOn(relocate)
        archiveClassifier.set("")
        exclude("META-INF/**")
        minimize()
    }

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
    }
}

paper {
    name = "Origin-Paper"
    main = "ir.syrent.origin.paper.Origin"
    hasOpenClassloader = false
    generateLibrariesJson = true
    foliaSupported = true
    apiVersion = "1.19"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("Syrent")
    prefix = "Origin-Paper"
    defaultPermission = BukkitPluginDescription.Permission.Default.OP
}
