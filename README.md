# UNCTale
Authors: Vincent Wang and Levin Wu  

---

## Overview

The player spawns in a grid representing the UNC campus. The school has been taken over by evil rams and
brainwashed students and professors. The player's goal is to avoid these enemies while collecting money
to save their bank account.
---

## Quickstart Guide

- The player is trying to: rescue the school from Ramses by navigating the maze, avoiding enemies, and collecting money along the way. The player wins upon completing all levels.
- The player can move: by using WASD or the directional buttons.
- Enemies will stop you by: lowering your health until you die.
- When you're health reaches 0, it will be game over.  
- Other game mechanics include: pencils and apples can increase health and attack. Homework decreases attack and score.
- Portals teleport the player.
- Money increases score.

## Compiling Instructions
- This project can be compiled by downloading the project file (such as from GitHub), opening it with a code editor (such as IntelliJ), and running it with Maven -> Plugins -> javafx -> javafx: run. 

## Testing Instructions
Making Levels
- Custom levels can be made by going to resources/level. The two integers in the first row are the dimensions of the
- level (r, c) = (number of rows, number of columns). The next r rows are length c strings. Each character represents a piece, and the character-to-piece correspondences are given in resources/misc/entity_data.txt. Each row in entity_data
- represents a unique piece of the form (character representation, piece name, piece resource path). To add more pieces, find a new character and fill in the other information - the resources will be automatically loaded.
- Making custom levels for testing is recommended rather than initializing a randomly generated level.

Printing out Board
- Board has a toString() method that prints out a grid of the single character representation's of the board's pieces.
- If a board is loaded correctly, toString() should return the level_text file, not including the board's dimensions.