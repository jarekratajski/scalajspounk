package pl.setblack.pongi.web

import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.prefix_<^._
import pl.setblack.pongi.web.api.{GameInfo, ServerApi}
import pl.setblack.pongi.web.pong.{GameState, KeyState, Player}
import pl.setblack.pongi.web.pong.KeyState.State
/**
  * Created by jarek on 1/22/17.
  */
class Pong {

}

object Pong {

  import scala.concurrent.ExecutionContext.Implicits.global

  private var mainBackend: Option[PongBackend] = None

  def getMainBackend = mainBackend

  class PongBackend($: BackendScope[PongClientState, PongClientState]) {

    mainBackend = Some(this)

    println("backend created")


    private val api = new ServerApi

    def server = api

    def render(state: PongClientState): ReactElement = {
      val elem: Option[ReactElement] = state.welcome.map(ws => {
        Welcome.page()
      })
      elem
        .orElse(state.currentGame.map(game => PlayField.GameStateComponent(game.state)))
        .orElse(state.games.map(list => GamesList.page(list)))
        .getOrElse(<.p("empty one"))
    }

    def toGameList() = {
      server.listGames.onComplete(games => {
        $.modState(ps => ps.toGamesList(games.get)).runNow()
      })
    }

    def movePlayer(gameId: String, player1Key: State, aplayer: Player) = {
        this.api.session
          .filter( sess => sess.userId==aplayer.name)
         .foreach(_ => {
           val currentPaddle = aplayer.paddle.y
           val newTarget = player1Key  match {
             case KeyState.Up => currentPaddle - 0.01;
             case KeyState.Down => currentPaddle + 0.01;
             case _ => currentPaddle
           }
           val sanitized = Math.min(Math.max(0f, newTarget),1.0f)
           this.api.movePaddle(gameId, sanitized)
         })
    }

    def movePaddle(player1Key: KeyState.State) = {
        $.state.map(
          ps => ps.currentGame
            .foreach(
              game => {
                movePlayer(game.uuid, player1Key, game.state.players._1)
                movePlayer(game.uuid, player1Key, game.state.players._2)
              })).runNow()

    }



    def refresh() = {
      $.state.map(
        ps => {
          ps.currentGame
            .map(
              game => {
                api.getGame(game.uuid)
                  .onComplete(tried => {
                    $.setState( ps.withState(game.uuid, tried.get)).runNow()
                    //tried.get.map(newState => $.setState(ps.withState(game.uuid, newState)).runNow())
                  })
              }
            )
        }).runNow()
    }

    def toGame(uuid: String, state: GameState) = {
      $.modState(ps => ps.toGame(uuid, state)).runNow()
    }

    def joinGame(uuid: String) = {
      api.joinGame(uuid).onComplete(_.foreach( state => toGame(uuid, state.get)))
    }

    def createGame(name: String) = {
      api.createGame(name).onComplete(_ => toGameList())
    }

  }

  val page =
    ReactComponentB[PongClientState]("PongGame")
      .initialState(new PongClientState())
      .backend(new PongBackend(_))
      .renderBackend
      .build

}


