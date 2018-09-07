package de.tud.swt.rms.roles

/**
  * Interface for the destructor roles.
  */
trait IDestructor {

  /**
    * General destruction function for external call.
    */
  def deleteRoleFunction(): Unit
}