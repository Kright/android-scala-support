# android-scala-support
It will be gradle plugin for scala support in android projects.

Plugin replaces compileJava tasks with compileScala tasks, but I didn't test on large projects.

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
// needs to be applied after android plugin
apply plugin: 'android-scala-support'

// It has to be after android{} block
// I know that it isn't good practice and will change it in future
androidScala {
    scalaVersion '2.11.8' // adds scala library as dependency
    zincVersion '0.3.11'  // may be skipped
    multiDexEnabled true  // may be skipped if already enabled
}
```
