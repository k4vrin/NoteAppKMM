pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://maven.google.com")
        mavenCentral()
        maven("https://repo1.maven.org/maven2")
        maven("https://jitpack.io")
        maven(url="https://dl.bintray.com/kotlin/dokka")
        jcenter()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://maven.google.com")
        google()
        mavenCentral()
        maven("https://repo1.maven.org/maven2")
        maven("https://jitpack.io")
        maven(url="https://dl.bintray.com/kotlin/dokka")
        jcenter()
    }
}

rootProject.name = "NoteAppKMM"
include(":androidApp")
include(":shared")