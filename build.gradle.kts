plugins {
    kotlin("jvm") version "1.8.21"
    id("maven-publish")
    application
}

group = "ir.sayandevelopment"
version = "1.0.0"

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