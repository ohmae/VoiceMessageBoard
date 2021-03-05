buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath(kotlin("gradle-plugin", version = "1.4.31"))
        classpath("com.github.ben-manes:gradle-versions-plugin:0.36.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
