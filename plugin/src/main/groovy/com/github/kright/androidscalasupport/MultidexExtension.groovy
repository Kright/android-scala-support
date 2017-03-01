package com.github.kright.androidscalasupport

import org.gradle.api.Project

/**
 * Created by lgor on 01.03.2017.
 */
class MultidexExtension {

	private final Project project

	private MainDexOverwrite mainDexModifier = null
	private boolean enabled = false

	MultidexExtension(Project project) {
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
	 * overwrites mainDexList from scratch. (only if multiDex is enabled, else does nothing)
	 *
	 * Example:
	 * overwriteMainDex {
	 *     includeMultiDexClasses true
	 *     includeClassesFromManifest true
	 *     include 'com/smth/Classname.class'
	 *}
	 */
	def overwriteMainDex(Closure closure) {
		if (!enabled) {
			project.logger.warn("multiDex is disabled")
			return
		}

		mainDexModifier = new MainDexOverwrite()
		closure.delegate = mainDexModifier
		closure.resolveStrategy = Closure.DELEGATE_FIRST
		closure.call()
	}

	/**
	 * @return mainDexModifier
	 */
	def getMainDexModifier() {
		return mainDexModifier
	}
}
