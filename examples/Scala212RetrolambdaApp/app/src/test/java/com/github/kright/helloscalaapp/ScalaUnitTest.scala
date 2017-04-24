package com.github.kright.helloscalaapp

import org.junit.Test
import org.junit.Assert._

class ScalaUnitTest {

	@Test
	@throws[Exception]
	def addition_isCorrect(): Unit = {
		assertEquals(4, 2 + 2)
	}
}
