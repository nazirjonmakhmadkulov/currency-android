package com.developer.currency

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class CurrencyFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    google(FlavorDimension.contentType),
    huawei(FlavorDimension.contentType, applicationIdSuffix = ".hms"),
    rustore(FlavorDimension.contentType, applicationIdSuffix = ".ru"),
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: CurrencyFlavor) -> Unit = {},
) {
    commonExtension.apply {
        FlavorDimension.values().forEach { flavorDimension ->
            flavorDimensions += flavorDimension.name
        }

        productFlavors {
            CurrencyFlavor.values().forEach { currencyFlavor ->
                register(currencyFlavor.name) {
                    dimension = currencyFlavor.dimension.name
                    flavorConfigurationBlock(this, currencyFlavor)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (currencyFlavor.applicationIdSuffix != null) {
                            applicationIdSuffix = currencyFlavor.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
