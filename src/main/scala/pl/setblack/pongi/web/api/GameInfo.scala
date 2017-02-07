package pl.setblack.pongi.web.api

/**
  * Created by jarek on 1/22/17.
  */
case class GameInfo(
                     name: String,
                     uuid: String,
                     players: Seq[String]
                   )


