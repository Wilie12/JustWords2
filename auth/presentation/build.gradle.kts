plugins {
    alias(libs.plugins.justwords2.android.feature.ui)
}

android {
    namespace = "com.willaapps.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
}