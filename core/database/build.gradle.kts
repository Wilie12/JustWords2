plugins {
    alias(libs.plugins.justwords2.android.library)
}

android {
    namespace = "com.willaapps.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
}