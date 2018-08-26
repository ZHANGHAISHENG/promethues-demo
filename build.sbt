name := "promethus-demo"
version := "0.1"
scalaVersion := "2.12.4"

val akkaV = "2.5.13"
val akkaHttpV = "10.1.3"
val catsV = "1.0.1"
val circeV = "0.9.1"
val finagleV = "18.5.0"
val prometheusV = "0.3.0"
val slickV = "3.2.3"
val nettyV = "4.1.25.Final"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaV
  , "com.typesafe.akka" %% "akka-actor" % akkaV
  , "com.typesafe.akka" %% "akka-stream" % akkaV
  , "com.typesafe.akka" %% "akka-cluster" % akkaV
  , "com.typesafe.akka" %% "akka-cluster-tools" % akkaV
  , "com.typesafe.akka" %% "akka-cluster-metrics" % akkaV
  , "com.typesafe.akka" %% "akka-remote" % akkaV
  , "com.typesafe.akka" %% "akka-protobuf" % akkaV
  , "com.typesafe.akka" %% "akka-http-core" % akkaHttpV
  , "com.typesafe.akka" %% "akka-http" % akkaHttpV
  , "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV
  , "io.prometheus" % "simpleclient" % prometheusV
  , "io.prometheus" % "simpleclient_hotspot" % prometheusV
  , "io.prometheus" % "simpleclient_common" % prometheusV
  , "org.apache.thrift" % "libthrift" % "0.10.0"
  , "com.twitter" %% "scrooge-core" % finagleV exclude("com.twitter", "libthrift")
  , "com.twitter" %% "scrooge-serializer" % finagleV exclude("com.twitter", "libthrift")
  , "com.twitter" %% "finagle-thrift" % finagleV exclude("com.twitter", "libthrift")
  , "com.twitter" %% "finagle-thriftmux" % finagleV exclude("com.twitter", "libthrift")
  , "com.twitter" %% "finagle-http" % finagleV exclude("com.twitter", "libthrift")
  , "io.circe" %% "circe-core" % circeV
  , "io.circe" %% "circe-generic" % circeV
  , "io.circe" %% "circe-parser" % circeV
  , "io.circe" %% "circe-generic-extras" % circeV
  , "com.typesafe.akka" %% "akka-stream-kafka" % "0.20"
  , "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0"
  , "ch.qos.logback" % "logback-classic" % "1.2.3"
  , "com.typesafe.slick" %% "slick" % slickV
  , "com.typesafe.slick" %% "slick-hikaricp" % slickV
  , "org.postgresql" % "postgresql" % "42.2.1"
  , "com.github.blemale" %% "scaffeine" % "2.2.0"
  , "com.github.tminglei" %% "slick-pg" % "0.16.2"
  , "com.github.tminglei" %% "slick-pg_circe-json" % "0.16.0"
  , "com.google.guava" % "guava" % "23.6-jre"
  , "com.pauldijou" %% "jwt-circe" % "0.16.0"
  , "com.twitter" %% "chill-akka" % "0.9.2"
  , "com.aerospike" % "aerospike-client" % "4.1.7"
  , "commons-logging" % "commons-logging" % "1.2" //needed by apache
).map(_.excludeAll(
  ExclusionRule("log4j", "log4j")
  , ExclusionRule("org.slf4j", "slf4j-log4j12")
))

