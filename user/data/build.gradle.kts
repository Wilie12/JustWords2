plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.user.data"
}

dependencies {
    implementation(projects.user.domain)
    implementation(projects.core.database)
}