package pl.setblack.pongi.web.pong

/**
  * Created by jarek on 1/18/17.
  */
case class GameState(ball: Ball, players : Tuple2[Player, Player], phase: String  ) {


}


sealed trait GamePhase

case object INITIAL extends GamePhase

case object STARTED extends GamePhase

case object ENDED extends  GamePhase




