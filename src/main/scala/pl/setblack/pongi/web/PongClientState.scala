package pl.setblack.pongi.web

import pl.setblack.pongi.web.api.GameInfo
import pl.setblack.pongi.web.pong.GameState

/**
  * Created by jarek on 1/22/17.
  */
case class PongClientState(
                          welcome  : Option[Welcome.WelcomeState],
                          games : Option[Seq[GameInfo]],
                          currentGame: Option[CurrentGameWrapper]
                          ) {



  def this()  = this(Some(Welcome.WelcomeState()), None,None)


  def toGamesList(games: Seq[GameInfo]): PongClientState = {
    PongClientState(None, Some(games) , None)
  }

  def toGame( uuid: String, gameState : GameState): PongClientState = {
    PongClientState(None, this.games, Some(CurrentGameWrapper(uuid, gameState)))
  }

  def withState(uuid: String, gameState: GameState):PongClientState = toGame(uuid, gameState )

  def withState(uuid: String, gameState: Option[GameState]):PongClientState = {
    gameState.map( this.withState(uuid, _ )).getOrElse(this.backToGamesList())
  }


  private def backToGamesList() = {
    println(s"games list state ${this.games}")
    PongClientState(None, this.games, None)
  }
}


