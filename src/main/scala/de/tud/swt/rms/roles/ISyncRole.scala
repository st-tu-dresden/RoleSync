package de.tud.swt.rms.roles

import de.tud.swt.rms.ISyncCompartment

/**
  * Interface for the synchronization roles.
  */
trait ISyncRole {

  /**
    * Function to get the synchronization compartment from a role instance.
    */
  def getOuterCompartment(): ISyncCompartment
}