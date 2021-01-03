name := "scala-akka-stream"

version := "0.1"

scalaVersion := "2.13.4"

lazy val akkaVersion = "2.6.10"
lazy val scalaTestVersion = "3.2.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion
)