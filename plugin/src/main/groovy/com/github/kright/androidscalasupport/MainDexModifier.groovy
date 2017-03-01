package com.github.kright.androidscalasupport

import org.gradle.api.GradleException

/**
 * overwrites given maindexlist.txt
 *
 * Created by lgor on 01.03.2017.
 */
class MainDexModifier {

	private final AndroidScalaSupport plugin

	MainDexModifier(AndroidScalaSupport androidScalaSupport) {
		assert androidScalaSupport != null
		plugin = androidScalaSupport
	}

	private final multiDexClasses = [
			'android/support/multiDex/MultiDexExtractor.class',
			'android/support/multiDex/MultiDex.class',
			'android/support/multiDex/MultiDexExtractor$1.class',
			'android/support/multiDex/MultiDex$V19.class',
			'android/support/multiDex/MultiDex$V14.class',
			'android/support/multiDex/MultiDexApplication.class',
			'android/support/multiDex/ZipUtil$CentralDirectory.class',
			'android/support/multiDex/MultiDex$V4.class',
			'android/support/multiDex/ZipUtil.class'
	]

	private List classesFromManifest() {
		throw new GradleException("not implemented yet")
	}

	private writeToFile(File file, List<String> lines) {
		file.withWriter('utf-8') { writer ->
			for (line in lines) {
				writer.writeLine(line)
			}
		}
	}

	def modify(File file, MainDexOverwrite rule) {
		def classes = []

		if (rule.includeMultiDexClasses)
			classes.addAll(multiDexClasses)

		if (rule.includeClassesFromManifest)
			classes.addAll(classesFromManifest())

		classes.addAll(rule.includedClasses)

		writeToFile(file, classes)
	}
}
