package de.tud.swt.rms.roles

import de.tud.swt.rms.PlayerSync
import scala.collection.mutable.ListBuffer
import de.tud.swt.rms.ConstructionContainer

/**
  * Interface for the constructor roles.
  */
trait IConstructor {

  /**
    * Container list for the construction process.
    */
  protected var containers = ListBuffer[ConstructionContainer]()

  /**
    * Create a container element with the incoming configuration.
    */
  protected def createContainerElement(start: Boolean, con: Boolean, play: PlayerSync, man: IRoleManager): Unit = {
    if (play == null || man == null)
      return
    var cc = new ConstructionContainer(start, con, play, man)
    containers += cc
  }

  /**
    * General construction function for external call.
    */
  def construct(comp: PlayerSync, man: IRoleManager): Unit
}