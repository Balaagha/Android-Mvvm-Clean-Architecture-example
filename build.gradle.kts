// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val navVersion = "2.3.5"
    val daggerHiltVersion = "2.38.1"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerHiltVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks { // ./gradlew cleanMe
    val cleanMe by registering(Delete::class){
        println("start")
        delete(buildDir)
        println("end")
    }
}