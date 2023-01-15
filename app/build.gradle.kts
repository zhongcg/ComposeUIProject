import com.guru.composecookbook.plugin.configurations.ProjectConfigs
import com.guru.composecookbook.plugin.dependencies.*
import com.guru.composecookbook.plugin.dependencies.addComposeOfficialDependencies
import com.guru.composecookbook.plugin.dependencies.addComposeThirdPartyDependencies
import com.guru.composecookbook.plugin.dependencies.addKotlinDependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {

    compileSdk = com.guru.composecookbook.plugin.configurations.ProjectConfigs.compileSdkVersion

    defaultConfig {
        applicationId = com.guru.composecookbook.plugin.configurations.ProjectConfigs.applicationId
        minSdk = com.guru.composecookbook.plugin.configurations.ProjectConfigs.minSdkVersion
        targetSdk = com.guru.composecookbook.plugin.configurations.ProjectConfigs.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
      //  useIR = true
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"

    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = com.guru.composecookbook.plugin.configurations.ProjectConfigs.kotlinCompilerExtensionVersion
    }
    lint {
        abortOnError = false
    }
    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    implementation(project(":theme"))
    implementation(project(":data"))

    addKotlinDependencies()

//    addDataDependencies()
//
    addComposeOfficialDependencies()
//    addComposeDebugDependencies()
    addComposeThirdPartyDependencies()
//
//    addThirdPartyUiDependencies()
//
//    addCoreAndroidDependencies()
//    addCoreAndroidUiDependencies()
//    addGoogleAndroidDependencies()
//    addNetworkingDependencies()
//
//    addKotlinTestDependencies()
//    addJunit5TestDependencies()
//    addThirdPartyUnitTestsDependencies()
//
//    addAndroidInstrumentationTestsDependencies()
//    addBiometricDependency()
}