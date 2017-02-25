package com.github.kright.manydepsapp.withheavydependencies

/**
	* this class have 4k methods
	*
	* Created by lgor on 16.02.2017.
	*/
class ClassWith4kMethods {

	def log[@specialized A, @specialized B, @specialized C, @specialized D](a: A, b: B, c: C, d: D): Unit = {}
}
