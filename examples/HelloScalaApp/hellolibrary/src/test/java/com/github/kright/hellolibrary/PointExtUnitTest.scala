package com.github.kright.hellolibrary

import android.graphics.Point
import org.junit.Test
import org.junit.Assert._

import com.github.kright.hellolibrary.Extensions._

/**
	* Created by lgor on 30.12.2016.
	*/
class PointExtUnitTest {

	@Test
	@throws[Exception]
	def javaTest(): Unit = {
		assertEquals(LibraryClass.getNumber(), 4)
	}

	@Test
	@throws[Exception]
	def scalaTest(): Unit = {
		assertEquals((new LibraryScalaClass).getNumber(), 42)
	}

	@Test
	@throws[Exception]
	def assignmentTest {
		val p = new Point()

		assertEquals(p.x, 0)
		assertEquals(p.y, 0)

		p := (1, 2)

		assertEquals(p.x, 1)
		assertEquals(p.y, 2)

		val p2 = new Point()
		p2 := p

		assertEquals(p2.x, 1)
		assertEquals(p2.y, 2)
	}
}
