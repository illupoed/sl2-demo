import AssemblyKeys._

assemblySettings

name := "sl2-demo"

organization := "de.tuberlin.uebb"

version := "0.1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation", "-feature")

// Skip tests during assembly
test in assembly := {} 

EclipseKeys.withSource := true

