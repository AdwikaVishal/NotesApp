plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false // Correctly define KSP plugin version
}

dependencies {
    // Define any shared dependencies here
}
repositories {
    google()
    mavenCentral()
}
