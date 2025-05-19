# COMP301 - Project 1: Game Proposal & Asset Plan (50 pts)

**Summer 2025**  
**Due: Sunday, May 25th**

---

## Overview

Before you write any code, you must first design your final project, which for us is going to be an arcade-style video game. This milestone asks you to clearly define the world, mechanics, and visual elements of your game before implementation begins. It will also be a chance to get feedback and make sure that your project is scoped correctly.

The final game will follow a 2D arcade style with a grid-based layout, using only square aspect ratio assets. You may choose either a top-down or side perspective. To be approved, your proposal must include all of the game assets described below.  In addition, your game should also feature multiple levels with increasing difficulty. Once we get to front-end, your game will be graded on overall cohesiveness both visually and mechanically, so think through this proposal carefully. The goal of this project is to ensure your design is both creative and technically feasible before moving forward with development.

You may choose to work with a partner, but this arrangement will last the entirety of the course (1 month). Working together does not guarantee the exact same grade. GitHub contributions may be analyzed to ensure that one partner is not doing all o the work.

## Objectives

- Propose a creative, technically feasible game that can be built in the month of Summer COMP 301
- Describe core gameplay and level design
- Outline components needed for future OOP implementation

## Requirements

### (23 pts) Game Overview

Create a requirements doc by describing the key ideas behind your game. For each section, write a few sentences with enough detail that it is clear to another person (TA and Prof) how the game works.

- **Title**
- **Game theme/story** including character motivations
- **Perspective** (top-down or side-scrolling)
- **Player actions**: What can the player do? (e.g., move, collect, attack, use items)
- **Enemy behavior**: How do enemies move or act?
- **Gameplay loop**: What happens during gameplay, and what core actions repeat?
- **Level progression**:
    - How does a level end?
    - How are levels different from one another?
- **Game structure**: Is there a win condition (must be reachable for grading) or is gameplay endless?

---

### (27 pts) Game Assets Inventory

Create a catalog of game elements. These may be referred to as `Pieces` (as in pieces on a board), `Assets`, or `Elements` interchangeably.  You will need to create the following:

- **Player character** - This is the character that the player controls.
- **Enemy - Easy** - Enemies should be visually distinct from each other and should vary in either movement and/or collision consequences.
- **Enemy - Medium**
- **Enemy - Hard**
- **Obstacle** - Takes up a space on the grid and cannot be interacted with (e.g., boulder, wall).
- **Positive Buff 1** - A collectible item that modifies the player character in some positive way.
- **Negative Buff** - A collectible item that modifies the player character in some negative way.
- **Treasure** - A collectible item that increases the score.
- **Exit** - An asset that ends the level when reached (e.g., door, castle).
- **(BONUS) Dealer’s choice** - New NPC, portals, key to the exit, etc. If you propose a bonus but do not implement it, you won’t be penalized, but neither will you get credit for half implementations.  Extra credit will be awarded based on the complexity of the final result.  

For each, provide:

- A name and short description of how it fits into the game’s theme.
- A visual asset (feel free to use generative AI or create your own).
- All assets must be **square aspect ratio** (1:1).
- Describe play interaction including movement behavior and if collisions are possible with another asset, what happens.

## Submission Guidelines

All visual assets should be stored under the `resources` folder in your GitHub repo. Create a markdown file in the root directory called `PlayerGuide.md`. This is where you will describe your game assets.

When finished, add the file to your repo and submit it to Gradescope for grading.
