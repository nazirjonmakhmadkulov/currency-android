// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.8.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    id("androidx.navigation.safeargs") version "2.8.7" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}