name := """backend"""
organization := "vauldex"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "org.playframework" %% "play-slick" % "6.1.1",
  "org.playframework" %% "play-slick-evolutions" % "6.1.1",
  "com.h2database" % "h2" % "2.3.232",
  guice
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "vauldex.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "vauldex.binders._"
