plugins {
    kotlin("jvm") version "1.8.21"
    id("maven-publish")
    application
}

group = "ir.syrent"
version = "1.0.1"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("Origin") {
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

application {
    mainClass.set("MainKt")
}