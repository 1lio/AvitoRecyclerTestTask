buildscript {

    var kotlinVer: String by extra
    kotlinVer = "1.4.21"

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer")

    }

    allprojects {

        repositories {
            google()
            jcenter()
            mavenCentral()
        }
    }
}
