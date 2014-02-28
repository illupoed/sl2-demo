import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "webui_sl"
  val appVersion      = "1.0-SNAPSHOT"
  val paradiseVersion = "2.0.0-M3"
  
  val json4sNative = "org.json4s" %% "json4s-native" % "3.2.4"

  val appDependencies = Seq(
    // Add your project dependencies here,
    json4sNative,
    jdbc,
    anorm
  )

  lazy val sl2 = Project(
    id = "sl2",
    base = file("sl2"), 
    settings = Project.defaultSettings ++ Seq(
      resolvers += Resolver.sonatypeRepo("snapshots"),
	  resolvers += Resolver.sonatypeRepo("releases"),
      addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
    ) ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
      libraryDependencies ++= (
        if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" % "quasiquotes" % paradiseVersion cross CrossVersion.full)
        else Nil
      )
    )
  )

  val main = play.Project(
    appName,
    appVersion,
    appDependencies
  ).settings(
    unmanagedClasspath in Compile <+= (baseDirectory) map { bd => bd / "sl-files" },
	resolvers += Resolver.sonatypeRepo("snapshots"),
	resolvers += Resolver.sonatypeRepo("releases"),
	addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
  ).dependsOn(sl2)

}
