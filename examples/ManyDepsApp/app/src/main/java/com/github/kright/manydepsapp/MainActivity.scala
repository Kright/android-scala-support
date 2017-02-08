package com.github.kright.manydepsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity extends AppCompatActivity {
	override protected def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	override protected def onResume {
		super.onResume
		AppActors.helloActor ! "resume"
	}
}
