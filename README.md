# android-scala-support
It will be gradle plugin for scala support in android projects (but it isn't yet)

### how add:

```
buildscript {
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
    dependencies {
        classpath 'com.github.Kright:android-scala-support:master-SNAPSHOT' // the newest vesion
    }
}
```

in module
```
apply plugin 'scala-android-support'
```
