package com.github.kright.androidscalasupport

import org.gradle.api.Project

/**
 * Example:
 *
 * androidScala {
 *     scalaVersion '2.11.8' // if skipped will be default
 *     zincVersion '0.3.11'  // if skipped will be default
 *     addScalaLibrary true  // adds scala library to dependencies
 *
 *     multiDex {            // optional
 *         enabled true
 *
 *         overwriteMainDex {                   //optional
 *              includeMultiDexClasses true
 *              includeClassesFromManifest false
 *              include 'com/github/smth/Classname.class'
 *         }
 *     }
 * }
 *
 * Created by lgor on 26.11.2016.
 */

class AndroidScalaExtension {

	private final Project project

	Multidex multiDex

	String scalaVersion = '2.11.8'
	String zincVersion = '0.3.11'

	AndroidScalaExtension(Project currentProject) {
		this.project = currentProject
		this.multiDex = this.extensions.create("multiDex", Multidex, project)
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


	static class Multidex {

		private Project project

		private MainDexOverwriteRule dexOverwriteRule = null
		private boolean enabled = false

		Multidex(Project project) {
			this.project = project
		}

		/**
		 * enables multiDex
		 */
		def enabled(boolean value) {
			enabled = value
			project.android.defaultConfig.multiDexEnabled = enabled
			if (enabled) {
				project.android.defaultConfig.multiDexEnabled = true
				project.dependencies {
					compile 'com.android.support:multidex:1.0.1'
				}
			}
		}

		/**
		 * overwrites mainDexList from scratch. (only if multiDex is enabled, else shows warning and does nothing)
		 *
		 * Example:
		 * overwriteMainDex {
		 *     includeMultiDexClasses true
		 *     includeClassesFromManifest true
		 *     include 'com/smth/Classname.class'
		 * }
		 */
		def overwriteMainDex(Closure closure) {
			if (!enabled) {
				project.logger.warn("multiDex is disabled")
				return
			}

			dexOverwriteRule = new MainDexOverwriteRule()
			closure.delegate = dexOverwriteRule
			closure.resolveStrategy = Closure.DELEGATE_FIRST
			closure.call()
		}

		/**
		 * @return MainDexOverwriteRule. may be null
		 */
		MainDexOverwriteRule getMainDexOverwriteRule() {
			return dexOverwriteRule
		}


		class MainDexOverwriteRule {

			def includedClasses = []

			boolean includeMultiDexClasses = false
			boolean includeClassesFromManifest = false

			def includeMultiDexClasses(boolean include = true) {
				includeMultiDexClasses = include
			}

			def includeClassesFromManifest(boolean include = true) {
				includeClassesFromManifest = include
			}

			def include(String className) {
				includedClasses << className
			}
		}
	}
}
