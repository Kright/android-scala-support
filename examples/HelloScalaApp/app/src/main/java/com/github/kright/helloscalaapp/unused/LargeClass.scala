package com.github.kright.helloscalaapp.unused

/**
	* Created by lgor on 02.03.2017.
	*/
class LargeClass {

	/**
		* this method will be compiled as many methods with all possible combinations of A, B, etc.
		* (for example, A may have 8 values)
		* 8**4 = 4096 methods
		*
		* scala compiler can't compile with 5 paramaters
		*/
	def method[@specialized A, @specialized B, @specialized C, @specialized D](a: A, b: B, c: C, d: D): Unit = {
		//nothing
	}
}


class LargeClass2 {

	/**
		* this method will be compiled as many methods with all possible combinations of A, B, etc.
		* (for example, A may have 8 values)
		* 8**4 = 4096 methods
		*
		* scala compiler can't compile with 5 paramaters
		*/
	def method[@specialized A, @specialized B, @specialized C, @specialized D](a: A, b: B, c: C, d: D): Unit = {
		//nothing
	}
}