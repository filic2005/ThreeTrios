package cs3500.threetrios.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the concrete model of a game of ThreeTrios
 */
public class ThreeTriosModel implements IThreeTriosModel {

  //the outer arraylist represents the left column, inner represents the rows
  //0-indexed
  private final ArrayList<ArrayList<Cell>> grid;
  private final int rows;
  private final int cols;
  private ArrayList<Card> cardList;
  private final Player bluePlayer;
  private final Player redPlayer;
  private final int gridCount;
  private boolean turn; //true is Red, false is Blue

  /**
   * Constructs a new game of ThreesTrios given files
   * representing the baord and cards to be used.
   * This also instantiates the game board and player's hands (randomly
   * dispersing the card based on a predetermined Random object).
   * @param gridFile text file to store configuration of the board.
   * @param cardDB text file to store card information used in the game.
   */
  public ThreeTriosModel(String gridFile, String cardDB) {
    this(new Random(), gridFile, cardDB);
  }

  /**
   * Constructs a new game of ThreeTrios given a Random object,
   * and files representing the board and cards to be used.
   * This also instantiates the game board and players' hands.
   * @param r Random object to shuffle cards.
   * @param gridFile text file to store configuration of the board.
   * @param cardDB text file to store card information used in the game.
   * @throws IllegalArgumentException if config files produce invalid game info.
   * @throws IllegalArgumentException if the passed path names are invalid.
   */
  public ThreeTriosModel(Random r, String gridFile, String cardDB) {
    cardList = new ArrayList<>();
    this.bluePlayer = new Player(PlayerColor.BLUE);
    this.redPlayer = new Player(PlayerColor.RED);
    this.turn = true;

    try {
      this.grid = new Reader().createGrid(gridFile);
      this.rows = grid.size();
      this.cols = grid.get(0).size();
      this.cardList = new Reader().createHands(cardDB, r);
      this.gridCount = numCardCellOnBoard();
      if (cardList.size() < gridCount + 1) {
        throw new IllegalArgumentException("Invalid config files");
      }
      this.dealToPlayers();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid path name");
    }
  }

  @Override
  public void dealToPlayers() {
    int playerCards = (gridCount + 1) /2;
    for (int i = 0; i < playerCards; i++) {
      cardList.get(0).setOwner(true);
      redPlayer.addCard(cardList.remove(0));
    }
    for (int i = 0; i < playerCards; i++) {
      cardList.get(0).setOwner(false);
      bluePlayer.addCard(cardList.remove(0));
    }
  }

  @Override
  public int numCardCellOnBoard() {
    int gridCount = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid.get(i).get(j) instanceof CardCell) {
          gridCount++;
        }
      }
    }
    return gridCount;
  }

  @Override
  public String getTurn() {
    if (turn) {
      return "RED";
    }
    return "BLUE";
  }

  @Override
  public ArrayList<ArrayList<Cell>> getGrid() {
    return new ArrayList<>(grid);
  }

  @Override
  public ArrayList<Card> getPlayerHand(String player) {
    if (player.equals("RED")) {
      return redPlayer.getHand();
    } else if (player.equals("BLUE")) {
      return bluePlayer.getHand();
    } else {
      throw new IllegalArgumentException("Invalid player name");
    }
  }

  @Override
  public void placeCard(int row, int col, int handIdx) {

    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Coordinates are outside of board boundaries!");
    }

    if (grid.get(row).get(col) instanceof CardCell) {

      if (((CardCell) grid.get(row).get(col)).getCard() != null) {
        throw new IllegalStateException("Cell already has a card!");
      }

      if (turn) {
        ((CardCell) grid.get(row).get(col)).setCard(redPlayer.getHand().get(handIdx));
        redPlayer.removeCard(redPlayer.getHand().get(handIdx));
      } else {
        ((CardCell) grid.get(row).get(col)).setCard(bluePlayer.getHand().get(handIdx));
        bluePlayer.removeCard(bluePlayer.getHand().get(handIdx));
      }
    } else {
      throw new IllegalStateException("Cannot place a card here!");
    }

    battle(row, col);

    turn = !turn;
  }

  @Override
  public void battle(int row, int col) {

    CardCell battler = (CardCell) grid.get(row).get(col);
    ArrayList<Cell> turnedCells = new ArrayList<>();

    if (row - 1 >= 0 && grid.get(row - 1).get(col) instanceof CardCell) {
      CardCell north = (CardCell) grid.get(row - 1).get(col);
      if (north.getCard() != null && north.getCard().getSouth() < battler.getCard().getNorth()) {
        north.getCard().setOwner(turn);
        turnedCells.add(north);
      }
    }

    if (col + 1 < this.cols && grid.get(row).get(col + 1) instanceof CardCell) {
      CardCell east = (CardCell) grid.get(row).get(col + 1);

      if (east.getCard() != null && east.getCard().getWest() < battler.getCard().getEast()) {
        east.getCard().setOwner(turn);
        turnedCells.add(east);
      }
    }

    if (row + 1 < this.rows && grid.get(row + 1).get(col) instanceof CardCell) {
      CardCell south = (CardCell) grid.get(row + 1).get(col);
      if (south.getCard() != null &&  south.getCard().getNorth() < battler.getCard().getSouth()) {
        south.getCard().setOwner(turn);
        turnedCells.add(south);
      }
    }

    if (col - 1 >= 0 && grid.get(row).get(col - 1) instanceof CardCell) {
      CardCell west = (CardCell) grid.get(row).get(col - 1);
      if (west.getCard() != null && west.getCard().getEast() < battler.getCard().getWest()) {
        west.getCard().setOwner(turn);
        turnedCells.add(west);
      }
    }

    for (Cell cell : turnedCells) {
      battle(cell.getRow(), cell.getCol());
    }

  }


  @Override
  public boolean isGameOver() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid.get(row).get(col) instanceof CardCell) {
          if (((CardCell) grid.get(row).get(col)).getCard() == null) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public String whoWonGame() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over!");
    }

    int redCount = redPlayer.getHand().size();
    int blueCount = bluePlayer.getHand().size();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid.get(row).get(col) instanceof CardCell) {
          if (((CardCell) grid.get(row).get(col)).getCard().getOwner().equals("R")) {
            redCount++;
          } else {
            blueCount++;
          }
        }
      }
    }

    if (redCount > blueCount) {
      return "Player RED wins.";
    }

    if (blueCount > redCount) {
      return "Player BLUE wins.";
    }

    return "The game is a TIE.";
  }

}
