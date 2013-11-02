package controllers

import play.api._
import play.api.mvc._

import de.tuberlin.uebb.sl2.impl.MacroDriver._

import org.json4s._
import org.json4s.JsonDSL.{list2jvalue=>_,_}
import org.json4s.native.JsonMethods.{render=>jrender,_}

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def koch = Action {
    Ok(views.html.koch())
  }

  def kochFile = Action {
    Ok(views.html.kochfile())
  }

  def expr = Action {
    Ok(views.html.expr())
  }

  def makeObj(clazz : Int, vals : JValue*) = {
    val fields : List[(String, JValue)]= ("_cid" -> JInt(clazz)) :: (vals.zipWithIndex.map( {case (v, idx) => (("_var" + idx) -> v)}).toList )
    JObject(fields)
  }

  val noneSl = JInt(0)

  def someSl(x : JValue) = makeObj(1, x)

  implicit def list2SlList[T](x : List[T])(implicit f : T => JValue) : JValue = x match {
    case hd::tl => makeObj(0, f(hd), list2SlList(tl))
    case Nil => 1
  }
  
  implicit def pos2Sl(p : Position) : JValue = p match {
    case Position(line, col) => makeObj(0, line, col)
  }

  implicit def loc2Sl(l : Location) : JValue = l match {
    case FileLocation(f, from, to) => makeObj(0, from, to)
    case NoLocation => makeObj(1)
  }

  implicit def attr2Sl(a : Attribute) : JValue = a match {
    case AttributeImpl(loc) => makeObj(0, loc)
    case EmptyAttribute => makeObj(1)
  }

  implicit def ty2Sl(x : ASTType) : JValue = x match {
    case FunTy(params, attr) => makeObj(0, params, attr)
    //case TyExpr(cons, args, attr) => makeObj(1, JString(cons), args, attr)
    case TyVar(name, attr) => makeObj(2, name, attr)
  }

  implicit def pat2Sl(p : Pattern) : JValue = p match {
    //case PatternExpr(c, ps, a) => makeObj(0, c, ps, a)
    case PatternVar(v, a) => makeObj(1, v, a)
  }

  implicit def alt2Sl(a : Alternative) : JValue = a match {
    case Alternative(p, e, attr) => makeObj(0, p, e, attr)
  }

  implicit def ld2Sl(d : LetDef) : JValue = d match {
    case LetDef(l, r, attr) => makeObj(0, l, r, attr)
  }

  implicit def expr2Sl(x : Expr) : JValue = x match {
    case App(l, r, attr)             => makeObj(0, l, r, attr)    
    case Case(e, as, attr)           => makeObj(1, e, as, attr)

    case Conditional(i, t, e, attr)  => makeObj(2, i, t, e, attr)

    case ConstChar(x, attr)          => makeObj(3, x, attr)
    case ConstInt(x, attr)           => makeObj(4, x, attr)
    case ConstReal(x, attr)          => makeObj(5, x, attr)
    case ConstString(x, attr)        => makeObj(6, x, attr)

    //case ExCon(x, attr)              => makeObj(7, x, attr)
    //case ExVar(x, attr)              => makeObj(8, x, attr)

    case JavaScript(c, t, attr)      => makeObj(9, c, t, attr)

    case Lambda(p, e, attr)          => makeObj(10, p, e, attr)
    case Let(ds, b, attr)            => makeObj(11, ds, b, attr)   
  }

  def expr2Json(in : String) = Action {
    println("Requested for parsing: '%s'".format(in))
    val e = parseExpr(in) 
    println("Got: " + e)
    e match {
      case Right(expr) => {
        val json = someSl(expr2Sl(expr))
        println("Reply: " + compact(jrender(json)))
        Ok(compact(jrender(json)))
      }
      case _ => Ok(compact(jrender(noneSl)))
    }
  }

  def parseType(in : String) = Action {
    println("Requested for parsing: '%s'".format(in))
    val t = parseExpr("{| |} : " + in) 
    println("Got: " + t)
    t match {
      case Right(JavaScript(_, Some(t), _)) => {
        val js = someSl(ty2Sl(t))
        println("Reply: " + compact(jrender(js)))
        Ok(compact(jrender(js)))
      }
      case _ => Ok(compact(jrender(noneSl)))
    }
  }
}
