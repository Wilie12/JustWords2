plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
}