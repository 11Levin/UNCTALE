# UNCTale

Authors: Vincent Wang and Levin Wu

---

## Overview

The player spawns in a grid representing the UNC campus. The school has been taken over by evil rams and
brainwashed students and professors. The player's goal is to avoid these enemies while collecting money
to save their bank account.

---

## Quickstart Guide

- The player is trying to rescue the school from Ramses by navigating the maze and collecting money along the way.
- The player can move by using WASD.
- Enemies will stop you by shooting bullets that lower your health until you die.
- When you're health reaches 0, it will be game over.
- Other game mechanics include: pencils and apples can increase health and attack, homework decreases attack and score,
  and money increases score.

## Compiling Instructions

This project can be compiled by downloading the project file (such as from GitHub), opening it with a code editor (such
as IntelliJ), and running it with Maven -> Plugins -> javafx -> javafx: run.

## Testing Instructions

### Making Levels

Custom levels can be made by going to resources/level. The two integers in the first row are the dimensions of the
level (r, c) = (number of rows, number of columns). The next r rows are length c strings. Each character represents a
piece, and the character-to-piece correspondences are given in resources/misc/entity_data.txt. Each row in entity_data
represents a unique piece of the form (character representation, piece name, piece resource path). To add more pieces,
find a new character and fill in the other information - the resources will be automatically loaded.
Making custom levels for testing is recommended rather than initializing a randomly generated level.

### Printing out Board

Board has a toString() method that prints out a grid of the single-character representations of the board's pieces.
If a board is loaded correctly, toString() should return the level_text file, not including the board's dimensions.
---

## Design Patterns

### MVC

The entire project follows the Model-Controller-View architecture. Model handles all logic, controller sends user
input to classes handling logic, and view displays the state of the game.

### Observer-Subject

The model and views follow the observer-subject design pattern. The views are the observers responding to what happens
in Model. The model updates the views with a GameStats object, containing relevant game information, such as score and
level to display in the views. Depending on the Model's game state, a particular view will be shown on the screen.

### Decorator

The buff visual classes follow the decorator pattern. When a player picks up a buff, the player is wrapped in a
decorator
that changes how the player is displayed. Since the player can pick up many buffs, we decided to increment the base
player's attack and health rather than add attack and health later through a decorator. This way, the player does not
get wrapped in too many decorators, hurting performance. Also, at the end of each level, the player is
unwrapped and rewrapped to prevent any duplicate decorators (since duplicate buffs have no visual changes).

### Factory

To make spawning enemies easier, an enemy factory was made. The EnemyFactoryClass has static methods that create enemies
as needed.

### Asynchronous Programming

Multiple threads allow the player and enemies to move independently (rather than all at once. This makes the enemies
less
predictable and the game more enjoyable. A gameTick thread handles most of the game's logic and separate enemy threads
handle enemy movement. JavaFX has a built-in thread to handle screen drawing.

## Known Issues / Incomplete Features

- Pixel Font is not working.

## Additional Features

- 5 levels instead of 3.
- Difficulty setting that allows for harder playthrough for pros.
- Player gets to dodge bullets instead of just getting hit by enemies.
- Instructions are also provided in-game.
- Portal that can teleport you.

