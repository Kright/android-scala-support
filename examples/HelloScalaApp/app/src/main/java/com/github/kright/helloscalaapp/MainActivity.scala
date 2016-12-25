package com.github.kright.helloscalaapp

import android.os.Bundle
import android.app.Activity

class MainActivity extends Activity {

	override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}
}
