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


-For hints, press "h" on keyboard to show it. (GUI VIEW)
