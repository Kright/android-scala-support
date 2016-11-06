# android-scala-support
It will be gradle plugin for scala support in android projects (but it isn't yet)

### how to use:

in main build.gradle
```groovy
buildscript {
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
    dependencies {
        classpath 'com.github.Kright:android-scala-support:master-SNAPSHOT' // the newest version
    }
}
```

in module
```groovy
apply plugin: 'android-scala-support'
```
