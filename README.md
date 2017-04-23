# Android scala support
Gradle plugin for scala support in android projects.

Features:

1. Compiling scala code.
2. Placing to mainDex only minimal set of classes.

Android plugin puts all classes from /src (and all classes which are used by them) to mainDex file. Whole scala library and all your code will be in mainDex even if multiDex enabled. So, you can run into 65k methods limit.

This plugin allows you to place to mainDex only minimal set of classes, which will change classloader, and after that application classes will be loaded from any dex file.

### Getting Started

Add to main build.gradle:
```groovy
buildscript {
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
    dependencies {
        classpath 'com.github.kright:android-scala-support:master-SNAPSHOT' // the newest version
    }
}
```

Add to module build.gradle:

```groovy
// has to be applied after andoid plugin
apply plugin: 'com.github.kright.android-scala-support'
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

#### All options

```groovy
// This has to be after android{} block

androidScala {
    scalaVersion '2.11.8' // necessary property
    zincVersion '0.3.11'  // if skipped will be '0.3.11'

    multiDex {            // helps to avoid 65k methods limit
        enabled true      // false by default

        mainDex {
            /* even if multiDex enabled,
             * android plugin places all classes from src to maindex.
             * If you have too many of them,
             * you can overwrite maindexlist with only necessary classes
             */
            addMultiDex true
            addApplication true
            add 'com/smth/Classname.class'
        }
    }
}
```

### Examples

* [HelloApp](https://github.com/Kright/android-scala-support/tree/master/examples/HelloApp): simple java app with loading plugin from jitpack:

For testing purposes other examples use jar file with plugin, but in the rest they may be useful:

* [HelloScalaApp](https://github.com/Kright/android-scala-support/tree/master/examples/HelloScalaApp): Contains two modules - app and library, both of them with scala code.
* [ProguardApp](https://github.com/Kright/android-scala-support/tree/master/examples/ProguardApp)
* To be sure that android-scala-plugin can build something big, I built [Antox project](https://github.com/Kright/Antox/tree/change-plugin) with my plugin instead of another [scala plugin](https://github.com/saturday06/gradle-android-scala-plugin).

### Known issues

* Log message "Pruning sources from previous analysis, due to incompatible CompileSetup." for scala compile task. This is okay on clean build and shouldn't be on incremental. [Info](https://github.com/sbt-compiler-maven-plugin/sbt-compiler-maven-plugin/issues/26), [sbt sources](https://github.com/sbt/sbt/commit/b70345c61b288276d5237261458c6676f34b696d).
* Plugin decreases warning level of lint for "Invalid package". Warning is still shown, but it won't prevent project build. Reason of warning: "(scala-library-2.11.8) Invalid package reference in library; not included in Android: java.lang.management. Referenced from scala.sys.process.package.)"

### Contributing

Next command builds plugin and all test projects in /examples folder.
```
./gradlew testsCheck
```

If you found any issue, feel free to report it.
