plugins {
    alias(libs.plugins.justwords2.android.library)
    alias(libs.plugins.justwords2.jvm.ktor)
}

android {
    namespace = "com.willaapps.shop.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.shop.domain)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}