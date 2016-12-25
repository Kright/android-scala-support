package com.github.kright.androidscalasupport

import org.gradle.api.Project

/**
 * Created by lgor on 26.11.2016.
 */

class AndroidScalaExtension {

	Project project

	String scalaVersion
	String zincVersion = "0.3.12"

	def scalaVersion(String version) {
		scalaVersion = version
		project.dependencies {
			compile "org.scala-lang:scala-library:$version"
		}
	}

	def multiDexEnabled(boolean enabled) {
		project.android.defaultConfig.multiDexEnabled = enabled
		if (enabled) {
			int minSdk = project.android.defaultConfig.minSdkVersion.apiLevel
			multiDexEnabled(minSdk)
		}
	}

	def multiDexEnabled(int minSdkVersion) {
		project.android.defaultConfig.multiDexEnabled = true
		// android with version 21 and higher uses ART and doesn't need library
		if (minSdkVersion <= 20) {
			project.dependencies {
				compile 'com.android.support:multidex:1.0.1'
			}
		}
	}
}
