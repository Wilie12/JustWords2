plugins {
    alias(libs.plugins.justwords2.android.library)
    alias(libs.plugins.justwords2.android.room)
}

android {
    namespace = "com.willaapps.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
}