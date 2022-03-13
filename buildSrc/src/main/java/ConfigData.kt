object ConfigData {
    const val compileSdk = 31
    const val applicationId = "com.example.androidmvvmcleanarchitectureexample"
    const val minSdk = 21
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    const val jvmTarget = "1.8"

    const val releaseMinifyEnabled = true
    const val debugMinifyEnabled = false
    const val defaultProguardFile = "proguard-android-optimize.txt"
    const val proguardRules = "proguard-rules.pro"

    const val dataBinding = true

}

object Modules {
    const val data = ":data"
    const val domain = ":domain"
    const val presentation = ":presentation"
    const val prettyPopUp = ":prettyPopUp"
    const val actionChooser = ":actionChooser"
    const val appTutorial = ":appTutorial"
    const val imagesSlider = ":imagesSlider"
}