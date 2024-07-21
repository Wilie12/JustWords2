plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.word.data"
}

dependencies {
    implementation(projects.word.domain)
    implementation(projects.core.database)
}