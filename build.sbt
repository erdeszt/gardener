ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

Compile / run / fork := true

lazy val root = (project in file("."))
  .settings(
    name := "zio-gardener",
    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:experimental.macros",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:noAutoTupling",
      "-Wvalue-discard",
      "-Wunused:all",
    ),
    wartremoverWarnings ++= Warts.allBut(
      Wart.Equals,
      Wart.FinalCaseClass,
      Wart.Any,
      Wart.Nothing,
      Wart.ImplicitParameter,
    ),
    name := "gardener",
    libraryDependencies ++= Seq(
      "com.github.f4b6a3" % "ulid-creator" % "5.2.3",
      "dev.zio" %% "zio-config" % "4.0.1",
      "dev.zio" %% "zio-http" % "3.0.0-RC3",
      "dev.zio" %% "zio-jdbc" % "0.1.2",
      "dev.zio" %% "zio-json" % "0.6.2",
      "dev.zio" %% "zio-prelude" % "1.0.0-RC23",
    ),
  )
