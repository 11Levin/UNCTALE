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
- Enemies will stop you by: lowering your health until you die.
- This will happen when an enemy is successful: when you're health reaches 0, it will be game over.  
- Other game mechanics include: pencils and apples can increase health and attack. Homework decreases attack and score.
- Money increases score.

---

## Known Issues / Incomplete Features

- [ ] Feature not yet implemented: a bullet class so enemies can shoot bullets and basic enemy AI.  
- [ ] Known bug: characters can't move.
- [ ] Design limitation: we're trying to configure this game around being turn-based, which we didn't previously expect.

## Notes
Using OpenJDK 24. I (Vincent) tried implemented a basic UI to test the game features but ran into complications. In the
process, I updated to JDK 24, though with the current game this is of no use. I later realized we are to test the game
by using toString(). 

## Sources Used
Keybind demo: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/events/KeyEventDemoProject/src/events/KeyEventDemo.java
Java Swing Documentation
KeyEvent: https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html
Canvas: https://docs.oracle.com/javase/8/docs/api/java/awt/Canvas.html
KeyListener: https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html

