# Assignment II Pair Blog Template

## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](/put/links/here)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

[Answer]

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

[Answer]

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

[Briefly explain what you did]

### b) Observer Pattern

> Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

[Answer]

### c) Inheritance Design

[Links to your merge requests](/put/links/here)

> i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

The code smell present is that code above violates the Liskov Substitution Principle. The presence of empty implementations for the onOverlap, onMovedAway, and onDestroy methods in subclasses like Exit indicates  that these methods are not universally applicable to all Entity subclasses. Every class that inherits entity has empty implementations of onOverlap, onMovedAway, and onDestroy, meaning that they have the same code smell. 


[Answer]

> ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

We redesign the inheritance structure by adding new interfaces: Destructible, MoveAwayable, and Overlappable. We add the function onMovedAway into MoveAwayable, onOverlap into Overlappable, and onDestroy into Destructible. We remove the onMovedAway, onOverlap and onDestroy in the Entity class and for each subclass of Entity, we have them implement these interfaces, depending on the properties of these entities. Since onOverlap and onMovedAway are called in GameMap, removing onMovedAway, onOverlap in the Entity class
will cause errors. Thus, we would have to create a new getEntities method that gets either an Entity of type MoveAwayable or Overlappable. To do this we create a new interface: EntityInterface, and have Destructible, MoveAwayable and Overlappable inherit EntityInterface, and with Entity implementing EntityInterface. We put the two functions canMoveOnto and getPosition into EntityInterface as we need them to create the getEntities method. We create a new getEntities method that gets all the entities at a certain position and of a certain type: MoveAwayable or Overlappable. Thus, these changes allow for us to remove onMovedAway, onOverlap and onDestroy from the Entity class so that there is no violation of the Liskov Substitution principle.



[Briefly explain what you did]

### d) More Code Smells

[Links to your merge requests](/put/links/here)

> i. What design smell is present in the above description?

[Answer]

> ii. Refactor the code to resolve the smell and underlying problem causing it.

[Briefly explain what you did]

### e) Open-Closed Goals

[Links to your merge requests](/put/links/here)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

In the current design, adding new types of goals requires modifying the Goal class to add new case branches in the switch statement. This approach does not comply with the open-closed principle, as each new goal type requires modifying the existing Goal class. Instead, new goal types should be added by extending the behavior without altering the Goal class.



[Answer]

> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

To fix the design, we will use the Composite Pattern. Firstly, we create a common interface that each of the specific goal strategies  (ExitGoal, BoulderGoal, TreasureGoal) implement in their respective classes.  These classes are the leaf components in the Composite Pattern, representing individual goals that do not contain other goals. We create a CompositeGoal abstract class, which holds goal1 and goal2 as private final variables, providing getters and a constructor. Next, we create two new classes representing composite objects: OrGoal and AndGoal, which inherit from CompositeGoal. This design allows for flexible and scalable goal combinations without modifying existing code, adhering to the Open-Closed Principle. 

[Briefly explain what you did]

### f) Open Refactoring

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:

## Task 2) Evolution of Requirements 👽

### a) Microevolution - Enemy Goal

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 1 (Insert choice)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 2 (Insert choice)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 3 (Insert choice) (If you have a 3rd member)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

## Task 3) Investigation Task ⁉️

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:
