// give the user a nice default project!

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.4"

val versionsJVM = Seq(scala211, scala212, scala213)
val versionsNative = Seq(scala211)

inThisBuild(
  List(
    scalaVersion := scala213,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.3",
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(
      scalaVersion.value
    ),
    semanticdbEnabled := true, // enable SemanticDB
    semanticdbVersion := scalafixSemanticdb.revision // use Scalafix compatible version
  )
)

lazy val commonSettings = Seq(
  scalacOptions ++= (Seq("-feature", "-deprecation")
    ++
      Seq(
        "-P:semanticdb:synthetics:on",
        "-Yrangepos",
        "-Ywarn-dead-code",
        "-deprecation",
        "-feature",
        "-Wunused"
        // "-Xfatal-warnings",
        // "-Ywarn-unuse"
      )
      ++ sys.env.get("SCALAC_OPTS").getOrElse("").split(" ").toSeq)
)

lazy val commonDependencies = Seq(
  libraryDependencies += "org.scala-lang.modules" %%% "scala-collection-compat" % "2.2.0",
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.2" % Test
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
