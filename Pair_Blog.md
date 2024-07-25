# Assignment II Pair Blog Template

## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](/put/links/here)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

The `ZombieToast` (line 27 - line 53) and `Mercenary` (line 103 - line 129) classes implement identical movement patterns for when the player has consumed an `InvincibilityPotiion`.

The `ZombieToast`'s standard movement pattern (line 55 - line 61) is identical to the `Mercenary`'s movement pattern when the player has consumed an `InvisibilityPotion` (line 93 - 101) (the discrepancy in line count is due to superfluous `map.moveTo` calls in the `Mercenary` class)

There is frequent and unneeded repetition of the `map.moveTo` method or `game.getMap().moveTo` method throughout the Entities in the enemies package, especially given all enemies move on every tick.

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.i

The two design patterns that could most improve the quality of code is the Template Pattern and Strategy Patterns.

To address the first instance of code duplication, we should implement movement strategy patterns for each of the enemy types which define their movement algorithm - we could implement a strategy pattern for when the player has an `InvicibilityPotion` that can be shared between the Mercenary and ZombieToast entities and create a strategy pattern for randomised movement that can be shared between the `ZombieToast`'s standard movement pattern and the `Mercenary`'s movement pattern when the player has consumed an `InvisibilityPotion`.

The second instance of code duplication can be resolved straigtforwardly by calculating the value of next position using the strategy pattern and then only calling the `map.moveTo` method when that computation has been finalised but, this refactoring can be taken further; Given all moving entities will 'move' every tick (every iteration of the game loop) a Template pattern where the next position is calculated by subclassed entity and then the movement is actualised in the super class would create more concise movement code. 

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

[Briefly explain what you did]

### b) Observer Pattern

> Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

There is an observer pattern within the Game class. Game acts as a the subject and maintains a list of ComparableCallbacks. These callbacks are the observers. Within the Game class are the register (or registerOnce), unsubscribe and tick methods; register adds observers to the list, unsubsribe can remove them and tick notifies the callbacks if any changes are made by calling their run method. Run method in ComparableCallback acts as the update method and executes the Runnable tasks if they are valid. 

### c) Inheritance Design

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W14A_JALAPENO/assignment-ii/-/merge_requests/1)

> i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

The code smell present is that code above violates the Liskov Substitution Principle. The presence of empty implementations for the onOverlap, onMovedAway, and onDestroy methods in subclasses like Exit indicates  that these methods are not universally applicable to all Entity subclasses. Every class that inherits entity has empty implementations of onOverlap, onMovedAway, and onDestroy, meaning that they have the same code smell. 

> ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

We redesign the inheritance structure by adding new interfaces: Destructible, MoveAwayable, and Overlappable. We add the function onMovedAway into MoveAwayable, onOverlap into Overlappable, and onDestroy into Destructible. We remove the onMovedAway, onOverlap and onDestroy in the Entity class and for each subclass of Entity, we have them implement these interfaces, depending on the properties of these entities. Since onOverlap and onMovedAway are called in GameMap, removing onMovedAway, onOverlap in the Entity class
will cause errors. Thus, we would have to create a new getEntities method that gets either an Entity of type MoveAwayable or Overlappable. To do this we create a new interface: EntityInterface, and have Destructible, MoveAwayable and Overlappable inherit EntityInterface, and with Entity implementing EntityInterface. We put the two functions canMoveOnto and getPosition into EntityInterface as we need them to create the getEntities method. We create a new getEntities method that gets all the entities at a certain position and of a certain type: MoveAwayable or Overlappable. Thus, these changes allow for us to remove onMovedAway, onOverlap and onDestroy from the Entity class so that there is no violation of the Liskov Substitution principle.



[Briefly explain what you did]

### d) More Code Smells

[Links to your merge requests](/put/links/here)

> i. What design smell is present in the above description?

The code smell present is Shotgun Surgery as to make a change on how entities are collected, many different changes must be made for the code to continue to function. This is a violation of the single responsibility principle that dictates a class should only have a single responsibility. This indicates high coupling and rigidity/fragility that makes making any changes to the codebase extremely difficult.

> ii. Refactor the code to resolve the smell and underlying problem causing it.

I used the interface InventoryItem that was provided and added a default method add which is given the item to be added and the players inventory that returns a boolean value based on if the item is successfully added. All inventory items implement this method and therefore adopt the add function without it having to be in the class itself. 

### e) Open-Closed Goals

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W14A_JALAPENO/assignment-ii/-/merge_requests/1)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

In the current design, adding new types of goals requires modifying the Goal class to add new case branches in the switch statement. This approach does not comply with the open-closed principle, as each new goal type requires modifying the existing Goal class. Instead, new goal types should be added by extending the behavior without altering the Goal class.


> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

The design is not sufficient as it is. To fix the design, we will use the Composite Pattern. Firstly, we create a common interface that each of the specific goal strategies  (ExitGoal, BoulderGoal, TreasureGoal) implement in their respective classes.  These classes are the leaf components in the Composite Pattern, representing individual goals that do not contain other goals. We create a CompositeGoal abstract class, which holds goal1 and goal2 as private final variables, providing getters and a constructor. Next, we create two new classes representing composite objects: OrGoal and AndGoal, which inherit from CompositeGoal. This design allows for flexible and scalable goal combinations without modifying existing code, adhering to the Open-Closed Principle. 

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

### Choice 1 (Snakes)

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W14A_JALAPENO/assignment-ii/-/merge_requests/6)

**Assumptions**

- Snake bodies can attack the player
- When a snake gets a health or attack buff, the entire body gets the buff. 
- If a new body node is created after a previous buff is applied, then that body does not receive the buff.
- If a new body node is created when a snake consumes an item that gives it a buff, then that body receives the buff.
- Players can fight invisible snakes
- A snake can be invincible and invisible at the same time.
- Snakes can teleport through portals, but one node at a time.
- DEFAULT_KEY_BUFF = 2.0;
- DEFAULT_TREASURE_BUFF = 1.0;
- DEFAULT_ARROW_BUFF = 2.0;
- DEFAULT_ATTACK = 1.0;
- DEFAULT_HEALTH = 5.0;

**Design**

[Design]

A snake is represented as a linked list of nodes. We create a SnakeNode interface, where the classes SnakeHead and SnakeBody will implement, and both will be subclasses of Enemy. For each SnakeHead, there is a right reference to the next SnakeNode in the linked list, and for each SnakeBody, there is a right and left references to the next and previous SnakeNodes of the linked list as well as a reference to the SnakeHead node. 

A SnakeHead node will also have a list of previous positions, as this will be needed for the movement of the body nodes and the splitting of the snake if it is invincible. It will also hold a SnakeState variable, that stores the state of the snake, whether the snake is hibernating, invicible or invisible or any combination of the three. It will also hold the buff variables: attackArrowBuff, healthTreasureBuff, healthKeyBuff.

A SnakeBody node will also hold a reference to the state of the snake. It will also store a variable of the index of this SnakeBody node. 

I also created an SnakeConsumable interface for all of the items that are consumable by the snake: invincibility potion, invisibility potion, key, treasure, arrow. I created the functions that applied their respective buffs to the snake once consumed.

To move the snake, we move the head first by assigning the head AI_MOVEMENT priority and all the body nodes the AI_MOVEMENT_CALLBACK movement priority when we create and register the head and body nodes. We move the head using the dijkstra's algorithm provided for the mercenary movement, and the body node using the previous position array, by selecting the array index: last index - index of the node. This ensures each body node moves to the position previously occupied by the node in front of it. 

When a body node gets destroyed, this is handled in the onDestroy function. If the snake is invincible a new snake is created by destroying the right node of the destroyed node, creating a new SnakeHead and rerouting the indices and head references of the right nodes and creating a new state based on the current state of the snake. 

The buff of the invisibility potion are handled in the canMoveOnto functions. Since the body nodes do not move in chronological order, we must allow collisions or overlapping between nodes of the same snake for only the snake movement phase. So we make it so that it can overlap with a node of the same snake only if that node is an adjacent node. 


**Changes after review**

[Design review/Changes made]

**Test list**

- testSnakeConsumesTreasure
- testSnakeConsumesKey
- testSnakeConsumesInvincibilityPotion
- testSnakeConsumesInvisibilityPotion
- testSnakeConsumesArrow
- testCanOverlapWallWhenInvisible
- testSnakeMovesOverOtherSnakesWhenInvisible
- testSnakeHibernatesWhenThereIsNoFood
- testEvadesWallWhenThereIsTreasure
- testEntireSnakeKilledWhenHeadIsDestroyedAndNotInvincible
- testEntireSnakeKilledWhenHeadIsDestroyedAndInvincible
- testDestroyBodyNodeWhenNotInvincible
- testDestroySnakeBodyWhenInvincible
- testDestroyHibernatingInvincibleSnake
- testSnakeCannotMoveOntoItselfWhenNotInvisible
- testSnakeCannotMoveOntoItselfWhenInvisible
- testSnakeBodyIsBuffedWhenConsumedTreasure
- testSnakeBodyIsBuffedWhenConsumedKey
- testSnakeBodyIsBuffedWhenConsumedArrow
- testSnakeCannotOverlapOtherSnakeWhenNotInvisible
- testSnakeBodyCanTeleport
- testSnakeTakesDamageWhenAttackedByPlayer
- testSnakeKilledWhenBombExplodes
- testSnakeBodyDestroyedWhenBombExplodes
- testSnakeMovesShortestPathWhenThereisFood
- testSnakeCannotMoveThroughWallsWhenNotInvisible
- testSnakeCanOverlapClosedDoor
- testSnakeCanOverlapOpenDoor
- testSnakeCanOverlapMercenary
- testSnakeCanOverlapSpider
- testSnakeCanOverlapBoulder
- testSnakeCanOverlapSwitch
- testSnakeCanOverlapExit
- testSnakeCanTeleport
- testSnakeCanOverlapZombieToast
- testSnakeCanOverlapZombieToastSpawner
- testSnakeCanOverlapWood
- testSnakeCanMoveOntoBomb
- testSnakeCanMoveOntoSword

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
