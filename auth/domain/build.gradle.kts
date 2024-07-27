plugins {
    alias(libs.plugins.justwords2.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}