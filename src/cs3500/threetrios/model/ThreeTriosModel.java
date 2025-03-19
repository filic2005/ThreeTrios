package cs3500.threetrios.model;

import cs3500.threetrios.controller.Features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Objects;
import java.util.Collections;

/**
 * Represents the concrete model of a game of ThreeTrios.
 */
public class ThreeTriosModel implements IThreeTriosModel {

  //the outer arraylist represents the left column, inner represents the rows
  //0-indexed
  protected final ArrayList<ArrayList<Cell>> grid;
  protected final int rows;
  protected final int cols;
  protected final ArrayList<ICard> cardList;
  protected final PlayerHand bluePlayer;
  protected final PlayerHand redPlayer;
  protected final int gridCount;
  protected boolean turn; //true is Red, false is Blue
  //INVARIANT: true is ALWAYS Red, false is ALWAYS Blue and this is maintained by our codebase
  protected final List<Features> features;
  protected final IBattleRule battleRule;

  /**
   * Constructs a new game of ThreesTrios given files
   * representing the baord and cards to be used.
   * This also instantiates the game board and player's hands (randomly
   * dispersing the card based on a predetermined Random object).
   * @param grid text file to store configuration of the board.
   * @param cards text file to store card information used in the game.
   */
  public ThreeTriosModel(ArrayList<ArrayList<Cell>> grid,
                         ArrayList<ICard> cards, IBattleRule battleRule) {
    this(new Random(), grid, cards, battleRule);
  }

  /**
   * Constructs a new game of ThreeTrios given a Random object,
   * and files representing the board and cards to be used.
   * This also instantiates the game board and players' hands.
   * @param r Random object to shuffle cards.
   * @param grid text file to store configuration of the board.
   * @param cards text file to store card information used in the game.
   * @throws IllegalArgumentException if config files produce invalid game info.
   * @throws IllegalArgumentException if the passed path names are invalid.
   */
  public ThreeTriosModel(Random r, ArrayList<ArrayList<Cell>> grid,
                         ArrayList<ICard> cards, IBattleRule battleRule) {
    this.bluePlayer = new PlayerHand();
    this.redPlayer = new PlayerHand();
    this.turn = true;
    this.features = new ArrayList<>();
    this.grid = grid;
    this.rows = grid.size();
    this.cols = grid.get(0).size();
    this.cardList = cards;
    Collections.shuffle(cards, r);
    this.gridCount = numCardCellOnBoard();
    this.battleRule = battleRule;
    if (cardList.size() < gridCount + 1) {
      throw new IllegalArgumentException("Invalid config files");
    }
    this.dealToPlayers();
  }

  @Override
  public void addFeaturesListener(Features listener) {
    this.features.add(listener);
  }

  public void startGame() {
    this.features.get(0).setTurn(turn);
    this.features.get(1).setTurn(!turn);
  }

  /**
   * Takes this model's list of cards and deals them out to both players.
   */
  private void dealToPlayers() {
    int cards = (gridCount + 1);
    int playerCards = cards / 2;
    int redCards = playerCards;
    if (cards % 2 != 0) {
      redCards += 1;
    }
    for (int i = 0; i < redCards; i++) {
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
      return "R";
    }
    return "B";
  }

  @Override
  public ArrayList<ArrayList<Cell>> getGrid() {
    return new ArrayList<>(grid);
  }

  @Override
  public ArrayList<ICard> getPlayerHand(String player) {
    if (player.equals("R")) {
      return redPlayer.getHand();
    } else if (player.equals("B")) {
      return bluePlayer.getHand();
    } else {
      throw new IllegalArgumentException("Invalid player name");
    }
  }

  @Override
  public void isLegalMove(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Coordinates are outside of board boundaries!");
    }

    if (grid.get(row).get(col) instanceof CardCell) {
      if (((CardCell) grid.get(row).get(col)).getCard() != null) {
        throw new IllegalStateException("Cell already has a card!");
      }
    } else {
      throw new IllegalStateException("Cannot place a card here!");
    }
  }

  @Override
  public void placeCard(int row, int col, int handIdx) {

    isLegalMove(row, col);

    if (turn) {
      ((CardCell) grid.get(row).get(col)).setCard(redPlayer.getHand().get(handIdx));
      redPlayer.removeCard(redPlayer.getHand().get(handIdx));
    } else {
      ((CardCell) grid.get(row).get(col)).setCard(bluePlayer.getHand().get(handIdx));
      bluePlayer.removeCard(bluePlayer.getHand().get(handIdx));
    }

    battle(row, col, grid);

    turn = !turn;
    if (!features.isEmpty()) {
      this.features.get(0).setTurn(turn);
      this.features.get(1).setTurn(!turn);
    }

    if (!features.isEmpty() && isGameOver()) {
      this.features.get(1).gameOver();
      this.features.get(0).gameOver();
    }
  }

  /**
   * Takes in coordinates for a Cell's Card and does battle with the adjacent Cards,
   * changing Card ownership accordingly and calling battle on Cards that got flipped.
   * @param row row of Card doing battle.
   * @param col col of Card doing battle.
   */
  protected void battle(int row, int col, ArrayList<ArrayList<Cell>> gameGrid) {

    CardCell battler = (CardCell) gameGrid.get(row).get(col);
    ArrayList<Cell> turnedCells = new ArrayList<>();

    if (row - 1 >= 0 && gameGrid.get(row - 1).get(col) instanceof CardCell) {
      CardCell north = (CardCell) gameGrid.get(row - 1).get(col);
      if (north.getCard() != null && !Objects.equals(north.getCard().getOwner(), getTurn())
              && battleRule.shouldFlip(battler.getCard().getNorth(), north.getCard().getSouth())) {
        north.getCard().setOwner(turn);
        turnedCells.add(north);
      }
    }

    if (col + 1 < this.cols && grid.get(row).get(col + 1) instanceof CardCell) {
      CardCell east = (CardCell) gameGrid.get(row).get(col + 1);

      if (east.getCard() != null && !Objects.equals(east.getCard().getOwner(), getTurn())
              && battleRule.shouldFlip(battler.getCard().getEast(), east.getCard().getWest())) {
        east.getCard().setOwner(turn);
        turnedCells.add(east);
      }
    }

    if (row + 1 < this.rows && gameGrid.get(row + 1).get(col) instanceof CardCell) {
      CardCell south = (CardCell) gameGrid.get(row + 1).get(col);
      if (south.getCard() != null && !Objects.equals(south.getCard().getOwner(), getTurn())
              && battleRule.shouldFlip(battler.getCard().getSouth(), south.getCard().getNorth())) {
        south.getCard().setOwner(turn);
        turnedCells.add(south);
      }
    }

    if (col - 1 >= 0 && gameGrid.get(row).get(col - 1) instanceof CardCell) {
      CardCell west = (CardCell) gameGrid.get(row).get(col - 1);
      if (west.getCard() != null && !Objects.equals(west.getCard().getOwner(), getTurn())
              && battleRule.shouldFlip(battler.getCard().getWest(), west.getCard().getEast())) {
        west.getCard().setOwner(turn);
        turnedCells.add(west);
      }
    }

    for (Cell cell : turnedCells) {
      battle(cell.getRow(), cell.getCol(), gameGrid);
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
    int redCount = playerOwnedCards(true, grid);
    int blueCount = playerOwnedCards(false, grid);

    if (redCount > blueCount) {
      return "Player RED Wins.";
    }

    if (redCount < blueCount) {
      return "Player BLUE Wins.";
    }

    return "The game is a TIE.";
  }

  @Override
  public int playerOwnedCards(boolean player, ArrayList<ArrayList<Cell>> tempGrid) {
    int count;
    if (player) {
      count = redPlayer.getHand().size();
    } else {
      count = bluePlayer.getHand().size();
    }

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (tempGrid.get(row).get(col) instanceof CardCell
                && ((CardCell) tempGrid.get(row).get(col)).getCard() != null) {
          if (((CardCell) tempGrid.get(row).get(col)).getCard().getOwner().equals("R") && player) {
            count++;
          }
          if (((CardCell) tempGrid.get(row).get(col)).getCard().getOwner().equals("B") && !player) {
            count++;
          }
        }
      }
    }
    return count;
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public int getCardsFlipped(int row, int col, ICard card) {
    ArrayList<ArrayList<Cell>> gridCopy = this.getGridCopy();
    boolean originalTurn = this.turn;
    int originalCardNum = this.playerOwnedCards(this.turn, grid);

    isLegalMove(row, col);
    ((CardCell) gridCopy.get(row).get(col)).setCard(card);
    battle(row, col, gridCopy);
    return this.playerOwnedCards(originalTurn, gridCopy) - 1 - originalCardNum;
  }

  @Override
  public ArrayList<ArrayList<Cell>> getGridCopy() {
    ArrayList<ArrayList<Cell>> gridCopy = new ArrayList<ArrayList<Cell>>();

    for (int rows = 0; rows < this.grid.size(); rows++) {
      gridCopy.add(new ArrayList<Cell>());
      for (int cols = 0; cols < this.grid.get(0).size(); cols++) {
        if (this.grid.get(rows).get(cols) instanceof CardCell) {
          if (((CardCell)this.grid.get(rows).get(cols)).getCard() != null) {
            ICard temp = ((CardCell)this.grid.get(rows).get(cols)).getCard();
            String north;
            String south;
            String east;
            String west;
            if (temp.getNorth() == 10) {
              north = "A";
            } else {
              north = Integer.toString(temp.getNorth());
            }
            if (temp.getSouth() == 10) {
              south = "A";
            } else {
              south = Integer.toString(temp.getSouth());
            }
            if (temp.getEast() == 10) {
              east = "A";
            } else {
              east = Integer.toString(temp.getEast());
            }
            if (temp.getWest() == 10) {
              west = "A";
            } else {
              west = Integer.toString(temp.getWest());
            }
            CardCell addCell = new CardCell(new Card(temp.getName(), north, south, east, west),
                    rows, cols);
            if (temp.getOwner().equals("R")) {
              addCell.getCard().setOwner(true);
            } else {
              addCell.getCard().setOwner(false);
            }
            gridCopy.get(rows).add(addCell);
          } else {
            gridCopy.get(rows).add(new CardCell(null, rows, cols));
          }
        } else {
          gridCopy.get(rows).add(new Hole(rows, cols));
        }
      }
    }
    return gridCopy;

  }

}
