package pl.setblack.pongi.web.pong

/**
  * Created by jarek on 2/11/17.
  */
case class Score(
                  userId : String,
                  totalScore : Int,
                  gamesWon : Int,
                  gamesLost : Int,
                  gamesPlayed: Int,
                  pointsScored : Int,
                  pointsLost: Int
                  )
