name := "proof-generator"
version := "1.0"
scalaVersion := "2.11.12"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    publishArtifact in(Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    sources in(Compile, doc) := Seq.empty
  )

libraryDependencies ++= Seq(
  javaWs,
  guice,
  openId,

  "org.projectlombok"               % "lombok"                          % "1.18.20"       % Provided,

  "com.itextpdf"                    % "itextpdf"                        % "5.5.13",
  "com.itextpdf.tool"               % "xmlworker"                       % "5.5.13"
)
