package com.github.kright.manydepsapp

import akka.actor.{Actor, ActorSystem, Props}
import android.util.Log

/**
	* Created by lgor on 08.02.2017.
	*/
object AppActors {

	val system = ActorSystem("AppActors")
	val logActor = system.actorOf(Props[LogActor], name = "logActor")

	logActor ! "init"
}

class LogActor extends Actor {
	def receive = {
		case msg: String => Log.d("LogActor", msg)
		case smth: Any => Log.d("LogActor", s"object($smth)")
	}
}
