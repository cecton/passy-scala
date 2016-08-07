import java.security.MessageDigest
import java.util.Base64

object Passy {
  val standardIn = System.console()
  val symbols = Array('!', '?', '+', '-', '=', '*', '/', '@', '#', '$', '%',
    '&', '(', ')', '[', ']', ';', ':', ',', '.', '<', '>')

  def sha1sum(bytes: Array[Byte]): Array[Byte] = {
    MessageDigest.getInstance("SHA").digest(bytes)
  }

  def passwordify(string: String): String = {
    val upperCase = ('A'.toInt + string(0).toInt % 26).toChar
    val lowerCase = ('a'.toInt + string(1).toInt % 26).toChar
    val numeric = ('0'.toInt + string(2).toInt % 10).toChar
    val symbol = symbols(string(3).toInt % symbols.length)
    Seq(upperCase, lowerCase, numeric, symbol).mkString + string.drop(4)
  }

  def genpass(password: String, site: String): String = {
    val digest = sha1sum(s"_${password}_${site}_".getBytes)
    val encodedPassword = Base64.getEncoder.encodeToString(digest)
    passwordify(encodedPassword).take(26)
  }

  def hint(password: String): String = genpass(password, "foo").take(6)

  def main(args: Array[String]) = {

    print("Password: ")
    val masterPassword = standardIn.readPassword.mkString

    println(s"Hint: ${hint(masterPassword)}")

    val password = args.headOption match {
      case Some(site: String) => genpass(masterPassword, site)
      case _ =>
        print("Site: ")
        val site = standardIn.readLine
        genpass(masterPassword, site)
    }

    println(password)
  }
}
