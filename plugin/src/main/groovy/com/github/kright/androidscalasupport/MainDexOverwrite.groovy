package com.github.kright.androidscalasupport

/**
 * builds with more than one manifest probably will work incorrectly
 * If so, you can include classes manually by name
 *
 * Created by lgor on 01.03.2017.
 */
class MainDexOverwrite {

	def includedClasses = []

	boolean includeMultiDexClasses = false
	String pathToManifest = null

	def includeMultiDexClasses(boolean value = true) {
		includeMultiDexClasses = value
	}

	def includeClassesFromManifest(boolean value = true) {
		if (value)
			pathToManifest = "src/main/AndroidManifest.xml"
	}

	def includeClassesFromManifest(String path) {
		pathToManifest = path
	}

	def include(String className) {
		includedClasses << className
	}
}
