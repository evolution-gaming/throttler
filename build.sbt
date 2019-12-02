name := "throttler"

organization := "com.evolutiongaming"

homepage := Some(new URL("http://github.com/evolution-gaming/throttler"))

startYear := Some(2017)

organizationName := "Evolution Gaming"

organizationHomepage := Some(url("http://evolutiongaming.com"))

bintrayOrganization := Some("evolutiongaming")

scalaVersion := crossScalaVersions.value.head

crossScalaVersions := Seq("2.13.1", "2.12.10")

releaseCrossBuild := true

scalacOptions in (Compile,doc) ++= Seq("-no-link-warnings")

resolvers += Resolver.bintrayRepo("evolutiongaming", "maven")

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.google.guava"            % "guava"         % "19.0",
  "com.google.code.findbugs"    % "jsr305"        % "3.0.2",
  "org.scalatest"              %% "scalatest"     % "3.1.0"  % Test,
  "org.mockito"                 % "mockito-core"  % "3.2.0" % Test)

licenses := Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))
