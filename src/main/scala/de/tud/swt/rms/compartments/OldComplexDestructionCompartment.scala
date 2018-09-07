package de.tud.swt.rms.compartments

import scroll.internal.Compartment
import de.tud.swt.rms.PlayerSync
import scala.collection.mutable.ListBuffer
import de.tud.swt.rms.IDestructionCompartment
import de.tud.swt.rms.roles.IDestructor
import de.tud.swt.rms.models.modelC.SimplePerson
import de.tud.swt.rms.models.modelA.Person
import de.tud.swt.rms.models.modelB.Member
import de.tud.swt.rms.models.modelB.Family
import de.tud.swt.rms.roles.IRoleManager

/**
  * Does the same as the GeneralDestructor
  * Calls the destruction method from all related RoleManagers and then deletes all roles from this player.
  */
object OldComplexDestructionCompartment extends IDestructionCompartment {

  def getDestructorForClassName(classname: Object): IDestructor = {
    if (classname.isInstanceOf[Family])
      return null
    else if (classname.isInstanceOf[Member])
      return new MemberDelete()
    else if (classname.isInstanceOf[Person])
      return new PersonDelete()
    else if (classname.isInstanceOf[SimplePerson])
      return new RegisterDelete()
    return null
  }

  class PersonDelete() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //println("##Delete element Player: " + this.player);
      var relatedManager = (+this).getRelatedManager()
      (+this).clearListsOfRelatedManager()
      //call delete method in all related role managers
      if (relatedManager.isRight) {
        //println("In IF STATEMENT" + relatedManager.right.get);
        var list: ListBuffer[IRoleManager] = relatedManager.right.get
        list.foreach { m =>
          (+m).deleteObjectFromSynchro()
        }
      }
      //clear now the related manager list
      (+this).clearRelatedManager()
      //delete all roles this element has      
      var player = this.player;
      if (player.isRight) {
        var test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        var roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
      /*var manager = (+this).getManager()
      if (manager.isRight)
      {
        var test: IRoleManager = manager.right.get
        println("--Roles Player: " + plays.getRoles(test))
      }
      println("Roles Comp: " + plays.getRoles(comp))
      //syncer.foreach { a => (+a) deleted = true }
      //syncer = List[PlayerSync]()*/
    }
  }

  class RegisterDelete() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //println("##Delete element Player: " + this.player);
      var relatedManager = (+this).getRelatedManager()
      (+this).clearListsOfRelatedManager()
      //call delete method in all related role managers
      if (relatedManager.isRight) {
        //println("In IF STATEMENT" + relatedManager.right.get);
        var list: ListBuffer[IRoleManager] = relatedManager.right.get
        list.foreach { m =>
          (+m).deleteObjectFromSynchro()
        }
      }
      //clear now the related manager list
      (+this).clearRelatedManager()
      //delete all roles this element has      
      var player = this.player;
      if (player.isRight) {
        //println("In IF STATEMENT" + player.right.get);
        var test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        var roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
    }
  }

  class MemberDelete() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //println("##Delete element Player: " + this.player);
      var relatedManager = (+this).getRelatedManager()
      (+this).clearListsOfRelatedManager()
      //call delete method in all related role managers
      if (relatedManager.isRight) {
        //println("In IF STATEMENT" + relatedManager.right.get);
        var list: ListBuffer[IRoleManager] = relatedManager.right.get
        list.foreach { m =>
          (+m).deleteObjectFromSynchro()
        }
      }
      //clear now the related manager list
      (+this).clearRelatedManager()
      //delete all roles this element has      
      var player = this.player;
      if (player.isRight) {
        //println("In IF STATEMENT" + player.right.get);
        var test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        var roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
    }
  }

}