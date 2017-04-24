package com.github.kright.hellolibrary

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert._

/**
	* Instrumentation test, which will execute on an Android device.
	*
	* @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
	*/
@RunWith(AndroidJUnit4.class)
class ExampleInstrumentedTest {

	@Test
	@throws[Exception]
	def useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getTargetContext();

		assertEquals("com.github.kright.hellolibrary.test", appContext.getPackageName());
	}
}
