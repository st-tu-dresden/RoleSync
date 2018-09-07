# Role-based Runtime Model Synchronization (Running Example)

Supplementary material for the [SEAA 2018](http://dsd-seaa2018.fit.cvut.cz/seaa/index.php?sec=main#page_header) publication *"Role-based Runtime Model Synchronization"* by Christopher Werner, Hendrik Schön, Thomas Kühn, Sebastian Götz, and Uwe Aßmann.

The repository described the synchronization of 3 related models. Therefore the Family-to-Person example from the Transformation Tool Contest 2017 (TTC 2017) is used.

## Installation

Needs the [SCROLL Scala Library](https://github.com/max-leuthaeuser/SCROLL/) as dependency and named in the build.sbt document.

### Prerequisites

* Java SE Development Kit 8 or 9
* SBT (Scala Build Tool)
   * Version 0.13.* only with Java 1.8
   * from Version 1.* with Java 9
   * SBT sets its version in project/build.properties. Remove it if neccessary.

### Get a Copy of this Repository

git `clone https://github.com/st-tu-dresden/RoleSync.git`

### Setup your favorite IDE

 - **IntelliJ**: use the built-in SBT importer.
 - **Eclipse**: use the sbteclipse SBT plugin.

## Running example

The [Example](src/main/scala/de/tud/swt/rms) folder contains the source code of the running example:

* `ExampleSync.scala` as start class that show synchronization operations with console output.
* `SynchronizationCompartment.scala` as global management object.
* Synchronization rules in the [Compartments](src/main/scala/de/tud/swt/rms/compartments) folder.


