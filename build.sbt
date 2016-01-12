name := """dondoroke"""

organization := "com.jamesneve"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.4", "2.11.7")

libraryDependencies ++= Seq(
  "org.scalatestplus" %% "play" % "1.4.0-M3",
  "org.scalatest" %% "scalatest" % "2.2.1"
)

import bintray.Keys._

lazy val commonSettings = Seq(
  version := "1.0.3",
  organization := "com.jamesneve"
)

lazy val root = (project in file(".")).enablePlugins(SbtTwirl).
  settings(commonSettings ++ bintrayPublishSettings: _*).
  settings(
    sbtPlugin := true,
    name := "Dondoroke",
    description := "Pagination library for Play 2.4",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := false,
    repository := "sbt-plugins",
    bintrayOrganization in bintray := None
  )

sourceDirectories in (Compile, TwirlKeys.compileTemplates) := (unmanagedSourceDirectories in Compile).value

com.typesafe.sbt.SbtGit.versionWithGit
