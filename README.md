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

- The player is trying to: *[insert game objective here]*  
- The player can move: *[describe allowed directions/actions]*  
- Enemies will stop you by: *[describe enemy behavior]*  
- This will happen when an enemy is successful: *[e.g., game over, lose points]*  
- Other game mechanics include: *[e.g., buffs, traps, gravity, etc.]*

---

## Known Issues / Incomplete Features

- [ ] Feature not yet implemented: *[e.g., enemy AI pathfinding]*  
- [ ] Known bug: *[e.g., player occasionally walks through walls]*  
- [ ] Design limitation: *[e.g., treasure only appears in top row for now]*

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

