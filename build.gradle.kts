buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.0"
}

/*base {
    archivesName.set(project.name)
}*/

dependencies {
    implementation(project(":paper"))
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        // Cloud SNAPSHOT (Dev repository)
        maven("https://repo.masmc05.dev/repository/maven-snapshots/")

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://jitpack.io")
        maven("https://repo.maven.apache.org/maven2/")

        // Crunch
        maven("https://redempt.dev")
    }

    dependencies {
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Crunch
        implementation("com.github.Redempt:Crunch:1.1.3")

        // Maven
        implementation("org.apache.maven:maven-artifact:3.8.5")
    }

    configurations.all {
        exclude(group = "com.mojang", module = "brigadier")
    }

    tasks {
        shadowJar {
            exclude("META-INF/**")
            minimize()
        }

        java {
            withSourcesJar()
            withJavadocJar()
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }

        compileKotlin {
            kotlinOptions {
                jvmTarget = "17"
            }
        }

        compileJava {
            options.isDeprecation = true
            options.encoding = "UTF-8"

            dependsOn(clean)
        }

        processResources {
            filesMatching(listOf("**plugin.yml")) {
                expand(
                    "version" to project.version,
                    "pluginName" to rootProject.name,
                    "description" to description
                )
            }
        }

        build {
            dependsOn(shadowJar)
        }
    }
}

group = "ir.syrent"
version = findProperty("version")!!