package com.github.kright.androidscalasupport

/**
 * Created by lgor on 01.03.2017.
 */
class MainDexOverwrite {

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
