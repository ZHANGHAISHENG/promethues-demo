package com.ag.game.quiz.api

import java.io.StringWriter
import java.lang.management.ManagementFactory
import javax.management.ObjectName

import scala.util._
import scala.concurrent._

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import io.prometheus.client.hotspot.DefaultExports
import com.ag.game.quiz.metrics.SimpleMetrics
import io.circe.{Json, JsonObject}
import io.circe.syntax._
import io.circe.parser._

object MainApi {
  def apply()(implicit actorSystem: ActorSystem): MainApi = new MainApi {
    lazy val system: ActorSystem = implicitly[ActorSystem]
  }
}


trait MainApi extends Api {

  implicit def system: ActorSystem

  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val routes: Route = rootR ~ metricsR ~ akkaStatsR ~ loginR

  DefaultExports.initialize()

  def rootR: Route = path("status") {
    get {
      onComplete {
        Future.successful("ok.")
      } {
        case Success(x) => encodeResponse(complete(x))
        case _ => complete(StatusCodes.InternalServerError)
      }
    }
  }

  def metricsR: Route = path("metrics") {
    implicit val marshaller: ToEntityMarshaller[String] = Marshaller.StringMarshaller
    get {
      val sw = new StringWriter(1024)
      TextFormat.write004(sw, SimpleMetrics.registry.metricFamilySamples())
      TextFormat.write004(sw, CollectorRegistry.defaultRegistry.metricFamilySamples())
      encodeResponse(complete(sw.toString))
    }
  }

  def akkaStatsR = path("diag" / "akka") {
    complete(akkaMBeansStatsPresentation.asJson.noSpaces)
  }

  def loginR =  path("api" / "v1" / "login") {
    val timer = SimpleMetrics.httpRequestLatency.labels("/api/v1/login").startTimer()
    val t = Random.nextInt(3000)
    Thread.sleep(t)
    timer.observeDuration()
    complete("ok! time:" + t)
  }

  def akkaMBeansStatsPresentation: JsonObject = {
    val mserver = ManagementFactory.getPlatformMBeanServer
    val cn = new ObjectName("akka:type=Cluster")
    JsonObject(
      "Available" -> Json.fromString(mserver.getAttribute(cn, "Available").toString),
      "ClusterStatus" -> parse(mserver.getAttribute(cn, "ClusterStatus").toString).getOrElse(Json.Null),
      "Leader" -> Json.fromString(mserver.getAttribute(cn, "Leader").toString),
      "MemberStatus" -> Json.fromString(mserver.getAttribute(cn, "MemberStatus").toString),
      "Members" -> Json.fromString(mserver.getAttribute(cn, "Members").toString),
      "Unreachable" -> Json.fromString(mserver.getAttribute(cn, "Unreachable").toString)
    )
  }
}
