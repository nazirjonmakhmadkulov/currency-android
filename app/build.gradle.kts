import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.currency.android.application)
    alias(libs.plugins.currency.android.application.compose)
//    alias(libs.plugins.currency.android.application.flavors)
    alias(libs.plugins.currency.android.application.firebase)
    alias(libs.plugins.android.navigation.safeargs)
    alias(libs.plugins.currency.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
}

android {
    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "local.properties")))
    }
    val unitId1 = prop["unitId1"] as? String ?: ""
    val unitId2 = prop["unitId2"] as? String ?: ""

    compileSdk = 36
    defaultConfig {
        applicationId = "com.developer.valyutaapp"
        minSdk = 23
        targetSdk = 36
        versionCode = 28
        versionName = "2.6.7"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
        buildConfigField("String", "UNIT_ID1", unitId1)
        buildConfigField("String", "UNIT_ID2", unitId2)
    }

    signingConfigs.create("release") {
        storeFile = file("store_key.jks")
        storePassword = prop.getProperty("KEYSTORE_PASSWORD")
        keyAlias = prop.getProperty("KEY_ALIAS")
        keyPassword = prop.getProperty("KEY_PASSWORD")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    val googlePlay = "https://play.google.com/store/apps/dev?id=5402022606902660683"
    val appGallery =
        "https://appgallery.huawei.ru/#/tab/appdetailCommon%7CC109579467%7Cautomore%7Cdoublecolumncardwithstar%7C903547"
    val ruStore = "https://www.rustore.ru/catalog/developer/ktgto9"

    sourceSets {
        named("main") {
            java.srcDir("src/main/java")
            java.srcDir("src/huawei/java")
            java.srcDir("src/rustore/java")
        }
        create("google").manifest.srcFile("src/google/AndroidManifest.xml")
        create("huawei").manifest.srcFile("src/huawei/AndroidManifest.xml")
        create("rustore").manifest.srcFile("src/rustore/AndroidManifest.xml")
    }

    flavorDimensions += listOf("bundle", "type", "store")

    productFlavors {
        // Bundles:
        create("currency") {
            dimension = "bundle"
            applicationId = "com.developer.valyutaapp"
        }

//         Types:
        create("prod") {
            dimension = "type"
        }

        create("google") {
            dimension = "store"
            buildConfigField("String", "MARKET_URL", "\"$googlePlay\"")
        }
        create("huawei") {
            versionNameSuffix = ".hms"
            dimension = "store"
            buildConfigField("String", "MARKET_URL", "\"$appGallery\"")
        }
        create("rustore") {
            versionNameSuffix = ".ru"
            dimension = "store"
            buildConfigField("String", "MARKET_URL", "\"$ruStore\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlin.jvmToolchain(19)
    kotlinOptions.jvmTarget = "19"

    buildFeatures.viewBinding = true
    buildFeatures.buildConfig = true
    namespace = "com.developer.currency"
}
dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.favorite)
    implementation(projects.feature.converter)
    implementation(projects.feature.currencies)
    implementation(projects.feature.setting)
    implementation(projects.feature.chart)

    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.preference.ktx)

    implementation(libs.viewbinding.noreflection)

    // Fragment
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.work.ktx)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.hilt.ext.work)

    // Logging:
    api(libs.timber)

    implementation(libs.kotlinx.coroutines.android)

    // Widget
    implementation("io.github.pilgr:paperdb:2.7.2")

    implementation(libs.mobileads)

    // Shimmer
    implementation(libs.shimmer)
}