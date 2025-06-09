# COMP301 - Project 3: The GUI

**Summer 2025**  
**Due: Sunday, June 8th**

### Model, Observer, and Subject

The first part will focus on completing the model that will hold our entire game state, including scores.  Take a look at the `Model` interface:
```
public interface Model extends Subject {
    enum STATUS {GAME_OVER, IN_PROGRESS }

    int getWidth();
    int getHeight();

    Piece get(Posn p);

    int getCurScore();
    int getHighScore();
    int getLevel();

    //Change status from IN_PROGRESS and GAME_OVER
    STATUS getStatus();
    void startGame();
    void endGame();

    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
}
```

If you are doing a side scroller, you may remove the `moveDown()` method.  Beyond that, all methods must be implemented.  Jump can be implemented as `moveUp()`.

There is an enumerated status at the top that will track whether there is an active game.  In addition to the methods here, notice that `Model extends Subject` which adds one more additional method: `void addView(View v)`.


#### Header, Instance Variables, and Constructors

- Surprise surprise, our `ModelImpl` class needs to implement `Model`.
- You can see 7 getter methods, 2 methods that change the status, and 4 movement methods.  You can intuit that there needs to be instance variables for a `Board`(to handle `getWidth`, `getHeight`, `get`), an `int curScore`, an `int highScore`, an `Status status`, `int level`, and a `List` of `Observers` (for the Subject interface).
- Write a constructor that takes in an `int width` and `int height`, and creates a new board, and initializes all instance variables to their pregame state.  All ints are 0 ,and the `STATUS` should be `GAME_OVER`.
- Write a second constructor that takes in a Board object already prepopulated with pieces, and initializes all instance variables to their pregame state.  This constructor is for testing.  You should be able to give it a gameboard already in progress.

#### Getters

Implement all getters.  Delegate to the `Board` when appropriate:
- `int getWidth()`
- `int getHeight()`
- `Piece get(Posn p)`
- `int getCurScore()`
- `int getHighScore()`
- `int getLevel()`
- `int getStatus()`

#### Status Methods

_startGame()_

This is where we initialize our board and start the game logic.  To begin, change the status of the game to `IN_PROGRESS`. Make sure that the current score is 0, and that the level resets to 1.  Every time the hero reaches an exit, the board will be re initialized and the level will increment by 1.  You must have **at least** 2 `Treasure` pieces and 2 `Obstacles` pieces.  However, you have freedom to decide what numbers you prefer for your game.  We will work more with Enemies and Buffs next week.

Finally, call updateViews() so the front end reflects the reset state.


_endGame()_

Chang the status to `GAME_OVER` and check to see if there is a new high score.

#### Movement

### Hero
Next, our model specifies the actions that the user can take.  Using our `board.moveHero()` method, move the hero in the correct direction, then check the CollisionResult to see if the game state needs to be updated accordingly.  If there are points in the `CollisionResult`, update the score. If it returns a `CollisionResult.Result.NEXT_LEVEL`, reinitialize the board at the next level.  If it returns `CollisionResult.Result.GAME_OVER`, you should end the game.

### Enemy

Enemies don't need to move this week, although they will next week.  It is recommended to add them as a stationary placeholder to test collisions.  If the rest of your GUI is finished, and you'd like to implement their movement, you are welcome to work ahead.  

#### Updating the view

When things change in the model, we will simply update the view to reflect the new changes.  Don't forget that your Model implements the Subject interface which gives us the `addView()` method.    

1. Implement the `addView()` method such that it adds it to a list saved as an instance variable.
2. Implement a private method called  `updateViews()` that calls the `update()` method specified by the `Observer` interface on any and all registered Views.
3. Go through the model again.  This time, whenever the game state changes, call `updateViews()` so that those changes appear in the front end.



## Front End

### What You Built

**DO NOT MOVE ON UNTIL YOU ARE FINISHED**

- (Part 1) Model:  
  Develop the core game logic and data structures. This layer will manage the dungeon's layout, rooms, the hero, monsters, items, and other game elements. The model is also responsible for maintaining game state and notifying observers about changes.

### What You Will Build

- Controller (given)
   The controller acts as a mediator between the Front end and the back end. It will handle user input (such as keyboard or mouse events), translate these events into actions in the game, and update the model accordingly. You will add additional user input here.  

- (Part 2) View (JavaFX):
  Create a dynamic user interface with JavaFX that visually represents the game, its inhabitants, and interactions. The view should automatically refresh the display when the game state changes.

  

## Controller

There should be new package in your project called  `controller` at the same level as `model`.

Now that you’ve built a fully functional game backend, it’s time to connect it to the rest of the application. In Part 2, you’ll implement the controller that interprets user actions (like arrow key presses or button clicks) and communicates with the model.

The controller’s job is to act as the **bridge between the view and the model**. It exposes a simplified API of user-facing actions, such as “move left” or “start game,” which the view can call directly. 

You have been given a basic Controller called `ControllerImpl`

This controller will handle user actions and translate them into model updates.  If you would like to add key press events, add them here.  Use the Side Scroller asynch activity as a guide for adding event handlers and threads to controllers.


The `controller` should have one method for every possible action that a user could take in your game.  The View can _read_ from the `model` but it cannot *modify* the Model.  That is the job of the controller.  Take a minute to write down all possible actions, your user could take.  At a bare minimum, it should have

- User can start the game
- User can move the hero up
- User can move the hero left
- User can move the hero right

Your list may grow longer.

Now take a look at the `Controller` interface:

```java
public interface Controller {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void startGame();
}
```
If you are doing a sidescroller, you may remove the `moveDown()` method.  Take a look at the `ControllerImpl` class.

The controller implements `Controller` and encapsulate single instance variable: a reference to the `Model`. This variable should be declared `private final` to ensure it does not change after construction.

#### Public Methods

These methods represent all the actions the user can perform:

- `void moveUp()`
- `void moveDown()`
- `void moveLeft()`
- `void moveRight()`
- `void startGame()`

Each method simply calls the corresponding method in the `model`. These controller methods will later be connected to user interface events like keyboard or button input.

You **should not** add any additional logic to these methods. Their sole purpose is to pass commands from the user interface into the model.

You **should** add a new method that allows you to add event handlers to the scene (if you so choose).  Use the Side Scroller Activity as your guide.

## JavaFX

You are given a few additional starter files to help you develop the view:

- `Main`
- `view.AppLauncher`
- `view.FXComponent`
- `view.View`

---

### Main

First, take a look at the `Main` class:

```java
public class Main {
  public static void main(String[] args) {
    Application.launch(AppLauncher.class);
  }
}
```

This is the standard `main()` method for a JavaFX application. It simply delegates to `Application.launch()`, which starts the JavaFX runtime and hands off control to the `start(Stage stage)` method in `AppLauncher`.

---

### AppLauncher

`AppLauncher` extends the `Application` class, which defines the lifecycle of a JavaFX app. The key method to override is `start(Stage stage)`, which is where you'll build the initial scene and display the user interface.

The `Stage` represents the top-level window in a JavaFX application. It is where scenes are displayed, and it’s what ultimately appears on screen.

The class currently contains a hello world screen:  You'll be adding more setup code here to configure the game window, initialize the model, and load the first view.

---

### FXComponent

`FXComponent` defines a single method, `render()`, which returns a JavaFX `Parent` node. Any class that implements this interface can be rendered onto the stage.

```java
public interface FXComponent {
  /** Render the component and return the resulting Parent object */
  Parent render();
}
```

This interface provides a consistent way to build reusable, modular UI components across your project.

---

### View

The `View` class is currently a skeleton, but it’s where you’ll implement the graphical layout of your game. This class is responsible for rendering the current game state and updating the display when the model changes.

The starter `View` class implements both the `Observer` and `FXComponent` interfaces. `Observer` allows it to display changes from the model, and `FXComponent` allows it to produce JavaFX UI elements via `render()`.

This dual role allows `View` to bridge the model and the GUI — it listens for updates and responds by updating the interface.

To run the starter app, go to the Maven toolbar (right panel) -> Plugins -> `javafx` -> `javafx:run`. You should see a blank white screen pop up.

---

To get your game to display something on the screen, you’ll need to implement the `render()` method in your `View` class to create a visible interface.


### Setting up the JavaFX

You have been given the starter code that hooks everything together.


First things first, set the window title to reflect the name of your game using the `stage.setTitle()` method.





### Creating and Displaying the Scene


At this point, your game window should appear on screen, showing a "Hello, world" label that is implemented in the `render()` method of your `View`.  


##  The User Interface.

By now you have everything you need to build the interface.  From here on out, the instructions will be a little more vague, because you have freedom to style this game according to your own preferences.

You will need at least two more FXViewComponents:
- `TitleScreenView`:  This is your starting screen before beginning a new game, and you will return to it after you die.  Whenever the model shows that the game is in its `GAME_OVER` state, this is the `View` that should render.
- `GameView`: This is your `IN_PROGRESS` `View`.

You may choose to have other UI elements: Some examples:
- a text-based output panel for game events.
- a high score label
- an end game button
- toggle switches for different modes
- etc.

A complete list of JavaFX controls (Look for the `Controls` section) and their associated css tags can be found at:

https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html


### TitleScreenView

TitleScreenView should have the following components:

- NAME OF THE GAME in big bold letters.
- Label that displays the overall Highest Score.
- Label that displays the Last Score.
- Button that will start the game.
- (Optional Bonus Interactions)
- "By [your name]"

#### Required Styling:

- It should be styled in a way that looks like a title Screen, with the title of the game in big (bold) letters, centered horizontally in the window _Hint: remember StackPane_.
- All text should be clearly readable on the background with good contrasting colors.  The text itself should be in a legible font, large enough that the vast majority of people should be able to read it without glasses.
- The button should look like a button.
-
### GameView

GameView should have the following components:

- A `GridPane` representing the game board that renders correctly. _Hint: GridPane lays out items vertically, which means the traditional row/column order is swapped from a 2-D array_
- Control Buttons for the user to control the hero, or instructions on what key presses are available.
- A Label that displays the current score.
- (optional) High score
- (Optional) any other relevant information

#### Required Styling:

This should look like an arcade game.
- Board, there should be no space between the different spaces so that it looks like one cohesive object.
- The control buttons should not be all in one line.  They should be arranged according to their direction with up being above down, and left being to the left of right.  Alternatively, you can implement button presses, in which case you need instructions on what controls are available.  

## Submission Checklist

### Files
Make sure your submission includes:

- [ ] Fully functional `ModelImpl` that implements all methods from `Model`, including game status, score tracking, and movement to pull together what you've done so far into a cohesive backend.
- [ ] `ControllerImpl` that implements all methods from `Controller` and delegates correctly to the model.  Add any additional methods as necessary.
  - [ ] Event handling wired through the controller (not directly in the view)
- [ ] `TitleScreenView` that renders when the model status is `GAME_OVER` and includes:
    - Title of the game
    - Last score and high score labels
    - Start game button
- [ ] `GameView` that renders when the model status is `IN_PROGRESS` and includes:
    - A visible game board using `GridPane`
    - Score label
    - Button controls or key instructions for movement
- [ ] JavaFX styling that reflects your game’s visual theme

### GamePlay
Your game should mostly work by now.  You should have the following features completely implemented:
- [ ] Any required physics should be implemented by the end of this week. 
- [ ] **Hero**: should be able to perform all available actions from the project proposal.
- [ ] **Obstacles**: should be displayed and represent places that the player cannot move.
- [ ] **Treasure**: should be collectable and increase the score.
- [ ] **Exit**: should end the level.
- [ ] [Optional] Enemies to test functionality.

Things your game does not yet implement:

- **Buffs**: Part 4.
- **Enemies**: Part 4.

## Documentation

Update your readme to reflect your current progress.  In particular, update the [Known Issues / Incomplete Features].

- [Known Issues / Incomplete Features]
  If there are any features not yet implemented, partially working, or known bugs that affect gameplay, describe them here. Be honest — noting limitations will not automatically reduce your score, but it helps us understand your current progress and intentions.



## Submitting

Push your changes to the same GitHub repository used for Part 1 and 2. Make sure all new files and updates are committed and pushed. Then, submit your repository to the new Gradescope assignment labeled **Project 3: Gui Design**.

Double-check that:
- Your implementation compiles without errors.
- All required classes and interfaces are present.
- Your code follows the structure and naming conventions described above.

Only one submission is required per group (if working with a partner), but both names must be listed in your `README.md` or project header comment.
