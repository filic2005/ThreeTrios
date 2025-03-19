package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Class that extends ThreeTriosModel and represents the "same" variant.
 */
public class SameModelVariant extends ThreeTriosModel implements IThreeTriosModel {

  /**
   * Constructs a new game of ThreesTrios given files
   * representing the baord and cards to be used.
   * This also instantiates the game board and player's hands (randomly
   * dispersing the card based on a predetermined Random object).
   * @param grid text file to store configuration of the board.
   * @param cards text file to store card information used in the game.
   */
  public SameModelVariant(ArrayList<ArrayList<Cell>> grid,
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
  public SameModelVariant(Random r, ArrayList<ArrayList<Cell>> grid,
                         ArrayList<ICard> cards, IBattleRule battleRule) {
    super(r, grid, cards, battleRule);
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

    ArrayList<CardCell> flippedCards = sameVariant(row, col, grid);

    for (int i = 0; i < flippedCards.size(); i++) {
      battle(flippedCards.get(i).getRow(), flippedCards.get(i).getCol(), grid);
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
   * Returns an ArrayList of CardCells that were turned by the same variant.
   * @param row row of the card placed.
   * @param col col of the card placed.
   * @param gameGrid grid used to reference.
   * @return ArrayList of CardCells
   */
  private ArrayList<CardCell> sameVariant(int row, int col, ArrayList<ArrayList<Cell>> gameGrid) {

    CardCell battler = (CardCell) gameGrid.get(row).get(col);
    ArrayList<CardCell> turnedCells = new ArrayList<>();

    if (row - 1 >= 0 && gameGrid.get(row - 1).get(col) instanceof CardCell) {
      CardCell north = (CardCell) gameGrid.get(row - 1).get(col);
      if (north.getCard() != null && !Objects.equals(north.getCard().getOwner(), getTurn())
              && battler.getCard().getNorth() == north.getCard().getSouth()) {
        turnedCells.add(north);
      }
    }

    if (col + 1 < this.cols && grid.get(row).get(col + 1) instanceof CardCell) {
      CardCell east = (CardCell) gameGrid.get(row).get(col + 1);

      if (east.getCard() != null && !Objects.equals(east.getCard().getOwner(), getTurn())
              && battler.getCard().getEast() == east.getCard().getWest()) {
        turnedCells.add(east);
      }
    }

    if (row + 1 < this.rows && gameGrid.get(row + 1).get(col) instanceof CardCell) {
      CardCell south = (CardCell) gameGrid.get(row + 1).get(col);
      if (south.getCard() != null && !Objects.equals(south.getCard().getOwner(), getTurn())
              && battler.getCard().getSouth() == south.getCard().getNorth()) {
        turnedCells.add(south);
      }
    }

    if (col - 1 >= 0 && gameGrid.get(row).get(col - 1) instanceof CardCell) {
      CardCell west = (CardCell) gameGrid.get(row).get(col - 1);
      if (west.getCard() != null && !Objects.equals(west.getCard().getOwner(), getTurn())
              && battler.getCard().getWest() == west.getCard().getEast()) {
        turnedCells.add(west);
      }
    }

    if (turnedCells.size() >= 2) {
      for (Cell cell : turnedCells) {
        ((CardCell) cell).getCard().setOwner(turn);
      }
    }

    return turnedCells;

  }


}
