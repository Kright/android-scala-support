package com.github.kright.androidscalasupport

/**
 * Settings for transformation lines in maindexlist.txt
 *
 * Created by lgor on 26.02.2017.
 */
class MainDexListTransformExtension {

	List<String> blackList = []
	boolean printLog = false        //prints result of maindexlist.txt transformation

	/**
	 * exclude files from list, examples:
	 * exclude akka
	 * exclude scala/util/Classname.class
	 */
	def exclude(banned) {
		blackList << banned
	}

	boolean fileIsExcluded(className) {
		return blackList.any { className.startsWith(it) }
	}
}
