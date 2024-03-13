val scala3Version = "3.3.3"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "scala-3-beginners",
    version      := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
  )
