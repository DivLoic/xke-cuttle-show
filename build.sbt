name := "xke-cuttle-show"

description :=
  """
    |
  """.stripMargin

organizationHomepage := Some(url("http://blog.xebia.fr"))

val cuttleVersion = "0.3.0"
val sparkVersion = "2.2.0"
val scalaTestVersion = "3.0.4"
val spotifyVersion = "0.2.2"
val gcpVersion = "v2-rev369-1.23.0"

enablePlugins(RpmPlugin)
enablePlugins(RpmDeployPlugin)
enablePlugins(JavaAppPackaging)
rpmVendor := "typesafe"

coverageEnabled := true

lazy val common = Seq(

  version := "0.1.0",

  isSnapshot := false,

  scalaVersion := "2.11.11",

  organization := "fr.xebia",

  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.3.1",
    "joda-time" % "joda-time" % "2.9.7",
    "ch.qos.logback" % "logback-classic" % "1.2.0" force()
  ),

  // logLevel in doc := Level.Error
)

lazy val `xke-cuttle-show` = (project in file("."))
  .aggregate(`cuttle-module`, `data-ingestion`, `dataset-series`)

lazy val `cuttle-module` = project
  .settings(common: _*)
  .settings(gcpDependencies: _*)
  .settings(cuttleDependencies: _*)

lazy val `data-ingestion` = project
  .settings(common: _*)
  .settings(sparkDependencies: _*)

lazy val `dataset-series` = project
  .settings(common: _*)
  .settings(sparkDependencies: _*)

lazy val cuttleDependencies = Seq(

  libraryDependencies ++= Seq(
    "com.criteo.cuttle" %% "cuttle" % cuttleVersion,
    "com.criteo.cuttle" %% "timeseries" % cuttleVersion
  )
)

lazy val sparkDependencies = Seq(

  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,
  )
)

lazy val gcpDependencies = Seq(

  libraryDependencies ++= Seq(
    "com.google.apis" % "google-api-services-bigquery" % gcpVersion,
    "com.spotify" %% "spark-bigquery" % "0.2.2"
  )

)