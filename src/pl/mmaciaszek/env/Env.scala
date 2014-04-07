package pl.mmaciaszek.env

object Env {
  lazy val userDir = System.getProperty("user.dir")
}