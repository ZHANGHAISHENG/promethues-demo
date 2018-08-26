package com.ag.game.quiz.context

import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import akka.stream.Materializer

/**
  * Created by weiwen on 17-6-24.
  */
object GlobalContext {
  def apply()(implicit actorSystem: ActorSystem, mt: Materializer): GlobalContext = new GlobalContext {
    implicit lazy val system: ActorSystem = actorSystem
    implicit lazy val mat: Materializer = mt
  }
}


trait GlobalContext extends HasGlobalContext {

}

trait HasGlobalContext {

  implicit def system: ActorSystem

  implicit def mat: Materializer


}