# UNCTale
Authors: Vincent Wang and Levin Wu  

---

## Overview

The player spawns in a grid representing the UNC campus. The school has been taken over by evil rams and
brainwashed students and professors. The player's goal is to avoid these enemies while collecting money
to save their bank account.

Board, CollisionEvent, and Character have been implemented. The board can generate a random level or a preset one
stored in a text file. CollisionEvent handles collision logic. Character is the implementation of movable piece and will represent the player, 
student enemies, and professors.

---

## Quickstart Guide

- The player is trying to: rescue the school from Ramses by navigating the maze and collecting money along the way.
- The player can move: by using WASD to play this side scroller.
- Enemies will stop you by: shooting bullets that lower your health until you die.
- This will happen when an enemy is successful: when you're health reaches 0, it will be game over.  
- Other game mechanics include: pencils and apples can increase health and attack. Homework decreases attack and score.
- Money increases score.

---

## Known Issues / Incomplete Features

- Pixel Font is not working.

## Additional Features

-5 levels instead of 3.
-Difficulty setting that allows for harder playthrough for pros.
-Player gets to dodge bullets instead of just getting hit by enemies.
-Instructions are also provided in-game.
-Special portal item that can teleport you.

## Notes
Using OpenJDK 24. I (Vincent) tried to implement a basic UI to test the game features but ran into complications. In the
process, I updated to JDK 24, though with the current game this is of no use. I later realized we are to test the game
by using toString(). 

## Sources Used
Keybind demo: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/events/KeyEventDemoProject/src/events/KeyEventDemo.java
Java Swing Documentation
KeyEvent: https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html
Canvas: https://docs.oracle.com/javase/8/docs/api/java/awt/Canvas.html
KeyListener: https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html

