buildscript {

    val kotlinVer: String by extra { "1.4.30" }
    val hiltVer: String by extra { "2.31.2-alpha" }

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVer")

    }

    allprojects {

        repositories {
            google()
            jcenter()
            mavenCentral()
        }
    }
}
