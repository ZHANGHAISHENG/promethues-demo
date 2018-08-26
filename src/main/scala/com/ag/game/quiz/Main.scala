package com.ag.game.quiz

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Success, Try}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import com.ag.game.quiz.context.GlobalContext


// $COVERAGE-OFF$
object Main extends App {

  private[this] val logger: Logger = Logger(getClass)

 private[this] implicit val system: ActorSystem = ActorSystem("promethus-test")

  private[this] implicit val mat: ActorMaterializer = ActorMaterializer()

  implicit val ec: ExecutionContextExecutor = system.dispatcher

  implicit val ctx: GlobalContext = GlobalContext()

  val bindingFuture: Future[Http.ServerBinding] = HttpService()

  sys.addShutdownHook {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete { _ =>
        logger.info("system terminated. Goodbye.")
      }

  }

  Await.result(system.whenTerminated, Duration.Inf)

}

// $COVERAGE-OFF$
