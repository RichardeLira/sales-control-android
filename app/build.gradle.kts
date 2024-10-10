plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.salescontroll"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.salescontroll"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    viewBinding {
        enable = true
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


//  Spinner
    implementation ("com.github.skydoves:powerspinner:1.2.4")
//    // RxJava 3 Core
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
//
    // RxAndroid para RxJava 3
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
//
//    // (Opcional) Retrofit RxJava3 Adapter
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    implementation ("com.cottacush:CurrencyEditText:1.0.0")

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    //     optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")




}


