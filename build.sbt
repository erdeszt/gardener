lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "gardener",
    organization := "com.example",
    version := "0.1-SNAPSHOT",
    scalaVersion := "3.3.1",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
    ),
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
    )
  )
