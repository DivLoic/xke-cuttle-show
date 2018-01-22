name := "xke-cuttle-show"

description :=
  """
    |
  """.stripMargin

maintainer :=
  """
    |Loïc DIVAD <ldivad@xebia.fr>"
    |Alban PHELIP <aphelip@xebia.fr>
  """.stripMargin

packageSummary := "package exemple"

packageDescription := "description example"

organizationHomepage := Some(url("http://blog.xebia.fr"))

val cuttleVersion = "0.3.0"
val sparkVersion = "2.2.0"
val scalaTestVersion = "3.0.4"
val spotifyVersion = "0.2.2"
val gcpVersion = "v2-rev369-1.23.0"

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
  )

  // logLevel in doc := Level.Error
)

lazy val `xke-cuttle-show` = (project in file("."))
  .settings(common: _*)
  .aggregate(`data-ingestion`, `dataset-series`)

lazy val `data-ingestion` = project
  .settings(common: _*)
  .settings(sparkDependencies: _*)
  .settings(packagingSettings: _*)
  .enablePlugins(JavaAppPackaging, UniversalPlugin)

lazy val `dataset-series` = project
  .settings(common: _*)
  .settings(sparkDependencies: _*)
  .settings(packagingSettings: _*)
  .enablePlugins(JavaAppPackaging, UniversalPlugin)

lazy val `dataset-ranking` = project
  .settings(common: _*)
  .settings(sparkDependencies: _*)
  .settings(packagingSettings: _*)
  .enablePlugins(JavaAppPackaging, UniversalPlugin)

lazy val cuttleDependencies = Seq(

  libraryDependencies ++= Seq(
    "com.criteo.cuttle" %% "cuttle" % cuttleVersion,
    "com.criteo.cuttle" %% "timeseries" % cuttleVersion
  )
)

lazy val sparkDependencies = Seq(

  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
  )
)

lazy val gcpDependencies = Seq(

  libraryDependencies ++= Seq(
    "com.google.apis" % "google-api-services-bigquery" % gcpVersion,
    "com.spotify" %% "spark-bigquery" % "0.2.2"
  )

)

lazy val packagingSettings = Seq(
  assemblyJarName in assembly := s"${name.value}-assembly-${version.value}.jar",
  mappings in Universal --= (mappings in Universal map { jars => jars.filter(jar => jar._2.endsWith(".jar"))}).value,
  mappings in Universal += (assembly in Compile map { jar => jar -> ("lib/" + jar.getName)}).value

)
