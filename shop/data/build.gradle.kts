plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.shop.data"
}

dependencies {
    implementation(projects.shop.domain)
    implementation(projects.core.database)
}