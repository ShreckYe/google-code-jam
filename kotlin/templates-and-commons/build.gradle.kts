import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("conventions")
}

dependencies {
    implementation(kotlin("stdlib", "1.4.31"))
    implementation(kotlin("script-runtime"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}
