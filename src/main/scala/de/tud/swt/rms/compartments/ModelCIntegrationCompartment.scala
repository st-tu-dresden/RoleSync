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

object ModelCIntegrationCompartment extends IIntegrationCompartment {

  def getIntegratorForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[Person])
      return new PersonConstruct()
    return null
  }

  class PersonConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): Unit = {
      //println("Start Register Integration " + comp);

      //Step 1: Get construction values
      var fullName: String = +this getFullName();
      var result: Array[java.lang.String] = fullName.split(" ");
      var firstName: String = result.head;
      var lastName: String = result.last;

      //Step 2: Create the object in the other models
      var register: SimplePerson = null;
      if (comp.isInstanceOf[Male])
        register = new SimplePerson(firstName + " " + lastName, true)
      else
        register = new SimplePerson(firstName + " " + lastName, false)
      
      var manager = +comp getManager()
      
      //Step 3: Create Containers
      createContainerElement(register, SynchronizationCompartment.createRoleManager(), comp, manager)

      //Step 4: Finish Creation
      ModelCIntegrationCompartment.this.makeCompleteIntegrationProcess(containers)
      
      //println("Finish Register Integration");
    }
  }

}