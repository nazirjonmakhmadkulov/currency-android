plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.developer.valyutaapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 7
        versionName = "2.4.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = true
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.20")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.annotation:annotation:1.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.8")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Logging:
    api("com.jakewharton.timber:timber:5.0.1")

    //Fragment
    implementation("androidx.fragment:fragment-ktx:1.5.6")

    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("ru.gildor.coroutines:kotlin-coroutines-retrofit:1.1.0")

    // Room
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("androidx.room:room-runtime:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0")

    // Koin main features for Android
    implementation("io.insert-koin:koin-android:3.3.3")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:3.3.3")

    //Okhttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.9")

    //Widget
    implementation("io.github.pilgr:paperdb:2.7.2")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("com.yandex.android:mobileads:5.7.0")
    implementation("com.yandex.ads.mediation:mobileads-admob:21.3.0.0")
    implementation("com.google.android.gms:play-services-ads:21.3.0")
}