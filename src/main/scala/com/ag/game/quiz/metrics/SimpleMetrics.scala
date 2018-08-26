package com.ag.game.quiz.metrics

/**
  * Created by weiwen on 17-7-12.
  */
import io.prometheus.client.{CollectorRegistry, Counter, Gauge, Histogram}


object SimpleMetrics {

  val registry: CollectorRegistry = new CollectorRegistry()

  val finagleServerLatency: Histogram = Histogram.build()
    .name("finagle_server_latency")
    .help("finagle call latency")
    .labelNames("fn")
    .register(registry)

  val aerospikeLatency = Histogram.build()
    .name("aerospike_latency")
    .help("aerospike latency")
    .labelNames("action")
    .register(registry)
    
  val kafkaSentCounter: Counter = Counter.build()
    .name("sent_to_kafka_total")
    .help("sent_to_kafka_total")
    .register()

  val httpRequestLatency: Histogram = Histogram.build()
    .name("http_request_latency")
    .help("http request latency")
    .labelNames("api")
    .register(registry)

  val inProcessGames: Gauge = Gauge.build()
    .name("in_process_games")
    .help("in process games")
    .labelNames("type")
    .register()

  val wxLoginLatency: Histogram = Histogram.build()
    .name("wx_login_latency")
    .help("wx login latency")
    .register(registry)

}
