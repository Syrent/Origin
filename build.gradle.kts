plugins {
    java
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ir.syrent"
version = findProperty("version")!!

base {
    archivesName.set(project.name)
}

dependencies {
    project(":paper").dependencyProject.subprojects {
        implementation(this)
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://jitpack.io")
    }

    dependencies {
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    java {
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    tasks {
        shadowJar {
            archiveClassifier.set("")
            exclude("META-INF/**")
            minimize()
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

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("Origin") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = rootProject.name

            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}


kotlin {
    jvmToolchain(17)
}