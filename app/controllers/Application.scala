package controllers

import play.api._
import play.api.mvc._
import de.tuberlin.uebb.sl2.impl.MacroDriver._
import org.json4s._
import org.json4s.JsonDSL.{ list2jvalue => _, _ }
import org.json4s.native.JsonMethods.{ render => jrender, parse => jparse, _ }
import play.api.data._
import play.api.data.Forms._
import de.tuberlin.uebb.sl2.slmacro.InvokeHelper

object Application extends Controller with InvokeHelper {

  def index = Action {
    Ok( views.html.index( "Your new application is ready." ) )
  }

  def koch(n: Int = 3) = Action {
    Ok( views.html.koch( n ) )
  }

  def foo = Action {
    Ok( views.html.foo() )
  }

  def checkOption = Action {
    Ok( views.html.checkOption() )
  }
  
  def checkSeq = Action {
    Ok( views.html.checkSeq() )
  }
  
  def checkEither = Action {
    Ok( views.html.checkEither() )
  }
  
  def checkTuple2 = Action {
    Ok( views.html.checkTuple2() )
  }
  
  def checkPrim = Action {
    Ok( views.html.checkPrim() )
  }
  
  def checkFunCall = Action {
    Ok( views.html.checkFunCall() )
  }
  
  def tictactoe = Action {
    Ok( views.html.tictactoe() )
  }
  
  def test = Action {
    Ok( views.html.test( true ) )
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
      case _: Throwable => BadRequest( "not found" )
    }

  }
}
