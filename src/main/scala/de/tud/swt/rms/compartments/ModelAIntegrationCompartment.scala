package de.tud.swt.rms.compartments

import de.tud.swt.rms.PlayerSync
import scala.collection.mutable.ListBuffer
import de.tud.swt.rms.IIntegrationCompartment
import de.tud.swt.rms.ISyncCompartment
import de.tud.swt.rms.roles.IIntegrator
import de.tud.swt.rms.models.modelA.Person
import de.tud.swt.rms.SynchronizationCompartment
import de.tud.swt.rms.models.modelA.Male
import de.tud.swt.rms.roles.IRoleManager
import de.tud.swt.rms.roles.ISyncRole
import de.tud.swt.rms.ModelElementLists
import de.tud.swt.rms.models.modelC.SimplePerson
import de.tud.swt.rms.models.modelA.Female

object ModelAIntegrationCompartment extends IIntegrationCompartment {

  def getIntegratorForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[SimplePerson])
      return new SimplePersonConstruct()
    return null
  }

  class SimplePersonConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): Unit = {
      //println("Start Person Integration " + comp);

      //Step 1: Get construction values
      var fullName: String = +this getCompleteName();
      var result: Array[java.lang.String] = fullName.split(" ");
      var firstName: String = result.head;
      var lastName: String = result.last;
      var male: Boolean = +this getMale();

      //Step 2: Create the object in the other models
      var person: Person = null;
      if (male) {
        person = new Male(firstName + " " + lastName)
      } else {
        person = new Female(firstName + " " + lastName)
      }
      
      var manager = +comp getManager()
      
      //Step 3: Create Containers
      createContainerElement(person, SynchronizationCompartment.createRoleManager(), comp, manager)

      //Step 4: Finish Creation
      ModelAIntegrationCompartment.this.makeCompleteIntegrationProcess(containers)

      /*//Step 3: Add RoleManager roles and Delete roles        
      var rmMA = SynchronizationCompartment.createRoleManager();
      person play rmMA

      var personDelete = SynchronizationCompartment.getDestructionRule().getDestructorForClassName(person)
      rmMA play personDelete

      //Step 4: Add the related Role Manager
      var manager = (+comp).getManager()
      if (manager.isRight) {
        var realManager: IRoleManager = manager.right.get
        var related = realManager.getRelatedManager()
        realManager.addRelatedManager(rmMA)
        rmMA.addRelatedManager(realManager)
        related.foreach { r =>
          r.addRelatedManager(rmMA)
          rmMA.addRelatedManager(r)
        }
      }

      //Step 5: Synchronize the Compartments
      SynchronizationCompartment combine person

      //Step 6: Integrate in Synchronization Rules
      var player = ModelAIntegrationCompartment.this.plays.roles(comp)
      player.foreach { r =>
        if (r.isInstanceOf[ISyncRole]) {
          var syncRole: ISyncRole = r.asInstanceOf[ISyncRole]
          var syncComp: ISyncCompartment = syncRole.getOuterCompartment()
          var newRole = syncComp.getNextIntegrationRole(person)
          if (newRole != null) {
            rmMA play newRole
          }
        }
      }

      //Step 7: Fill Test Lists
      ModelElementLists.addElement(person)*/
      
      //println("Finish Register Integration");
    }
  }

}