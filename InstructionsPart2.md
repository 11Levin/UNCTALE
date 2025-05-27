# COMP301 - Project 2: Object Oriented Game Design 

**Summer 2025**  
**Due: Sunday, June 1st**

---

## Overview
In this second phase of the project, you will implement the core object-oriented structure that powers your game. This includes writing Java classes for the major game elements—such as the player, enemies, treasures, and obstacles—and building the logic that controls how those elements interact on a shared game board. Using interfaces, abstract classes, inheritance, and encapsulation, you will define the behavior and state of each piece and lay the groundwork for a dynamic and interactive game world. You will also implement the `Board` class, which manages piece placement, movement, and collision events. By the end of this milestone, your game will be able to simulate basic gameplay and handle player and enemy turns according to your proposal design.


## Objectives

By the end of this milestone, you should be able to:

- Implement Java classes that follow the `Piece` and `MovablePiece` interfaces, representing the game entities outlined in your project proposal.
- Use inheritance to reduce duplication and promote code reuse across similar game elements.
- Create polymorphic interfaces for game asset types (e.g., Player, Enemy, Treasure) to allow flexible handling of pieces.
- Understand and apply encapsulation when designing instance variables, particularly with position tracking and resource management.
- Define and implement rules for collision behavior using the `CollisionEvent` class.
- Design and implement a flexible `Board` class that supports piece placement, movement, and turn-based logic.
- Safely handle illegal moves, turn order, and game state transitions such as level progression or game over conditions.
- Use helper methods and encapsulated data structures (e.g., 2D arrays) to manage game logic efficiently.
- Debug your implementation with meaningful `toString()` output for internal game state inspection.


### Pieces

In the starter code you will see a package called `pieces`.  These are the required game assets that we will need to make our game, named for pieces on a game board.


Take a look at the `Piece` interface:

```{j}
public interface Piece {
    String getName();
    void setPosn(Posn posn);
    Posn getPosn();
    String getResourcePath();
}
```

As you can see, these are basic setters and getters of an `Object` in java.  We can intuit that there will be 3 fields:
- `String name`: The name represents a name that you may need in your code.
- `String resourcePath`:
  Specifies the file path to the image associated with the piece.
  Important: This path should be relative to the classpath (i.e., under "src/main/resources"). You may use any images for your pieces. I encourage you to spend some time brainstorming the art style, as the images you select will dictate the game’s overall look and feel. Although the sample uses a pixel art style, you are free to be creative—AI-generated art is perfectly acceptable.
- `Posn posn`: (Short for "Position") This is a simple x,y ordered pair used to simplify locations.

To get you started, we have given you the `abstract APiece` class that initializes all of these instance variables and implements the required methods from the interface.  Your job is to implement each of the provided children classes to initialize the values of the instance variables without taking them as parameters in the constructor.  _Note: Posn will be initialized to null right now, since it is not yet on the board._

We also have given you interfaces that represent each category of Game Asset described in the instructions.  These interfaces are empty but are nevertheless required, so that we can test each Piece without knowing class names.  This is the power of polymorphism.

### Movable Piece
Take a look at the `MovablePiece` interface and the `CollisionEvent` class:

```{j}
public interface MovablePiece extends Piece {
    CollisionEvent collide(Piece other);
}
```

```
public class CollisionEvent {
private final int points;
private final Result res;
public enum Result {CONTINUE, GAME_OVER, NEXT_LEVEL}

    public CollisionEvent(int points, Result res) {
        this.points = points;
        this.res = res;
    }

    public int getPoints() {
        return points;
    }

    public Result getResults(){
        return res;
    }
}
```
This is the minimum amount of information, but depending on your mechanics, you may put additional information in here like damage and buffs.  From this point forward, the instructions apply to most games, but you may find that the mechanics don't follow this exactly.  That's okay.  Your project proposal is now your ground truth, but these instructions will still nudge you in the direction you need to go.  

Take a look at the `enum` in the CollisionEvent object.

When certain pieces collide on the board, specific outcomes occur. For the `Player`:
1. If the player collides with nothing (`null`), game should continue unaffected. <-- Absolutely required.
2. The game will continue, but the score may change if the player collides with a piece of treasure.
3. The game might end with other conditions like if the player collides with an enemy.
4. The player will move to the next level if they collide with the exit.

The enemy behaves differently:

1. If the enemy collides with nothing (`null`), game should continue unaffected.
3. The enemy can catch up with the player and "eat" him, possibly causing the game to end.

Additional rules:

- The enemy cannot leave the room and is therefore forbidden from colliding with the exit.
- Similarly, neither the player nor the enemy should collide with obstacles.

Both the `Enemy` and `Player` classes implement the `MovablePiece` interface. Write them to match the specifications as described in your project proposal.

Hint: Focus on the numbered items only; you will implement these additional rules elsewhere.

## Board


### Board

Take a look at the `Board` interface in the `board` package.

```
public interface Board {
    
    void init(int enemies, int treasures, int obstacles);

    int getWidth();
    
    int getHeight();

    Piece get(Posn posn);
    
    CollisionEvent movePlayer(int drow, int dcol);
}
```

This is our Game Board Data Structure that holds our pieces and has encapsulated methods and manages game state.   The other item in the package is the Posn class.  This is an ordered pair of row,column coordinates.  It is final so that the values are immutable.
```
public class Posn {
private final int row;
private final int col;

    public Posn(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public String toString(){
        return row + "," + col;
    }
}
```

You have freedom to implement the BoardImpl class as you see fit.  You are encouraged to implement additional private helper methods.

### Class header, Instance Variables, and Constructors
#### Header
Surprise surprise, BoardImpl should implement the Board interface.

#### Instance Variables
You are free to define any instance variables you find useful.  The only one that you must have, is a properly encapsulated instance of 2-Dimensional array of Pieces called board.  _Hint: As you go through this class, think about what instance variables may be useful that would prevent you from searching the entire board for a piece._

#### Constructors

You should have two constructors:
1. One that takes in a width and a height and initializes a `board` array with those dimensions along with any other instance variables you choose to make.
2. One that takes in a Piece[][] that represents a game already in play in the array and sets your instance variable with the parameter along with any other instance variables you choose to make.

At initialization, the board is empty (full of null pieces).

#### toString
Write a toString method for board so that you can use print statements to help debug your code. The toString method should clearly show the whole board, and you should be able to differentiate every piece on the board.  

#### init(int enemies, int treasures, int obstacles)
We will have to set the board every level, with a different number of pieces each time.  The method takes in the number of `Enemy` pieces, `Treasure` pieces, and `Obstacle` pieces.  In addition, we know that there is one `Player` and one `Exit`.

`init  should first clear the board of any pieces.  Then, for each required piece, it should "set" the board.  If there is not a specific map, choose a random empty spot on the board, and place it.  If there is a map, be sure to load it here.

In addition, if the number of required pieces exceeds the number of available spots on the board, the method should throw an `IllegalArgumentException`.

#### Getters

The board has three getter methods:
```
int getWidth();

int getHeight();

Piece get(Posn posn);
```

- `getWidth()`: Should return the number of columns (horizontal space)
- `getHeight()`: should return the number of rows (vertical space)
- `get(Posn posn)`: get the pieces at a particular Posn coordinate in the `board` and return it.

#### Movement
_Player_

TOP_DOWN: 
- `movePlayer(int drow, int dcol)`: The MovablePieces will be able to move `UP`,`DOWN`, `LEFT`, `RIGHT` from its current position.  The inputs `drow` and `dcol` should be the delta between the current position and the new position.  For example, if the Player is at (2,3) going `UP` would be `movePlayer(-1,0)`.
- The Player has restrictions on where it can move:
    - The Player cannot move off the bounds of the board.
    - The Player cannot move where an obstacle is currently placed.


SIDE_SCROLLER:
- `movePlayer(int drow, int dcol)`: The MovablePieces will be able to move, `LEFT`, `RIGHT` from its current position.  The inputs `drow` and `dcol` should be the delta between the current position and the new position.  For example, if the Player is at (2,3) going `LEFT` would be `movePlayer(0,-1)`.
- In addition, you will have gravity, so the player will fall down if you walk off the edge of a platform.  This does not have to be animated.  The player can just move in that direction until a new obstacle stops the fall, or they fall off the map and die.
- Jumping will be most easily implemented as a diagonal move.   
- 
- The Player has restrictions on where it can move:
    - The Player cannot move off the bounds of the board.
    - The Player cannot move where an obstacle is currently placed.


If the move is illegal, the Player should remain in its current position. Otherwise, it can move into the new spot and `collide()` with any piece that might be there.

Once the Player has moved, all enemies get a chance to move.  (Enemies do not move if the `Player` action was illegal)

Once all pieces have moved, the method should return a `CollisionEvent` that represents the state of the game.  **BEWARE**  there is some order of precedence here.

For example:
- A `Player` can make it to the exit, in which case, the `Enemy` should not be able to perform another action. The `CollisionEvent` should be `Result.NEXT_LEVEL`.
- A `Player` can collect a treasure, thus increasing the score, and then immediately die by an `Enemy`.  In which case, the `CollisionEvent` would need both the score from the Player's action, and the `Result.GAME_OVER` from the Enemy's action.
- If gameplay should continue after everyone has moved, the `CollisionEvent` should be `Result.CONTINUE`

_Enemy Movement_

Enemy movement is highly dictated by your proposal, but as a default option, they can pick a random direction to move.  

However, they have more restrictions on its movement than a Player.
- `Enemy` cannot move off the bounds of the board.
- `Enemy` cannot move where a `Obstacle` is currently placed.
- `Enemy` cannot move where an `Exit` is currently placed.
- `Enemy` cannot collide with another `Enemy`.

If an enemy is trapped, it should just skip its turn.


_Other Movement Considerations_

- The piece has a reference to its current location as an instance variable.  Therefore, whenever you move it, you should update the instance variable.
- Object-based arrays store memory pointers.  That means if you place the Piece a second time, it may show up twice on your board.  You must remove the old reference when you move the piece.

## Documentation

Your `README.md` file in the root of your repository must be updated with the following sections. This documentation will help us understand and evaluate your project during grading, and it will also serve as a reference for your future work.

Please use the following structure:


- [Project Title]
- [Authors] — include both names if working with a partner.
- [Overview]
Briefly describe your game and the current state of implementation for Part 2.  
You should include:
- A 2–3 sentence summary of the game concept.
- Which major features or pieces have been implemented so far.

- [Quickstart Guide]
Explain how to compile and run your code. Include:
- How to build the project (e.g., `mvn compile`)
- How to run any tests (e.g., `mvn test`)
- Any special instructions for testing the board or piece classes manually

- [Known Issues / Incomplete Features]
If there are any features not yet implemented, partially working, or known bugs that affect gameplay, describe them here. Be honest — noting limitations will not automatically reduce your score, but it helps us understand your current progress and intentions.


## Submitting

Push your changes to the same GitHub repository used for Part 1. Make sure all new files and updates are committed and pushed. Then, submit your repository to the new Gradescope assignment labeled **Project 2: Object Oriented Game Design**.

Double-check that:
- Your implementation compiles without errors.
- All required classes and interfaces are present.
- Your code follows the structure and naming conventions described above.

Only one submission is required per group (if working with a partner), but both names must be listed in your `README.md` or project header comment.
