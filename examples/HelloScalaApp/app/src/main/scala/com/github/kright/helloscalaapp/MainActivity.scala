package com.github.kright.helloscalaapp

import android.os.Bundle
import android.app.Activity
import android.graphics.Point

import com.github.kright.hellolibrary.{LibraryClass, LibraryScalaClass}
import com.github.kright.hellolibrary.Extensions._

class MainActivity extends Activity {

	override def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val logger = new LoggerImpl()
		logger("hello world! " + LibraryClass.getNumber() + ", " + (new LibraryScalaClass).getNumber())

		val point = new Point()
		point := (1, 2)
	}
}

trait Logger {
	def apply(msg: Any): Unit
}
