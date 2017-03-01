package com.github.kright.androidscalasupport

/**
 * Created by lgor on 01.03.2017.
 */
class MainDexOverwrite {

	def includedClasses = []

	boolean includeMultiDexClasses = false
	boolean includeClassesFromManifest = false

	def includeMultiDexClasses(boolean value = true) {
		includeMultiDexClasses = value
	}

	def includeClassesFromManifest(boolean value = false) {
		includeClassesFromManifest = value
	}

	def include(String className) {
		includedClasses << className
	}
}
