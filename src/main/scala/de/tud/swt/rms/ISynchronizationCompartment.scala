package de.tud.swt.rms

import scroll.internal.Compartment
import de.tud.swt.rms.roles.IRoleManager

/**
  * Interface for the synchronization management compartment.
  */
trait ISynchronizationCompartment extends Compartment {

  protected var underConstruction: Boolean = false

  /**
    * Is currently in a process where new elements could be created.
    */
  def isUnderConstruction(): Boolean = underConstruction

  /**
    * Get a new RoleManager instance.
    */
  def createRoleManager(): IRoleManager
}