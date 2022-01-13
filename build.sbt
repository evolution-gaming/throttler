name := "throttler"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/throttler"))

startYear := Some(2017)

organizationName := "Evolution"

organizationHomepage := Some(url("http://evolution.com"))

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.13.8", "2.12.13")

releaseCrossBuild := true

Compile / doc / scalacOptions ++= Seq("-no-link-warnings")

publishTo := Some(Resolver.evolutionReleases)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.2",
  "com.google.guava"            % "guava"                 % "19.0",
  "com.google.code.findbugs"    % "jsr305"                % "3.0.2",
  "org.scalatest"              %% "scalatest"             % "3.2.9"       % Test,
  "org.mockito"                 % "mockito-core"          % "3.4.4"       % Test,
  "org.scalatestplus"          %% "scalatestplus-mockito" % "1.0.0-M2" % Test)

licenses := Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))
