package com.github.kright.helloscalaapp

import android.test.ActivityInstrumentationTestCase2
import android.widget.TextView

class ApplicationScalaTest extends ActivityInstrumentationTestCase2[MainActivity](classOf[MainActivity]) {
	def testActivityContent(): Unit = {
		val textView = getActivity.findViewById(R.id.text_view_hello).asInstanceOf[TextView]
		// assertEquals(textView.getText, "Hello World!")
		// todo: fix it
		assert(textView.getText == "Hello World!")
	}
}
