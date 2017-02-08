package com.github.kright.manydepsapp

import akka.actor.{Actor, ActorSystem, Props}

/**
	* Created by lgor on 08.02.2017.
	*/
object AppActors {

	val system = ActorSystem("AppActors")
	val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")

	helloActor ! "init"
}

class HelloActor extends Actor {
	def receive = {
		case msg: String => println(msg)
		case smth: Any => println(s"object($smth)")
	}
}
