# Code Documentation

## Introduction

More detailed explanation of code and conventions will be given here. Anything unexpected/nonstandard is explained here.

## File Structure

Game objects called pieces are organized into buff, movable, and immovable. The java and image files follow this
structure.
Buffs actively benefit or harm the player.

## Piece Type

Analogous to file structure, pieces can be type BUFF, MOVABLE, and IMMOVABLE. This can be used for collision handling.
getName() can be called for more specificity.

## buff

Doesn't include Treasure.

## movable

Includes the player and enemy classes.

## immovable

Includes the ram obstacle and Treasure.

## Naming Conventions

Classes will not implement interfaces unless necessary for function. Implementing classes will not be followed by "
Impl".
Subclasses will not be followed by a prefix indicating their superclass. Ex: a bullet class will be named "Bullet", not
"BulletEnemy". Directory names will be singular.

## Level Creation

Levels are stored in text tiles at resources/level. The first line is (row) (col) (type). (row, col) is the size of the
level.
Type indicates how the level will be loaded. If type = 1, the strings representing tiles will be full-length. If type =
2, one-character
codes will be used to generate the level. keyToName in resources/misc has the character to full-name associations.

## CollisionEvent

CollisionEvent will store updated values of a Movable after a collision, not change in values.