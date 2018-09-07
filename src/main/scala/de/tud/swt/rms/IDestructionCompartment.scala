package de.tud.swt.rms

import scroll.internal.Compartment
import de.tud.swt.rms.roles.IDestructor

/**
  * Interface for each destruction rule.
  */
trait IDestructionCompartment extends Compartment {

  /**
    * Return a role instance that handles the destruction process for the object.
    */
  def getDestructorForClassName(classname: Object): IDestructor
}