package com.github.kright.androidscalasupport

import org.gradle.api.Project

/**
 * Example:
 *
 * androidScala {
 *     scalaVersion '2.11.8' // if skipped will be default
 *     zincVersion '0.3.11'  // if skipped will be default
 *     multiDexEnabled true  // may be skipped if already enabled in android{}
 *     addScalaLibrary true  // adds scala library to dependencies
 * }
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

	/**
	 * adds project dependency on "org.scala-lang:scala-library:$scalaVersion"
	 */
	def addScalaLibrary(boolean add = true) {
		if (add) {
			project.dependencies {
				compile "org.scala-lang:scala-library:$scalaVersion"
			}
		}
	}

	/**
	 * adds multiDex option to default config
	 */
	def multiDexEnabled(boolean enabled = true) {
		project.android.defaultConfig.multiDexEnabled = enabled
		if (enabled) {
			project.android.defaultConfig.multiDexEnabled = true
			project.dependencies {
				compile 'com.android.support:multidex:1.0.1'
			}
		}
	}
}
