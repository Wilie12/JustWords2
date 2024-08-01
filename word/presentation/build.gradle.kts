plugins {
    alias(libs.plugins.justwords2.android.feature.ui)
}

android {
    namespace = "com.willaapps.word.presentation"
}

dependencies {
    implementation(projects.word.domain)
    implementation(projects.core.domain)
}