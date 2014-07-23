package callable_functions

import de.tuberlin.uebb.sl2.slmacro.sl_function
import java.util.Calendar
import java.util.Random
import java.text.SimpleDateFormat

object Examples {
  @sl_function def fac( i: String ): Option[String] = {
    if ( i.toInt < 0 )
      None
    else
      Some( factorial( i.toInt ).toString )
  }

  @sl_function def factorial( i: Int ): Int =
    {
      if ( 1 >= i )
        1
      else
        i * factorial( i - 1 )
    }

  @sl_function def addfoo( k: Int, l: Int ): Int =
    {
      k + l + 3
    }

  @sl_function def blub( k: Int = 3 ): Int = 2 + k

  @sl_function def getserverinfo(): String = {
    val today = Calendar.getInstance().getTime()
    val simpleFormat = new SimpleDateFormat( "hh:mm:ss" )
    "Server Time: " + simpleFormat.format( today ) + " Scala " + util.Properties.versionString
  }

  /**
   * crappy ai for tic tac toe
   */
  @sl_function def tictactoesolver( input: Seq[Int] ): Either[String, Pair[String, Int]] = {
    val unset = 0
    val user_set = 1
    val comp_set = 2
    var to_set = -1

    if ( input.length != 9 || input.filter( ( x: Int ) => !( x <= 2 && x >= 0 ) ).length > 0 )
      return Left( """<span style="color:#f00">Error: Corrupted Input</span>""" )

    if ( cheated( input ) )
      return Left( """<span style="color:#f00">You have cheated ... Are you serious?</span>""" )

    if ( checkRows( input, user_set ) || checkCols( input, user_set ) || checkDiag( input, user_set ) )
      return Left( """<span style="color:#090">You WON!!!</span>""" )

    if ( input.filter( ( x: Int ) => x == 0 ).length == 0 )
      return Left( """<span style="color:#fbb117">DRAW</span>""" )

    // disallow 2 in a row
    if ( unset == input( 0 ) &&
      ( ( input( 1 ) == user_set && input( 2 ) == user_set )
        || ( input( 3 ) == user_set && input( 6 ) == user_set )
        || ( input( 4 ) == user_set && input( 8 ) == user_set ) ) )
      to_set = 0

    if ( unset == input( 2 ) &&
      ( ( input( 1 ) == user_set && input( 0 ) == user_set )
        || ( input( 5 ) == user_set && input( 8 ) == user_set )
        || ( input( 4 ) == user_set && input( 6 ) == user_set ) ) )
      to_set = 2

    if ( unset == input( 6 ) &&
      ( ( input( 0 ) == user_set && input( 3 ) == user_set )
        || ( input( 7 ) == user_set && input( 8 ) == user_set )
        || ( input( 4 ) == user_set && input( 2 ) == user_set ) ) )
      to_set = 6

    if ( unset == input( 8 ) &&
      ( ( input( 6 ) == user_set && input( 7 ) == user_set )
        || ( input( 5 ) == user_set && input( 2 ) == user_set )
        || ( input( 4 ) == user_set && input( 0 ) == user_set ) ) )
      to_set = 8

    if ( unset == input( 4 ) &&
      ( ( input( 1 ) == user_set && input( 7 ) == user_set )
        || ( input( 5 ) == user_set && input( 3 ) == user_set )
        || ( input( 8 ) == user_set && input( 0 ) == user_set )
        || ( input( 6 ) == user_set && input( 2 ) == user_set )
      ) )
      to_set = 4

    if ( unset == input( 1 ) &&
      ( ( input( 0 ) == user_set && input( 2 ) == user_set )
        || ( input( 4 ) == user_set && input( 7 ) == user_set ) ) )
      to_set = 1

    if ( unset == input( 3 ) &&
      ( ( input( 0 ) == user_set && input( 6 ) == user_set )
        || ( input( 5 ) == user_set && input( 4 ) == user_set ) ) )
      to_set = 3

    if ( unset == input( 7 ) &&
      ( ( input( 1 ) == user_set && input( 4 ) == user_set )
        || ( input( 6 ) == user_set && input( 8 ) == user_set ) ) )
      to_set = 7

    if ( unset == input( 5 ) &&
      ( ( input( 2 ) == user_set && input( 8 ) == user_set )
        || ( input( 3 ) == user_set && input( 4 ) == user_set ) ) )
      to_set = 5

    // nothing urgend try to get the middle
    if ( unset == input( 4 ) )
      to_set = 4

    // choose one by rand
    if ( to_set == -1 ) {
      val to_choose = for ( i <- 0 to 8 if input( i ) == unset ) yield i

      val rand = new Random( System.currentTimeMillis() )
      to_set = to_choose( rand.nextInt( to_choose.length ) )
    }

    val new_board = for ( i <- 0 to 8 ) yield { if ( i == to_set ) comp_set else input( i ) }

    if ( checkRows( new_board, comp_set ) || checkCols( new_board, comp_set ) || checkDiag( new_board, comp_set ) )
      return Right( Pair( """<span style="color:#090">HAHA I have won1!!1</span>""", to_set ) )
    else
      return Right( Pair( "", to_set ) )

  }

  def checkRows( input: Seq[Int], n: Int ): Boolean = {
    ( input( 0 ) == n && input( 1 ) == n && input( 2 ) == n ) ||
      ( input( 3 ) == n && input( 4 ) == n && input( 5 ) == n ) ||
      ( input( 6 ) == n && input( 7 ) == n && input( 8 ) == n )
  }

  def checkCols( input: Seq[Int], n: Int ): Boolean = {
    ( input( 0 ) == n && input( 3 ) == n && input( 6 ) == n ) ||
      ( input( 1 ) == n && input( 4 ) == n && input( 7 ) == n ) ||
      ( input( 2 ) == n && input( 5 ) == n && input( 8 ) == n )
  }

  def checkDiag( input: Seq[Int], n: Int ): Boolean = {
    ( input( 0 ) == n && input( 4 ) == n && input( 8 ) == n ) ||
      ( input( 6 ) == n && input( 4 ) == n && input( 2 ) == n )
  }

  def cheated( input: Seq[Int] ): Boolean = {
    var own = 0;
    var player = 0;

    for ( i <- input ) {
      if ( i == 1 ) player = player + 1

      if ( i == 2 ) own = own + 1
    }

    player != own + 1
  }

}
 