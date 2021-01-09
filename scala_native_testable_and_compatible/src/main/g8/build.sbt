// give the user a nice default project!
ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.4"

lazy val root = (project in file(".")).settings(
  name := "scala_native_testable_and_compatible"
)

// <https://github.com/scala-native/scala-native.g8> --- {{{

scalaVersion := "2.11.12"

// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

enablePlugins(ScalaNativePlugin)

// --- }}}

// <scala/scalatest-example.g8> --- {{{

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.2" % Test

// --- }}}
