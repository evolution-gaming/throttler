name := "throttler"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/throttler"))

startYear := Some(2017)

organizationName := "Evolution Gaming"

organizationHomepage := Some(url("http://evolutiongaming.com"))

bintrayOrganization := Some("evolutiongaming")

scalaVersion := "2.12.3"

crossScalaVersions := Seq("2.12.3", "2.11.11")

releaseCrossBuild := true

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture"
)

scalacOptions in (Compile,doc) ++= Seq("-no-link-warnings")

resolvers += Resolver.bintrayRepo("evolutiongaming", "maven")

libraryDependencies ++= Seq(
  "com.evolutiongaming" %% "scala-tools" % "1.11",
  "com.google.guava" % "guava" % "19.0",
  "com.google.code.findbugs" % "jsr305" % "3.0.2",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test

)

licenses := Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))
