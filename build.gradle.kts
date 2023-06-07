import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.lamti"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation ("androidx.compose.ui:ui:1.0.0")

                val lifecycleVersion = "2.6.1"
                implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
                implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
                implementation ("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

                implementation("uk.co.caprica:vlcj:4.8.2")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "TheChase"
            packageVersion = "1.0.0"
        }
    }
}
