package com.ag.game.quiz

import scala.concurrent.Future
import scala.util.Try
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.headers.RawHeader
import com.ag.game.quiz.api._
import com.ag.game.quiz.context.GlobalContext


object HttpService {

  val corsHeaders: List[RawHeader] = List(
    RawHeader("Access-Control-Allow-Origin", "*"),
//    RawHeader("Access-Control-Allow-Credentials", "true"),
//    RawHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE"),
//    RawHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, Auth-Token, Set-Auth-Token, Refresh-Token, Set-Refresh-Token")
  )

  private[this] val logger = com.typesafe.scalalogging.Logger(getClass)

  def apply()(implicit ctx: GlobalContext
  ): Future[Http.ServerBinding] = {

    implicit val system: ActorSystem = ctx.system

    implicit val mat: Materializer = ctx.mat

    import system.dispatcher

    val config = system.settings.config

    val address = config.getString("http.host")
    val port = config.getInt("http.port")
    val debug: Boolean = Try(config.getBoolean("debug")).getOrElse(false)

    val apis: List[Api] = List(
      MainApi()
    )

    val routes: Route = apis.map(_.routes).reduceLeft(_ ~ _)

    val bindingFuture = if (debug) {
      // 允许跨域，仅用于测试环境
      Http().bindAndHandle(respondWithHeaders(corsHeaders)(routes), address, port)
    } else {
      // 不允许跨域，用于生产环境
      Http().bindAndHandle(routes, address, port)
    }

    try {
      val stream = getClass.getResourceAsStream("/issue.txt")
      val text = scala.io.Source.fromInputStream(stream).mkString.replace("guaguagua!", s"""Server online at http://$address:$port/ ...""")
      stream.close()
      logger.info(text)
      logger.info(s"""Server online at http://$address:$port/ ...""")
    } finally {}

    bindingFuture
  }

}
