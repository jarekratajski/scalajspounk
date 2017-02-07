enablePlugins(ScalaJSPlugin)

name := "pong"

scalaVersion := "2.12.0"

persistLauncher := true


libraryDependencies ++= Seq (
  "com.github.japgolly.scalajs-react" %%% "core" % "0.11.3",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
  "com.lihaoyi" %%% "upickle" % "0.4.4"
)

jsDependencies ++= Seq(

  "org.webjars" % "jquery" % "2.1.3" / "2.1.3/jquery.js",

  "org.webjars.bower" % "react" % "15.3.2"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.3.2"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",

  "org.webjars.bower" % "react" % "15.3.2"
    /         "react-dom-server.js"
    minified  "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer")