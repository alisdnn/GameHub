@file:Suppress("ClassName")

import org.gradle.api.JavaVersion

/**
 * All the configurations we write in gradle scripts about app's main characteristics.
 */
object AppConfig {
    // app itself
    const val compileSdkVersion = 32
    const val targetSdkVersion = 32
    const val minSdkVersion = 21
    const val applicationId = "ca.on.hojat.gamenews"
    const val versionCode = 1
    const val versionName = "1.0.0"

}

/**
 * All the stuff that I don't have an idea how the fuck they're working; but I'm using them anyway.
 */
object Tooling {
    // language
    const val kotlin = "1.7.0"
    val javaCompatibilityVersion = JavaVersion.VERSION_11
    val kotlinCompatibilityVersion = JavaVersion.VERSION_11

    // build tools
    const val gradlePluginVersion = "7.2.2"
    const val gradleVersionsPlugin = "0.42.0"

    // serializer
    const val protobufPluginVersion = "0.8.19"
    private const val serializationVersion = "1.3.3"
    const val serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"


    const val kspPlugin = "1.7.0-1.0.6"
    const val ktlintPlugin = "10.3.0"
    const val ktlint = "0.45.2"
}

object Versions {
    const val compose = "1.2.0"
    const val daggerHilt = "2.43.2"
    const val detektPlugin = "1.21.0"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"


    // androidX
    private const val splashVersion = "1.0.0"
    const val viewModel = "2.5.1" // to be deleted when the linked issue at use site is fixed
    private const val browserVersion = "1.4.0"
    private const val dataStoreVersion = "1.0.0"

    const val splash = "androidx.core:core-splashscreen:$splashVersion"
    const val browser = "androidx.browser:browser:${browserVersion}"
    const val room = "androidx.room:room-runtime:2.4.3"
    const val roomKtx = "androidx.room:room-ktx:2.4.3"
    const val roomCompiler = "androidx.room:room-compiler:2.4.3"
    const val prefsDataStore = "androidx.datastore:datastore-preferences:${dataStoreVersion}"
    const val protoDataStore = "androidx.datastore:datastore:${dataStoreVersion}"

    // compose

    private const val navigationVersion = "2.5.1"
    private const val constraintLayoutVersion = "1.1.0-alpha02"
    private const val hiltVersion = "1.0.0"

    const val ui = "androidx.compose.ui:ui:$compose"
    const val tooling = "androidx.compose.ui:ui-tooling:$compose"
    const val animation = "androidx.compose.animation:animation-graphics:$compose"
    const val foundation = "androidx.compose.foundation:foundation:$compose"
    const val activity = "androidx.activity:activity-compose:$compose"
    const val material = "androidx.compose.material:material:$compose"
    const val runtime = "androidx.compose.runtime:runtime:$compose"

    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"
    const val hilt = "androidx.hilt:hilt-navigation-compose:$hiltVersion"

    object accompanist {

        private const val version = "0.25.0"

        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val flowLayout = "com.google.accompanist:accompanist-flowlayout:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val systemUi = "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val navigationAnimations =
            "com.google.accompanist:accompanist-navigation-animation:$version"
    }

    // google stuff
    private const val protobufVersion = "3.21.4"

    const val daggerHiltCore = "com.google.dagger:hilt-core:$daggerHilt"
    const val daggerHiltCoreCompiler = "com.google.dagger:hilt-compiler:$daggerHilt"
    const val daggerHiltAndroid = "com.google.dagger:hilt-android:$daggerHilt"
    const val daggerHiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:$daggerHilt"
    const val protobuf = "com.google.protobuf:protobuf-javalite:${protobufVersion}"
    const val protobufCompiler = "com.google.protobuf:protoc:${protobufVersion}"


    // square stuff
    private const val okHttpVersion = "4.10.0"
    private const val retrofitVersion = "2.9.0"
    private const val retrofitKotlinxSerializationConverterVersion = "0.8.0"

    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${okHttpVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${retrofitVersion}"
    const val retrofitScalarsConverter =
        "com.squareup.retrofit2:converter-scalars:${retrofitVersion}"
    const val retrofitKotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitKotlinxSerializationConverterVersion"

    // misc
    private const val desugaredLibsVersion = "1.1.5"
    private const val kotlinResultVersion = "1.1.16"
    private const val hiltBinderVersion = "1.1.2"
    private const val coilVersion = "2.1.0"
    private const val zoomableVersion = "1.5.1"

    const val desugaredLibs = "com.android.tools:desugar_jdk_libs:${desugaredLibsVersion}"
    const val kotlinResult =
        "com.michael-bull.kotlin-result:kotlin-result:${kotlinResultVersion}"
    const val hiltBinder = "com.paulrybitskyi:hilt-binder:$hiltBinderVersion"
    const val hiltBinderCompiler = "com.paulrybitskyi:hilt-binder-compiler:$hiltBinderVersion"
    const val coil = "io.coil-kt:coil-compose:$coilVersion"
    const val zoomable = "com.mxalbert.zoomable:zoomable:$zoomableVersion"

    // testing
    private const val jUnitVersion = "4.13.2"
    private const val jUnitExtVersion = "1.1.3"
    private const val truthVersion = "1.1.3"
    private const val mockkVersion = "1.13.3"
    private const val turbineVersion = "0.9.0"
    private const val testRunnerVersion = "1.4.0"
    private const val archCoreVersion = "2.1.0"
    private const val mockWebServerVersion = "4.10.0"

    const val jUnit = "junit:junit:$jUnitVersion"
    const val jUnitExt = "androidx.test.ext:junit:$jUnitExtVersion"
    const val truth = "com.google.truth:truth:$truthVersion"
    const val mockk = "io.mockk:mockk:$mockkVersion"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"
    const val archCore = "androidx.arch.core:core-testing:$archCoreVersion"
    const val daggerHiltTest = "com.google.dagger:hilt-android-testing:$daggerHilt"
    const val roomTest = "androidx.room:room-testing:2.4.3"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$mockWebServerVersion"
}
