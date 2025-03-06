package com.developer.currency

/**
 * This is shared between :app module to provide configurations type safety.
 */
enum class CurrencyBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}