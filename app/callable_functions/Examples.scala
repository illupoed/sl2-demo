package callable_functions

import de.tuberlin.uebb.sl2.slmacro.sl_function
import java.util.Calendar
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
    val simpleFormat = new SimpleDateFormat("hh:mm:ss")
    "Server Time: " + simpleFormat.format(today)
  }

}
 