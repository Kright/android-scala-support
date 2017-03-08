package com.github.kright.androidscalasupport

/**
 * overwrites given maindexlist.txt
 *
 * Created by lgor on 01.03.2017.
 */
class MainDexModifier {

	private final static multiDexClasses = [
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

	private final AndroidScalaSupport plugin

	MainDexModifier(AndroidScalaSupport androidScalaSupport) {
		assert androidScalaSupport != null
		plugin = androidScalaSupport
	}

	private List classesFromManifest(File manifestFile) {
		assert manifestFile != null
		assert manifestFile.exists()

		def xml = new XmlSlurper().parse(manifestFile)

		def name = xml.application[0].@'android:name'

		return ["$name".replace('.' as char, '/' as char) + ".class"]
	}

	private writeToFile(File file, List<String> lines) {
		file.withWriter('utf-8') { writer ->
			for (line in lines) {
				writer.writeLine(line)
			}
		}
	}

	def modify(File mainDexList, AndroidScalaExtension.Multidex.MainDexOverwriteRule rule, File manifest) {
		assert mainDexList != null
		assert rule != null

		def classes = []

		if (rule.includeMultiDexClasses)
			classes.addAll(multiDexClasses)

		if (rule.includeClassesFromManifest)
			classes.addAll(classesFromManifest(manifest))

		classes.addAll(rule.includedClasses)

		writeToFile(mainDexList, classes.unique())
	}
}
