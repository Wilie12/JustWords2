plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
}