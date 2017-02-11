package pl.setblack.pongi.web.pong

import org.scalajs.dom.raw.KeyboardEvent
import org.scalajs.dom.document
import pl.setblack.pongi.web.Pong

import scala.scalajs.js

/**
  * Created by jarek on 1/20/17.
  */
class GameLoop {
  var stopped = false
  val keysMap = Map[Int, (Int, KeyState.State)](
    81->Tuple2(1, KeyState.Up),
    65->Tuple2(1, KeyState.Down),
    80->Tuple2(2, KeyState.Up),
    76->Tuple2(2, KeyState.Down)
  )

  var playerKeys = Map[(Int, KeyState.State), Boolean]()







  def init: Unit = {
    println("inited...")

    document.onkeydown =  keyDown

    document.onkeyup = keyUp

    js.timers.setInterval(50) {
      scanKeys
      //if  (!stopped) Pong.getMainBackend.foreach( pb => pb.refresh())
    }

    js.timers.setInterval(5000) {

      Pong.getMainBackend.foreach(_.refreshGameList())
    }

  }

  def getCommand(playerActiveKeys: Set[KeyState.State]):KeyState.State = {
    if (playerActiveKeys.size == 1) {
      playerActiveKeys.toSeq(0)
    } else {
      KeyState.Neutral
    }
  }



  private def scanKeys = {
    val active = playerKeys.filter( tp => tp._2)
    val player1 = active.filter( tp => tp._1._1 == 1)
      .keySet.map(tp => tp._2)

    val player2 = active.filter( tp => tp._1._1 == 2)
      .keySet.map(tp => tp._2)

    val player1Key = getCommand( player1)
    val player2Key = getCommand( player2)
    if ( player1Key != KeyState.Neutral) {
      println(s"player1 : ${player1Key}")
      Pong.getMainBackend.foreach(pb => pb.movePaddle(player1Key) )
    }
    if ( player2Key != KeyState.Neutral)
    println(s"player2 : ${player2Key}")


  }

  private def keyDown(ev: KeyboardEvent) = {

    val playerKey  = keysMap.get(ev.keyCode)

    playerKey.foreach( playerKeyTuple=>playerKeys   = playerKeys +  (playerKeyTuple -> true)
    )
    if (ev.keyCode == 83) {
        this.stopped = !this.stopped
    }
  }

  private def keyUp(ev: KeyboardEvent) = {
    val playerKey  = keysMap.get(ev.keyCode)
    playerKey.foreach( playerKeyTuple=>playerKeys   = playerKeys +  (playerKeyTuple -> false)
    )
  }

}
object KeyState extends Enumeration {
  type State = Value
  val Up, Down, Neutral = Value
}