package com.github.kright.androidscalasupport

import org.gradle.api.Project

/**
 * Examples of usage:
 *
 * minimal:
 * androidScala {
 *      addScalaLibrary()
 *      multiDexEnabled()
 * }
 *
 * full:
 * androidScala {
 *     scalaVersion '2.11.8'
 *     zincVersion '0.3.11'
 *     addScalaLibrary true
 *     multiDexEnabled true
 *}
 *
 * Created by lgor on 26.11.2016.
 */

class AndroidScalaExtension {

	private Project project

	String scalaVersion = '2.11.8'
	String zincVersion = '0.3.11'

	AndroidScalaExtension(Project currentProject) {
		this.project = currentProject
	}

	def addScalaLibrary(String scalaVersion) {
		this.scalaVersion = scalaVersion
		addScalaLibrary(true)
	}

	def addScalaLibrary(boolean add = true) {
		if (add) {
			project.dependencies {
				compile "org.scala-lang:scala-library:$scalaVersion"
			}
		}
	}

	def multiDexEnabled(boolean enabled = true) {
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
