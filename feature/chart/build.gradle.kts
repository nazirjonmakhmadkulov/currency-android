plugins {
    alias(libs.plugins.currency.android.feature)
    alias(libs.plugins.currency.android.library.compose)
}

android {
    namespace = "com.developer.chart"
    viewBinding.enable = true
}

dependencies {
    implementation(libs.mpandroidchart)
}