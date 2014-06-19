package controllers

import play.api._
import play.api.mvc._
import de.tuberlin.uebb.sl2.impl.MacroDriver._
import org.json4s._
import org.json4s.JsonDSL.{ list2jvalue => _, _ }
import org.json4s.native.JsonMethods.{ render => jrender, parse => jparse, _ }
import play.api.data._
import play.api.data.Forms._
import de.tuberlin.uebb.sl2.slmacro.sl_function

object Application extends Controller {

  def index = Action {
    Ok( views.html.index( "Your new application is ready." ) )
  }

  def koch = Action {
    Ok( views.html.koch( true ) )
  }

  def foo = Action {
    Ok( views.html.foo() )
  }

  def slCalls = Action { request =>

    val obj_name = request.body.asFormUrlEncoded.get( "object_name" ).head
    val fun_name = request.body.asFormUrlEncoded.get( "function_name" ).head
    val params = request.body.asFormUrlEncoded.get( "params" ).head
    
    try {
      val fun_result = invokeObjectMethodByName( obj_name, fun_name, jparse( params ).children: _* )
      Ok( compact( jrender( JObject( JField( "result", fun_result ) ) ) ) ).as( "application/json" )
    }
    catch {
      case _ : Throwable => BadRequest( "not found" )
    }

  }

  /**
   * Invokes a method of an object by its name
   *
   * this should help the controller to call the right function
   *
   * this method can only call methods of type String* -> String
   * (because this is the only use case i have)
   *
   * @see https://stackoverflow.com/questions/1913092/getting-object-instance-by-string-name-in-scala
   * @see https://stackoverflow.com/questions/2060395/is-there-any-scala-feature-that-allows-you-to-call-a-method-whose-name-is-stored
   *
   * @example invokeObjectMethodByName("main.bar.Woot", "fac", "10")
   */
  def invokeObjectMethodByName( object_name_with_path: String, function_name: String, params: JValue* ): JValue =
    {
      val c = Class.forName( object_name_with_path + "$" )
      val clazz = c.getField( "MODULE$" ).get( c )
      val param_classes = params.map( ( _ ) => classOf[JValue] /*_.getClass()*/ )

      clazz.getClass.getMethod( function_name, param_classes: _* ).invoke( clazz, params: _* ).asInstanceOf[JValue]
    }

  //  def expr = Action {
  //    Ok( views.html.expr() )
  //  }
  //
  //  def makeObj( clazz: Int, vals: JValue* ) =
  //    {
  //      val fields: List[( String, JValue )] = ( "_cid" -> JInt( clazz ) ) :: ( vals.zipWithIndex.map( { case ( v, idx ) => ( ( "_var" + idx ) -> v ) } ).toList )
  //      JObject( fields )
  //    }
}
