plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.sync"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.notification)

    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    ksp(libs.hilt.ext.compiler)
}