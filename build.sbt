name := "throttler"

organization := "com.evolutiongaming"

homepage := Some(url("https://github.com/evolution-gaming/throttler"))

startYear := Some(2017)

organizationName := "Evolution"

organizationHomepage := Some(url("https://evolution.com"))

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("3.8.4", "2.13.18")

ThisBuild / versionScheme := Some("early-semver")

Compile / doc / scalacOptions ++= Seq("-no-link-warnings")

scalacOptions -= "-Xfatal-warnings"
scalacOptions += "-Werror"

publishTo := Some(Resolver.evolutionReleases)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.6",
  "com.google.guava" % "guava" % "19.0",
  "com.google.code.findbugs" % "jsr305" % "3.0.2",
  "org.scalatest" %% "scalatest" % "3.2.20" % Test,
  "org.mockito" % "mockito-core" % "5.23.0" % Test,
)

licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT")))

//addCommandAlias("check", "all versionPolicyCheck Compile/doc")
addCommandAlias("check", "show version")
addCommandAlias("build", "+all compile test")
