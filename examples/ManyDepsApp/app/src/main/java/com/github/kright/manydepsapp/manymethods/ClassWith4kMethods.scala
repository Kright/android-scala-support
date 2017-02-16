package com.github.kright.manydepsapp.manymethods

/**
	* this class have 4k methods
	*
	* Created by lgor on 16.02.2017.
	*/
class ClassWith4kMethods {

	/**
		* this method will be compiled as many methods with all possible combinations of A, B, etc.
		* (for example, A may have 8 values)
		* 8**4 = 4096 methods
		*
		* scala compiler can't compile with 5 paramaters
		*/
	def log[@specialized A, @specialized B, @specialized C, @specialized D](a: A, b: B, c: C, d: D): Unit = {
		//nothing
	}
}