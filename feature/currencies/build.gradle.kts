plugins {
    alias(libs.plugins.currency.android.feature)
    alias(libs.plugins.currency.android.library.compose)
}

android {
    namespace = "com.developer.currencies"
    viewBinding.enable = true
}

dependencies {}
