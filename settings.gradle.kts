pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.openjfx.javafxplugin") version "19"
    }

//    javafx {
//        version = "19"
//        modules("javafx.media", "javafx.swing")
//    }
}

rootProject.name = "TheChase"

