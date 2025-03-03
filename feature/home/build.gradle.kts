plugins {
    alias(libs.plugins.currency.android.feature)
    alias(libs.plugins.currency.android.library.compose)
}

android {
    namespace = "com.developer.home"
    viewBinding.enable = true
}

dependencies {}