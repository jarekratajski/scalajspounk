package pl.setblack.pongi.web

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactEventI}
import japgolly.scalajs.react.vdom.prefix_<^._
import pl.setblack.pongi.web.api.GameInfo
import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLInputElement
import pl.setblack.pongi.web.pong.Score

/**
  * Created by jarek on 1/22/17.
  */
object GamesList {

  class GamesListBackend($: BackendScope[(Seq[GameInfo],Seq[Score]),Unit] ) {

    def joinGame(uuid: String)(e: ReactEventI) = {
        Callback {
          Pong.getMainBackend.foreach(mb => mb.joinGame(uuid))
        }
    }

    def createGame(e: ReactEventI) = {
      Callback {
        val gameName = document.getElementsByName("createdGame")(0).asInstanceOf[HTMLInputElement].value
        println("creatin ..."+ gameName)
        Pong.getMainBackend.foreach(mb => mb.createGame(gameName))
      }
    }

    def render(state: (Seq[GameInfo],Seq[Score])) = {
      <.section(
        ^.`class` := "games",
        <.ul(state._1.map( game => <.li("join => ", game.name, ^.onClick ==> joinGame(game.uuid) ) ) ),
        <.ul(state._2.map( score => <.li( score.userId," " ,score.totalScore ) ) ),
        <.p(<.input(^.name:="createdGame"), <.button("create", ^.onClick==>createGame))
      )
    }
  }


  val page = ReactComponentB[(Seq[GameInfo],Seq[Score])]("Games")
      .backend(new GamesListBackend(_))
    .renderBackend
    .build



}
