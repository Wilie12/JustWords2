plugins {
    `kotlin-dsl`
}

group = "com.willaapps.justwords2.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "justwords2.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}