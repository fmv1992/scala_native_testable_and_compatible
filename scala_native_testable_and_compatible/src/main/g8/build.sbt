// give the user a nice default project!

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.3"

val versionsJVM = Seq(scala211, scala212, scala213)
val versionsNative = Seq(scala211)

inThisBuild(
  List(
    scalaVersion := scala213,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.3",
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(
      scalaVersion.value
    ),
    // https://index.scala-lang.org/ohze/scala-rewrites/scala-rewrites/0.1.10-sd?target=_2.13
    semanticdbEnabled := true,
    semanticdbOptions += "-P:semanticdb:synthetics:on", // make sure to add this
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(
      scalaVersion.value
    )
  )
)

lazy val commonSettings = Seq(
  Compile / scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n == 11 => Seq()
      case Some((2, n)) if n == 12 => Seq("-Xlint:unused")
      case Some((2, n)) if n == 13 =>
        Seq(
          "-deprecation",
          "-feature",
          "-P:semanticdb:synthetics:on",
          "-Wunused",
          "-Yrangepos",
          "-Ywarn-dead-code"
        )
    }
  }
)

lazy val commonDependencies = Seq(
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n == 11 =>
        List(
          "org.scala-lang.modules" %%% "scala-collection-compat" % "2.2.0",
          "org.scalatest" %%% "scalatest" % "3.2.2" % Test
        )
      case Some((2, n)) if n == 12 =>
        List(
          "com.sandinh" %% "scala-rewrites" % "0.1.10-sd",
          "org.scala-lang.modules" %%% "scala-collection-compat" % "2.2.0",
          "org.scalatest" %%% "scalatest" % "3.2.2" % Test
        )
      case Some((2, n)) if n == 13 =>
        List(
          "com.sandinh" %% "scala-rewrites" % "0.1.10-sd",
          "org.scala-lang.modules" %%% "scala-collection-compat" % "2.2.0",
          "org.scalatest" %%% "scalatest" % "3.2.2" % Test
        )
      case _ => Nil
    }
  }
)

lazy val commonSettingsAndDependencies = commonSettings ++ commonDependencies

lazy val scalaNativeSettings = Seq(
  crossScalaVersions := versionsNative,
  scalaVersion := scala211, // allows to compile if scalaVersion set not 2.11
  nativeLinkStubs := true,
  nativeLinkStubs in runMain := true,
  nativeLinkStubs in Test := true,
  Test / nativeLinkStubs := true,
  sources in (Compile, doc) := Seq.empty
)

lazy val crossProj: sbtcrossproject.CrossProject =
  crossProject(JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("."))
    .settings(
      name := "scala_native_testable_and_compatible"
    )
    .settings(commonSettingsAndDependencies)
    .jvmSettings(
      crossScalaVersions := versionsJVM
    )
    .nativeSettings(
      scalaNativeSettings
    )

lazy val crossProjectJVM: sbt.Project =
  crossProj.jvm

lazy val crossProjectNative: sbt.Project =
  crossProj.native

lazy val root: sbt.Project = (project in file("."))
  .settings(
    publish / skip := true,
    doc / aggregate := false,
    crossScalaVersions := Nil,
    packageDoc / aggregate := false
  )
  .aggregate(
    crossProjectJVM,
    crossProjectNative
  )

// <https://github.com/scala-native/scala-native.g8> --- {{{

// --- }}}

// <scala/scalatest-example.g8> --- {{{

// --- }}}
