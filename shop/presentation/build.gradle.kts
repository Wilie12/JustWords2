plugins {
    alias(libs.plugins.justwords2.android.feature.ui)
}

android {
    namespace = "com.willaapps.shop.presentation"
}

dependencies {
    implementation(projects.shop.domain)
    implementation(projects.core.domain)
}