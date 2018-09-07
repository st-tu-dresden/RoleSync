package de.tud.swt.rms.models.modelA

/**
  * Female from model A extends from Person.
  */
class Female(cname: String) extends Person(cname) {

  override def toString(): String = {
    return "Female: " + fullName + " D: " + deleted;
  }
}