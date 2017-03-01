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
 *     multiDex{
 *         enabled true
 *
 *         overwriteMainDex {                   //optional
 *              includeMultiDexClasses true
 *              includeClassesFromManifest false
 *              include 'com/github/smth/Classname.class'
 *         }
 *     }
 *}
 *
 * Created by lgor on 26.11.2016.
 */

class AndroidScalaExtension {

	private final Project project

	MultidexExtension multiDex

	String scalaVersion = '2.11.8'
	String zincVersion = '0.3.11'

	AndroidScalaExtension(Project currentProject) {
		this.project = currentProject
		this.multiDex = this.extensions.create("multiDex", MultidexExtension, currentProject)
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
}
