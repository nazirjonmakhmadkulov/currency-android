import java.io.FileInputStream
import java.util.Properties
import kotlin.apply

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = 35
    defaultConfig {
        applicationId = "com.developer.valyutaapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 22
        versionName = "2.6.1"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "local.properties")))
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
            isMinifyEnabled = true
            isShrinkResources = true
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

        // Types:
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
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.annotation:annotation:1.9.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5")

    // Logging:
    api("com.jakewharton.timber:timber:5.0.1")

    // Jetpack WorkManager
    implementation("androidx.work:work-runtime-ktx:2.10.0")
    implementation("io.insert-koin:koin-androidx-workmanager:4.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.retrofit2:converter-simplexml:2.11.0")

    // Koin main features for Android
    implementation("io.insert-koin:koin-android:4.0.0")

    // Okhttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")

    // Widget
    implementation("io.github.pilgr:paperdb:2.7.2")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("com.yandex.android:mobileads:7.8.0")

    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx:19.3.0")
    implementation("com.google.firebase:firebase-analytics-ktx:22.1.2")
}