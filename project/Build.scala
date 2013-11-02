import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "webui_sl"
  val appVersion      = "1.0-SNAPSHOT"

  val json4sNative = "org.json4s" %% "json4s-native" % "3.2.4"

  val appDependencies = Seq(
    // Add your project dependencies here,
    json4sNative,
    jdbc,
    anorm
  )

  lazy val sl2 = Project(id = "sl2", base = file("sl2"))


  val main = play.Project(appName, appVersion, appDependencies).settings(
    unmanagedClasspath in Compile <+= (baseDirectory) map { bd => bd / "sl-files" }
 ).dependsOn(sl2)

}
