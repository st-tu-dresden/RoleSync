package de.tud.swt.rms.compartments

import de.tud.swt.rms.PlayerSync
import de.tud.swt.rms.IConstructionCompartment
import de.tud.swt.rms.SynchronizationCompartment
import de.tud.swt.rms.roles.IRoleManager
import de.tud.swt.rms.roles.IConstructor
import de.tud.swt.rms.ModelElementLists
import de.tud.swt.rms.models.modelA.Person
import de.tud.swt.rms.models.modelB.Member
import de.tud.swt.rms.models.modelB.Family
import de.tud.swt.rms.models.modelC.SimplePerson
import de.tud.swt.rms.models.modelA.Male
import de.tud.swt.rms.models.modelA.Female
import de.tud.swt.rms.ConstructionContainer

/**
  * Construction Process for Model A, B, and C.
  */
object ModelABCConstructionCompartment extends IConstructionCompartment {

  def getConstructorForClassName(classname: Object): IConstructor = {
    if (classname.isInstanceOf[Family])
      return new FamilyConstruct()
    else if (classname.isInstanceOf[Member])
      return new MemberConstruct()
    else if (classname.isInstanceOf[Person])
      return new PersonConstruct()
    else if (classname.isInstanceOf[SimplePerson])
      return new RegisterConstruct()
    return null
  }

  class FamilyConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //println("Start Family Construct");

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)

      //Step 4: Finish Creation
      ModelABCConstructionCompartment.this.makeCompleteConstructionProcess(containers)

      //println("Finish Family Construct");
    }
  }

  class MemberConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //println("Start Member Construct");

      //Step 1: Get construction values
      var firstName: String = +this firstName;
      var lastName: String = +this getLastName();
      var family: Family = null
      var male: Boolean = true

      var father: Family = (+this).getFamilyFather()
      var son: Family = (+this).getFamilySon()
      var mother: Family = (+this).getFamilyMother()
      var daughter: Family = (+this).getFamilyDaughter()
      if (father != null)
        family = father
      else if (son != null)
        family = son
      else if (mother != null) {
        family = mother
        male = false
      } else if (daughter != null) {
        family = daughter
        male = false
      }

      var rmFamily: IRoleManager = null
      if (family != null) {
        var manager = (+family).getManager()
        if (manager.isRight)
          rmFamily = manager.right.get //manager.right.get.asInstanceOf[IRoleManager]
      }

      //Step 2: Create the object in the other models
      var person: Person = null
      var register: SimplePerson = new SimplePerson(firstName + " " + lastName, male)
      if (male)
        person = new Male(firstName + " " + lastName)
      else
        person = new Female(firstName + " " + lastName)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, false, family, rmFamily)
      createContainerElement(false, true, register, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, person, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      ModelABCConstructionCompartment.this.makeCompleteConstructionProcess(containers)

      //println("Finish Member Construct");
    }
  }

  class RegisterConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //println("Start Register Construct");

      //Step 1: Get construction values
      var fullName: String = +this getCompleteName();
      var result: Array[java.lang.String] = fullName.split(" ");
      var firstName: String = result.head;
      var lastName: String = result.last;
      var male: Boolean = +this getMale();

      //Step 2: Create the object in the other models
      var family = new Family(lastName);
      var member: Member = null;
      var person: Person = null;
      if (male) {
        person = new Male(firstName + " " + lastName)
        member = new Member(firstName, family, false, false, true, false);
      } else {
        person = new Female(firstName + " " + lastName)
        member = new Member(firstName, family, false, false, false, true);
      }

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, family, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, member, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, person, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      ModelABCConstructionCompartment.this.makeCompleteConstructionProcess(containers)

      //println("Finish Register Construct");
    }
  }

  class PersonConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //println("Start Person Construct");

      //Step 1: Get construction values
      var fullName: String = +this getFullName();
      var result: Array[java.lang.String] = fullName.split(" ");
      var firstName: String = result.head;
      var lastName: String = result.last;

      //Step 2: Create the object in the other models
      var family = new Family(lastName);
      var member: Member = null;
      var register: SimplePerson = null;
      if (comp.isInstanceOf[Male]) {
        register = new SimplePerson(firstName + " " + lastName, true);
        member = new Member(firstName, family, false, false, true, false);
      } else {
        register = new SimplePerson(firstName + " " + lastName, false)
        member = new Member(firstName, family, false, false, false, true);
      }

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, family, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, member, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, register, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      ModelABCConstructionCompartment.this.makeCompleteConstructionProcess(containers)

      //println("Finish Person Construct");
    }
  }

}