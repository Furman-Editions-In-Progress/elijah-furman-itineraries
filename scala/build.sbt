
name := "elijah-furman"

// must be at least 2.11 to use hmt_textmodel
scalaVersion := "2.12.8"

run / connectInput := true

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith","maven")
resolvers += Resolver.bintrayRepo("eumaeus", "maven")
resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases")

Compile / run / fork := true


connectInput in run := true

javaOptions in run ++= Seq(
    "-Xms256M",
    "-Xmn16M",
    "-Xmx2G"
)

libraryDependencies ++=   Seq(
  "edu.holycross.shot.cite" %% "xcite" % "4.3.0",
  "edu.holycross.shot" %% "ohco2" % "10.20.3",
  "edu.holycross.shot" %% "scm" % "7.4.0",
  "edu.holycross.shot" %% "dse" % "7.1.3",
  "edu.holycross.shot" %% "citebinaryimage" % "3.2.0",
  "edu.holycross.shot" %% "citeobj" % "7.5.1",
  "edu.holycross.shot" %% "citerelations" % "2.7.0",
  "edu.holycross.shot" %% "cex" % "6.5.0",
  "edu.holycross.shot" %% "greek" % "9.0.0",
  "edu.furman.classics" %% "citewriter" % "1.2.2",
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "com.github.tototoshi" %% "scala-csv" % "1.3.6"
)

