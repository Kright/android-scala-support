# Android scala support
Gradle plugin for scala support in android projects.

Plugin replaces compileJava tasks with compileScala tasks.
### Usage:

Add to main build.gradle:
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

Add to module build.gradle:

```groovy
// has to be applied after andoid plugin
apply plugin: 'android-scala-support'
```

In common cases scalaVersion and multidex are required:
```
androidScala {
    scalaVersion '2.11.8'
    multiDex.enabled true    
}
```
with next line in manifest:
```
android:name="android.support.multidex.MultiDexApplication"
```


####All options

```groovy
// This has to be after android{} block

androidScala {
    scalaVersion '2.11.8' 
    zincVersion '0.3.11'  // if skipped will be '0.3.11'
    
    multiDex {            // helps to avoid 65k methods limit
        enabled true      // false by default
        
        overwriteMainDex {
            /* even if multiDex enabled, 
             * android plugin places all classes from src to maindex. 
             * If you have too many of them, 
             * you can overwrite maindexlist with only necessary classes
             */
            includeMultiDexClasses true         
            includeClassesFromManifest true 
            include 'com/smth/Classname.class'
        }
    }
}
```
