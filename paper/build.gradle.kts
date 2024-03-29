import java.net.URL
import java.util.concurrent.Executors

plugins {
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "ir.syrent"
version = rootProject.version
description = "An experimental API for my minecraft stuff"

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    implementation("org.incendo:cloud-core:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-paper:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-extensions:2.0.0-SNAPSHOT")

//    implementation("net.kyori:adventure-text-minimessage:4.16.0")
//    implementation("net.kyori:adventure-api:4.16.0")
//    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
}

tasks.register("prepareKotlinBuildScriptModel") {}

val extraDependencies = emptyMap<String, String>()

val relocatePrefix = "ir.syrent.origin.dependencies"

tasks {
    shadowJar {
        archiveClassifier.set("")
        exclude("META-INF/**")
//        relocate("cloud.commandframework", "$relocatePrefix.cloud.commandframework")
//        relocate("net.kyori", "$relocatePrefix.net.keyori")
    }

    runServer {
        minecraftVersion("1.20.4")
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
        dependsOn(publishToMavenLocal)
    }
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile)
        }
    }

    /*tasks.withType<PublishToMavenLocal> {
        dependsOn(":paper:shadowJar")
    }*/
}