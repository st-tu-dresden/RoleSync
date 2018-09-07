package de.tud.swt.rms.models.modelA

import de.tud.swt.rms.PlayerSync

/**
  * General person from model A.
  */
abstract class Person(cname: String) extends PlayerSync {

  var fullName: String = cname

  buildClass()

  def setFullName(n: String): Unit = {
    fullName = n
    +this changeFullName()
    //println("----------Per Change---------------");
  }

  def getFullName(): String = {
    return fullName
  }

  def listen(): Unit = {
    println("++++++++++Person+++++++++++++++");
    +this listNames()
    println("----------Person---------------");
  }

  override def toString(): String = {
    return "Person: " + fullName + " D: " + deleted;
  }

}