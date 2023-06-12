import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.5.10"
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

                implementation("io.ktor:ktor-client-core:2.3.1")
                implementation("io.ktor:ktor-client-cio:2.3.1")
                implementation("io.ktor:ktor-client-serialization:2.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
                implementation("io.ktor:ktor-client-auth:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.1")

                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-xml:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-cbor:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-protobuf:2.3.1")
                implementation("io.ktor:ktor-client-logging:2.3.1")
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
