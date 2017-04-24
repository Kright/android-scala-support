package com.github.kright.helloscalaapp

import android.support.multidex.MultiDexApplication
import android.util.Log

/**
	* Created by lgor on 04.03.2017.
	*/
class DevApplication extends MultiDexApplication {

	override def onCreate(): Unit = {
		super.onCreate()
		Log.d("DevApplication", "onCreate")
	}
}
