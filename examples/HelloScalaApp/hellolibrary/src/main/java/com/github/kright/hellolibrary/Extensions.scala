package com.github.kright.hellolibrary

import android.graphics.Point

/**
	* Created by lgor on 30.12.2016.
	*/
object Extensions {

	implicit class PointExtension(val p: Point) extends AnyVal {

		def :=(x: Int, y: Int): Unit = {
			p.x = x
			p.y = y
		}

		def :=(r: Point): Unit = p := (r.x, r.y)

		def +=(dx: Int, dy: Int): Unit = {
			p.x += dx
			p.y += dy
		}

		def +=(r: Point): Unit = p += (r.x, r.y)
	}

}
