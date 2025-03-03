plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.android.navigation.safeargs)
}

android {
    namespace = "com.developer.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}