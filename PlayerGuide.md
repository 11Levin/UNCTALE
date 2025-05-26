Requirements

(23 pts) Game Overview
Title: UNCtale - Ramses Revolution

Game theme/story: Ramses has led a coup against Lee Roberts and taken over the school to make it a school for Rams. He
has brainwashed professors and students to defend him and released a herd of Rams to block the players from proceeding.
It is now all up to one student to make his way to Kenan Stadium and stop Ramses.

Perspective: Top-down, scrolling for movement.

Player actions: What can the player do?: Move, collect, attack, use items

Enemy behavior: How do enemies move or act?: They move around until they launch dodgeable attacks at the player until
defeated.

Gameplay loop: What happens during gameplay, and what core actions repeat?: The player tries to make their way across
campus while either avoiding or attacking enemies and collecting items to get stronger for the final fight against
Ramses.
Level progression:

How does a level end?: When the player successfully traverses from one end of the map to the other end while staying
alive.
How are levels different from one another? Each level will be a different part of campus, meaning there will be a
different amount of obstacles blocking the way or enemies populating the level.

Game structure: Is there a win condition (must be reachable for grading) or is gameplay endless? To reach the exit in
each level or beat Ramses in the final level all without dying.

(27 pts) Game Assets Inventory

Create a catalog of game elements. These may be referred to as Pieces (as in pieces on a board), Assets, or Elements
interchangeably. You will need to create the following:

Player character (UNCPlayer) - This is the character that the player controls.
Enemies should be visually distinct from each other and should vary in either movement and/or collision consequences.

Enemy - Easy - Students (UNCStudent_Enemy)- Mind-controlled students have less will, leading to simple movement back and
forth in a predetermined path, and will have a simple attack pattern. Drops a pencil (buff).

Enemy - Medium - Professors (UNCProfessor_Enemy)- Despite being mind-controlled the professors retain higher function,
leading to them moving around randomly with a harder to predict attack pattern. Drops an apple (buff)

Enemy - Hard - Ramses (UNCRamses_Enemy)- As the final boss Ramses sits on a throne and waits for the player to challenge
him. He has a difficult attack pattern with a lot of health.

Obstacle - Rams (UNCRam_Obstacle): Takes up space on the grid and cannot be interacted with (e.g., boulder, wall). Rams
will show up all around the map, forcing the player to move across predetermined paths along the map.

Positive Buff 1 - A collectible item that modifies the player's character in some positive way. Pencils (
UNCPencil_Buff): Pencils will increase the player's attack while apples will increase the player’s health.

Negative Buff - A collectible item that modifies the player's character in some negative way. Homework (
UNCHomework_Debuff): will be littered across the map making the player choose between fighting enemies or weakening
themselves by picking up homework that decreases health and attack.

Treasure - A collectible item that increases the score: Money ((UNCMoney_Treasure): Which college student doesn’t like
free money? Picking up this item will increase the score

Exit - An asset that ends the level when reached (e.g., door, castle): UNC Logo (UNCLogo_Exit): The exit will be marked
by a UNC logo and signifies the end of a level.

(BONUS) Dealer’s choice -Door (UNCDoor_Portal): A maze of portals that you have to escape from because you got stuck in
Davis after it closed. The maze will consist of chambers, each containing multiple portals to choose from.

Only the Rams/Doors can impede Movement, the rest are collectible items and you can walk through enemies.

Grass Texture: https://marketplace.secondlife.com/p/Grass-Square-Texture/11370343

Portal Picture: https://opengameart.org/content/portals


