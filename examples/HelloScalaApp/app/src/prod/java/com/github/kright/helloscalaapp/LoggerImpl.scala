package com.github.kright.helloscalaapp

class LoggerImpl extends Logger {

	def apply(msg: Any): Unit = {
		println(s"msg from prod: '$msg'")
	}
}
