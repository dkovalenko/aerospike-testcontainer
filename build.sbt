scalaVersion := "2.13.6"

name := "testcontainer-aerospike"
organization := "com.dkovalenko"
version := "1.0"

libraryDependencies ++= Seq(
  "com.aerospike"           % "aerospike-client"                % "5.0.5",
  "org.scalatest"           %% "scalatest"                      % "3.2.9"   % Test,
  "org.scalatest"           %% "scalatest-flatspec"              % "3.2.9"   % Test,
  "com.dimafeng"            %% "testcontainers-scala-scalatest" % "0.39.5"  % Test,
)

Global / onChangedBuildSource := ReloadOnSourceChanges