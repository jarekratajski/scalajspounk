package pl.setblack.pongi.web

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.ReactAttr.{Generic, ValueType}
import japgolly.scalajs.react.{ReactComponentB, ReactDOM}
import org.scalajs.dom.document
import pl.setblack.pongi.web.pong._

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.ScalaJSDefined
/**
  * Created by jarek on 1/16/17.
  */
object Main extends JSApp{



  def main(): Unit = {
    println("Hello world!")


    val gameLoop  = new GameLoop
    gameLoop.init

    val myState = new PongClientState

    ReactDOM.render(Pong.page(myState), document.getElementById("react"))
  }





}


