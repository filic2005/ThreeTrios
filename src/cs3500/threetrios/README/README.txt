Overview:
- This codebase's purpose is to model and represent a game called ThreeTrios, where
- two players can play against each other.
- This codebase assumes that the user has a basic knowledge of java and has the ability to
- call methods and create class objects from a main method.


Quick start:

In order to quick start from the command line in the JAR file, pick two of the following in the command line:
- human
- strategy1
- strategy2

example:
java -jar out/artifacts/ThreeTrios_jar/ThreeTrios.jar human strategy1

the test below is for a text view.

@Test
  public void testInitialGameStateView() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    Appendable ap = new StringBuilder();
    ThreeTriosView view = new ThreeTriosView(model, ap);

    try {
      view.render();
    } catch (IOException ignored) {
    }

    String expected = "Player: RED\n"
            + "____\n"
            + "____\n"
            + "____\n"
            + "____\n"
            + "Hand:\n"
            + "Card12 5 6 A 3\n"
            + "Card10 9 4 2 A\n"
            + "Card4 7 A 3 5\n"
            + "Card1 5 3 A 2\n"
            + "Card3 8 1 4 A\n"
            + "Card9 3 A 7 8\n"
            + "Card14 A 3 1 8\n"
            + "Card6 4 8 1 3\n";
    assertEquals(expected, ap.toString());

    model.placeCard(0, 0, 0);
  }


Key components:
- model: this conducts the actual gameplay, keeping track of the state of the board,
- players' hands, and enforcing the rules of the game.
- view: this component extracts data from the model and displays it in either a text graphic
- or a visual GUI.
- controller: this handles actions coming from the view and model and translates it in a way that the other can
- understand, and then sends that information over.


Key subcomponents:
- Model:
    - Players - Player class that holds each player's hand.
    - Card - A card playable from a player's hand to the board
    - Cell - Represents a spot on the gameboard that's either a...
        - Hole --> can't be played to
        - CardCell --> can be empty or contain a card
    - Grid - 2D ArrayList of Cells to represent the gameboard
    - Reader - file reading class used to read in grid and card files.
    - Strategies - Classes used to determine optimal moves in the game.
- View
    - Appendable - allows for rendering of the model's toString method
    - HandPanel - Swing JPanel used to represent each player's hand in the GUI
    - BoardPanel - Swing JPanel used to represent the gameboard in the GUI.
    - BlurOverlay - Swing JPanel that blurs an entire frame
    - EnemyPanel - HandPanel that doesn't register mouse input
    - PlayerView - The GUI view of a specific player of the game
- Controller
    - Features - actions that the controller must translate between the model and view
    - HumanPlayer - represents a Human playing the game
    - MachinePlayer - represents a Machine playing the game using strategies
    - ThreeTriosController - the implementations of the features that control the flow of info between model and view


Source organization:

cs3500.threetrios
    README.txt
    model
        configfiles
        PlayerHand
        Card
        Cell
        Hole
        CardCell
        Model class
        NoHolesBoard (3x3 board text file)
        17Cards (txt file with 17 cards)
        Reader
        StrategyInterface
        CornersFirstStrategy
        FlipMaxCardsStrategy
        RobotMove
    view
        BlurOverlay
        BoardPanel
        IBoardPanel
        EnemyPanel
        HandPanel
        IHandPanel
        TextView
        GUIView
        PlayerView
        GUIViewInterface
    controller
        Features
        HumanPlayer
        MachinePlayer
        PlayerInterface
        PlayerColor
        ThreeTriosController


Changes for HW6 -

- previously, the model lacked the functionality to take in a Card and coordinate and
- produce the number of cards flipped if that Card was placed there. It wasn't there because
- in the last assignment there was no need for strategic information like this.

- a new method was created to produce a full, deep copy of the grid. It wasn't in the previous
- implementation because a shallow copy sufficed for the scenerio.

- a method to compute either players' scores was implemented. As previously stated, strategic or game-level
- information wasn't necessary in the previous assignment, so this method wasn't included before.

- methods to return the dimensions of the grid were implemented. This functionality wasn't previously
- included because the view was able to get the grid's size by calling the getGrid method and using
- the size method.

- both constructors were modified to take as parameters a 2D ArrayList of Cells to represent the grid
- and an ArrayList of Cards to represent the Cards in play. This was done to make sure the model
- had no contact with the configuration files. This was done by performing the conversion outside
- of the model and then passing the resultant data in.

Changes for HW7:

- created a controller package containing the controller interface, controller impl, A player interface,
and two player implementations, one for a human player and one for an AI.

- created a PlayerView implementation that implements the IThreeTriosGUI interface. This view impl represents the frame
that each player sees. It contains both players hands and a board.

- created a EnemyPanel class that doesn't register clicks. This makes it so that a player cant select and play the other
players cards.

- I added observers (feature listeners) to the model and the view so that the controller is notified when player turns
are switched in the model, and what mouse input means for the model when it is detected in the view.

- Created a blur overlay class that blurs out a player's JFrame to indicate it is not their turn, and this blur goes
away when it is their turn.

Changes for HW8:

- created an IPlayerView interface that extends IThreeTriosViewGUI, and made PlayerView implement IPlayerView instead of
IThreeTriosViewGUI. This allows the controller to take in an interface with all the required functionality of PlayerView
instead of directly taking in PlayerView.

-Created Interfaces for Card and CardCell, then made the codebase impl and interfaces use those interfaces instead of
the concrete classes

-Made 7 Adapters for customer code, and made ThreeTriosController implement their controller interface so that it has
their functionality too.

WHAT WORKED AND WHAT DIDNT:

-They only had one feature: handleBoardClick, and we were able to get it to work by calling our methods.
-Nothing didn't work that they sent us, but some of our additional features like blurring the board or displaying an
error message in a pop-up window couldn't be adapted because they didn't have them.


Extra Credit:

-For hints, press "h" on keyboard to show it.
-We implemented all the EC features
-ThreeTrios.java was modified to allow for modification of rules / variants
-at runtime.

Files added:

MODEL:
- IBattleRule
    - DefaultBattleRule
    - FallenAceRule
    - ReverseBattleRule

- PlusModelVariant
- SameModelVariant

VIEW:
- IBoardPanelHints
    - BoardPanelHints