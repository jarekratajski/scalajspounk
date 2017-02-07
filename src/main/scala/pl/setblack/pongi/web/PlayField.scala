package pl.setblack.pongi.web

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.ReactAttr.{Generic, ValueType}
import japgolly.scalajs.react.{ReactComponentB, ReactDOM}
import org.scalajs.dom.document
import pl.setblack.pongi.web.pong._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * Created by jarek on 1/24/17.
  */
object PlayField {

  implicit val asJSObj = ValueType.map[StylePositioned](a=>a)

  private val PlayerComponent = ReactComponentB[Player]("Player")
    .render_P(ply => <.figure(
      ^.`class` := "player",
      ^.style :=  new StylePositioned(ply.paddle),
      Generic("x") := ply.paddle.x.toString,
      Generic("y") := ply.paddle.y.toString
    ))
    .build

  private val BallComponent = ReactComponentB[Ball]("Ball")
    .render_P(ball=> <.figure(
      ^.`class` := "ball",
      ^.style :=  new StylePositioned(ball)
    ))
    .build

  private val ScoreComponent = ReactComponentB[PlayerScore]("Score")
      .render_P( score => {
            <.figure(
              ^.`class` := s"score${score.player}",
              score.score)
      }).build

  private val PlayFieldComponent =
    ReactComponentB[GameState]("Playfield")
      .render_P(game => <.section(
        ^.`class` := "playfield",
        PlayerComponent(game.players._1),
        PlayerComponent(game.players._2),
        BallComponent(game.ball),
        ScoreComponent(PlayerScore(game.players._1.score, 1)),
        ScoreComponent(PlayerScore(game.players._2.score, 2)))
      ).build

  val GameStateComponent = ReactComponentB[GameState]("GameState")
      .render_P( game => PlayFieldComponent(game))
      .build

  @ScalaJSDefined
  class StylePositioned(o : GameObject)  extends js.Object {

    val transform : String =  "translateX("+toPercent(o.x)+") translateY("+toPercent(o.y)+")"
  }

  private def toPercent(v :Float) : String = {
    val inPercent = v*100f
    f"$inPercent%111.1f%%"
  }
}

private case class  PlayerScore(score : Int, player: Int)
