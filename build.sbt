ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

Compile / run / fork := true

lazy val flywayVersion = "10.10.0"
lazy val zioConfigVersion = "4.0.1"

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
      "dev.zio" %% "zio-config" % zioConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
      "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio" %% "zio-http" % "3.0.0-RC3",
      "dev.zio" %% "zio-jdbc" % "0.1.2",
      "dev.zio" %% "zio-json" % "0.6.2",
      "dev.zio" %% "zio-prelude" % "1.0.0-RC23",
      "org.flywaydb" % "flyway-core" % flywayVersion,
      "org.flywaydb" % "flyway-database-postgresql" % flywayVersion,
      "org.postgresql" % "postgresql" % "42.7.3",
    ),
  )
