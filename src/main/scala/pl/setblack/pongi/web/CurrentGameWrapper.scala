package pl.setblack.pongi.web

import pl.setblack.pongi.web.pong.GameState

/**
  * Created by jarek on 1/24/17.
  */
case class CurrentGameWrapper(uuid: String, state : GameState)
