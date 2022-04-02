plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.6.10" // TODO
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
}
