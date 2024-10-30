Overview:
- This codebase's purpose is to model and represent a game called ThreeTrios, where
- two players can play against each other.
- This codebase assumes that the user has a basic knowledge of java and has the ability to
- call methods and create class objects from a main method.


Quick start:

@Test
public void testCodeBase() {

    IThreeTriosModel model = new ThreeTriosModel(
                new Random(1),
                "src/cs3500/threetrios/model/configfiles/gridFile1.txt",
                "src/cs3500/threetrios/model/configfiles/cardDB1.txt");

    ThreeTriosView view = new ThreeTriosView(model, new StringBuilder());

    System.out.print(view);

    model.placeCard(0, 0, 0);
}


Key components:
- model: this conducts the actual gameplay, keeping track of the state of the board,
- players' hands, and enforcing the rules of the game.
- view: this component extracts data from the model and displays it in a text-graphic.


Key subcomponents:
- Model:
    - Players - Player class that holds each player's hand.
    - Card - A card playable from a player's hand to the board
    - Cell - Represents a spot on the gameboard that's either a...
        - Hole --> can't be played to
        - CardCell --> can be empty or contain a card
    - Grid - 2D ArrayList of Cells to represent the gameboard
    - Reader - file reading class used to read in grid and card files.
- View
    - Appendable - allows for rendering of the model's toString method


Source organization:
cs3500.threetrios
    README.txt
    model
        configfiles
        Player
        Card
        Cell
        Hole
        CardCell
        Model class
            Grid
        Reader
    view
        View class