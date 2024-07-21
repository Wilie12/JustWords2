plugins {
    alias(libs.plugins.justwords2.android.library)
    alias(libs.plugins.justwords2.jvm.ktor)
}

android {
    namespace = "com.willaapps.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
}