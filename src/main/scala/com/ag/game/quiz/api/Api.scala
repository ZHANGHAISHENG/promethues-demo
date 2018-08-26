package com.ag.game.quiz.api

import akka.http.scaladsl.server.Route


trait Api {

  def routes: Route

}
