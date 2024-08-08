plugins {
    alias(libs.plugins.justwords2.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.willaapps.word.data"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.datastore.preferences)

    implementation(projects.word.domain)
    implementation(projects.core.database)
}