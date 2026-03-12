plugins {
    id("myapp.android.library")
    id("myapp.android.compose")
    id("myapp.koin")
    id("myapp.kotlin.serialization")
}

android {
    namespace = "dev.alexmester.users"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
}