package com.github.kright.androidscalasupport

import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Example:
 *
 * androidScala {
 *     scalaVersion '2.11.8'
 *     zincVersion '0.3.11'  // if skipped will be default
 *     addScalaLibrary true  // adds scala library to dependencies
 *
 *     multiDex {            // optional
 *         enabled true
 *
 *         overwriteMainDex {                   // optional
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
	final Multidex multiDex

	private String scalaVersion = null
	String zincVersion = '0.3.11'

	AndroidScalaExtension(Project currentProject) {
		this.project = currentProject
		this.multiDex = this.extensions.create("multiDex", Multidex, project)
	}

	def scalaVersion(String version) {
		scalaVersion = version
		project.dependencies {
			compile "org.scala-lang:scala-library:$scalaVersion"
		}
	}

	def getScalaVersion() {
		if (scalaVersion == null)
			throw new GradleException("androidScala.scalaVersion wasn't specified")

		return scalaVersion
	}

	static class Multidex {

		private Project project

		private MainDexOverwriteRules dexOverwriteRules = null
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
				project.dependencies {
					compile 'com.android.support:multidex:1.0.1'
				}
			}
		}

		/** getter */
		def getEnabled() {
			return enabled
		}

		/**
		 * overwrites mainDexList from scratch. (only if multiDex is enabled, else shows warning and does nothing)
		 * lazy initialization (if isn't used, maindexlist won't be modified)
		 */
		def getOverwriteMainDex() {
			if (!enabled) {
				project.logger.warn("multiDex is disabled")
				return
			}

			if (!dexOverwriteRules)
				dexOverwriteRules = this.extensions.create("overwriteMainDex", MainDexOverwriteRules)

			return dexOverwriteRules
		}

		/**
		 * @return MainDexOverwriteRules. may be null
		 */
		MainDexOverwriteRules getMainDexOverwriteRule() {
			return enabled ? dexOverwriteRules : null
		}


		static class MainDexOverwriteRules {

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
