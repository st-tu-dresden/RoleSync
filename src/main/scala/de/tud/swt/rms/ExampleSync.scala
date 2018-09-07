package de.tud.swt.rms

import scroll.internal.Compartment
import de.tud.swt.rms.models.modelB.Family
import de.tud.swt.rms.models.modelB.Member
import de.tud.swt.rms.compartments.ModelBCConstructionCompartment
import de.tud.swt.rms.compartments.ModelABCDestructionCompartment
import de.tud.swt.rms.compartments.ModelABCConstructionCompartment
import de.tud.swt.rms.compartments.ModelAIntegrationCompartment
import de.tud.swt.rms.compartments.SyncCommaNames
import de.tud.swt.rms.compartments.GeneralDestructor
import de.tud.swt.rms.models.modelA.Male
import de.tud.swt.rms.compartments.SyncSpaceNames
import de.tud.swt.rms.compartments.SyncKnownList

object ExampleSync extends App {

  new Compartment {
    println("%%%%%%%%%%%%%%%%%%% Add Rules to the synchronization context %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
    //Add construction and deletion rules
    SynchronizationCompartment.changeConstructionRule(ModelBCConstructionCompartment)
    SynchronizationCompartment.changeDestructionRule(ModelABCDestructionCompartment)
    //Add synchronization rules
    SynchronizationCompartment.addSynchronizationRule(new SyncKnownList())
    SynchronizationCompartment.addSynchronizationRule(new SyncSpaceNames())

    println("%%%%%%%%%%%%%%%%%%% Create some start instances of Model A %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
    //Create some instances of classes
    val johnson = new Family("Johnson")
    val dad = new Member("Dad", johnson, true, false, false, false)
    val mom = new Member("Mom", johnson, false, true, false, false)

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Change First and Lastnames %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Change first Name to Mami
    mom.setFirstName("Mami")

    //Change first Name to Dadi
    dad.setFirstName("Dadi")

    //Change last Name to Smith
    johnson.setLastName("Smith")

    //Show related Elements
    mom.listen()
    dad.listen()

    //Help printing
    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Integrate Model B %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Test 1: Model integration
    SynchronizationCompartment.changeConstructionRule(ModelABCConstructionCompartment)
    //rule change not necessary
    //destruction change not necessary
    SynchronizationCompartment.integrateNewModel(ModelAIntegrationCompartment)

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Change again last name %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Change last name to Johnson
    johnson.setLastName("Johnson")

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Changes Rules at runtime %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Test 2: Rule Delete and new Add
    SynchronizationCompartment.deleteRule("SyncNameRule1")
    SynchronizationCompartment.addSynchronizationRule(new SyncCommaNames)

    ModelElementLists.changeRegisterNames()

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Change first names %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    mom.setFirstName("Mom")
    dad.setFirstName("Dad")

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Change last name %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    johnson.setLastName("Smith")

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Synchronization rule change %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Test 3: Rule change
    SynchronizationCompartment.changeRuleFromTo("SyncNameRule2", new SyncSpaceNames)

    ModelElementLists.changeRegisterNames()

    mom.setFirstName("Mami")
    dad.setFirstName("Dadi")

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Delete first object %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    mom.deleteObjectFromSynchro();

    ModelElementLists.printALL()

    println("%%%%%%%%%%%%%%%%%%% Delete second object %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //Test 4: Destructor change
    SynchronizationCompartment.changeDestructionRule(GeneralDestructor)

    dad.deleteObjectFromSynchro();

    ModelElementLists.printALL()
  }

}