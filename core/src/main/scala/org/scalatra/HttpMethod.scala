package org.scalatra

import java.util.Locale

sealed trait HttpMethod {
  /**
   * Flag as to whether the method is "safe", as defined by RFC 2616.
   */
  def isSafe: Boolean
  /**
   * Flag as to whether the method allows a body, as defined by RFC 2616.
   */
  def allowsBody: Boolean


}
case object Options extends HttpMethod {
  val isSafe = true
  val allowsBody = false
  override def toString = "OPTIONS"
}
case object Get extends HttpMethod {
  val isSafe = true
  val allowsBody = false
  override def toString = "GET"
}
case object Head extends HttpMethod {
  val isSafe = true
  val allowsBody = false
  override def toString = "HEAD"
}
case object Post extends HttpMethod {
  val isSafe = false
  val allowsBody = true
  override def toString = "POST"
}
case object Put extends HttpMethod {
  val isSafe = false
  val allowsBody = true
  override def toString = "PUT"
}
case object Delete extends HttpMethod {
  val isSafe = false
  val allowsBody = false
  override def toString = "DELETE"
}
case object Trace extends HttpMethod {
  val isSafe = true
  val allowsBody = false
  override def toString = "TRACE"
}
case object Connect extends HttpMethod {
  val isSafe = false
  val allowsBody = false
  override def toString = "CONNECT"
}
case object Patch extends HttpMethod {
  val isSafe = false
  val allowsBody = true
  override def toString = "PATCH"
}
case class ExtensionMethod(name: String, isSafe: Boolean = false, allowsBody: Boolean = false) extends HttpMethod


object HttpMethod {
  private val methodMap =
    Map(List(Options, Get, Head, Post, Put, Delete, Trace, Connect, Patch) map {
      method => (method.toString, method)
    } : _*)

  /**
   * Maps a String as an HttpMethod.
   *
   * @param name a string representing an HttpMethod
   * @return the matching common HttpMethod, or an instance of `ExtensionMethod`
   * if no method matches
   */
  def apply(name: String): HttpMethod = {
    val canonicalName = name.toUpperCase(Locale.ENGLISH)
    methodMap.getOrElse(canonicalName, ExtensionMethod(canonicalName))
  }

  /**
   * The set of common HTTP methods: GET, HEAD, POST, PUT, DELETE, TRACE,
   * CONNECT, and PATCH.
   */
  val methods: Set[HttpMethod] = methodMap.values.toSet
}
