package de.tud.swt.rms.compartments

import scala.collection.immutable.Set
import de.tud.swt.rms.PlayerSync
import de.tud.swt.rms.IDestructionCompartment
import de.tud.swt.rms.roles.IDestructor
import de.tud.swt.rms.roles.IRoleManager
import de.tud.swt.rms.models.modelB.Family

/**
  * Calls the destruction method from all related RoleManagers and then deletes all roles from this player.
  */
object ModelABCDestructionCompartment extends IDestructionCompartment {

  def getDestructorForClassName(classname: Object): IDestructor = {
    if (classname.isInstanceOf[Family])
      return new FamilyDelete()
    return new DeleteRole()
  }

  class FamilyDelete() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //println("##Delete roles and related from Player: " + this.player);
      //delete also all persons that belong to this Family
      var relatedManager = (+this).getRelatedManager()
      (+this).clearListsOfRelatedManager()
      //call delete method in all related role managers
      if (relatedManager.isRight) {
        //println("In IF STATEMENT" + relatedManager.right.get);
        var list: Set[IRoleManager] = relatedManager.right.get
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
    }
  }

  class DeleteRole() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //println("##Delete roles and related from Player: " + this.player);
      var relatedManager = (+this).getRelatedManager()
      (+this).removeThisManager()
      //call delete method in all related role managers
      if (relatedManager.isRight) {
        var list: Set[IRoleManager] = relatedManager.right.get
        list.foreach { m =>
          //proof player for Family
          var player = m.player
          if (player.isRight) {
            var realPlayer = player.right.get
            if (!realPlayer.isInstanceOf[Family]) {
              (+m).clearRelatedManager()
              (+m).deleteObjectFromSynchro()
            }
          }
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
    }
  }

}