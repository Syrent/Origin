# DISCONTINUED IN FAVOR OF [STICKYNOTE](https://github.com/Sayan-Development/StickyNote)

# Origin - Minecraft API/Framework

Origin is a comprehensive Minecraft API/Framework that provides a wide range of features and utilities to streamline your Minecraft plugin development process. It is designed to work with the latest version of Minecraft using the Paper API.

## Features
- **OriginGUI:** The OriginGUI class represents a graphical user interface for inventory-based interactions in the Origin plugin. It provides methods for managing inventory items, handling events, and customizing GUI behavior.
- **OriginItem:** The OriginItem class represents a custom item in the Origin plugin. It allows you to define behavior for when the item is used by a player. The class provides flexibility by allowing you to associate additional data with the item, which can be accessed during item use events.

## Installation
You can easily integrate Origin into your project by following the instructions below.

### Gradle
[![](https://jitpack.io/v/Syrent/Origin.svg)](https://jitpack.io/#Syrent/Origin)

Add the Jitpack repository to your `build.gradle` file:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
Add the Origin dependency:
```gradle
dependencies {
    implementation 'com.github.Syrent:Origin:Tag'
}
```
* Replace Tag with the desired version of Origin.

### Maven
Add the Jitpack repository to your `pom.xml` file:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the Origin dependency:
```xml
<dependency>
    <groupId>com.github.Syrent</groupId>
    <artifactId>Origin</artifactId>
    <version>Tag</version>
</dependency>
```
* Replace Tag with the desired version of Origin.

## Documentation
All the documentation for Origin is available on the [wiki page](https://github.com/Syrent/Origin/wiki). The wiki contains detailed explanations, tutorials, and examples to help you make the most out of Origin in your Minecraft plugin development.
