plugins {
    alias(libs.plugins.justwords2.android.feature.ui)
}

android {
    namespace = "com.willaapps.user.presentation"
}

dependencies {
    implementation(projects.user.domain)
}