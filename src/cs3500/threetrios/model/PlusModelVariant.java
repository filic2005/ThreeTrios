package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Variant rule class for ThreeTrios that can be used with different rule types.
 */
public class PlusModelVariant extends ThreeTriosModel implements IThreeTriosModel {

  /**
   * Constructs a new game of ThreesTrios given files
   * representing the baord and cards to be used.
   * This also instantiates the game board and player's hands (randomly
   * dispersing the card based on a predetermined Random object).
   * @param grid text file to store configuration of the board.
   * @param cards text file to store card information used in the game.
   */
  public PlusModelVariant(ArrayList<ArrayList<Cell>> grid,
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
  public PlusModelVariant(Random r, ArrayList<ArrayList<Cell>> grid,
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

    ArrayList<CardCell> flippedCards = plusVariant(row, col, grid);

    System.out.println("Flipped cards by plus variant: " + flippedCards.size());

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
   * Returns the CardCells whose cards were turned by the plus rule variant.
   * @param row row of the placed card.
   * @param col col of the placed card.
   * @param gameGrid gamegrid to reference.
   * @return arraylist of CardCells.
   */
  private ArrayList<CardCell> plusVariant(int row, int col, ArrayList<ArrayList<Cell>> gameGrid) {

    CardCell battler = (CardCell) gameGrid.get(row).get(col);
    Map<CardCell, Integer> cellSums = new HashMap<CardCell, Integer>();

    if (row - 1 >= 0 && gameGrid.get(row - 1).get(col) instanceof CardCell) {
      CardCell north = (CardCell) gameGrid.get(row - 1).get(col);
      if (north.getCard() != null) {
        cellSums.put(north, north.getCard().getSouth() + battler.getCard().getNorth());
      }
    }

    if (col + 1 < this.cols && grid.get(row).get(col + 1) instanceof CardCell) {
      CardCell east = (CardCell) gameGrid.get(row).get(col + 1);

      if (east.getCard() != null) {
        cellSums.put(east, east.getCard().getWest() + battler.getCard().getEast());
      }
    }

    if (row + 1 < this.rows && gameGrid.get(row + 1).get(col) instanceof CardCell) {
      CardCell south = (CardCell) gameGrid.get(row + 1).get(col);
      if (south.getCard() != null) {
        cellSums.put(south, south.getCard().getNorth() + battler.getCard().getSouth());
      }
    }

    if (col - 1 >= 0 && gameGrid.get(row).get(col - 1) instanceof CardCell) {
      CardCell west = (CardCell) gameGrid.get(row).get(col - 1);
      if (west.getCard() != null) {
        cellSums.put(west,west.getCard().getEast() + battler.getCard().getWest());
      }
    }

    ArrayList<CardCell> turnedCells = findDuplicateValueCards(cellSums);

    for (Cell cell : turnedCells) {
      ((CardCell) cell).getCard().setOwner(turn);
    }

    return turnedCells;
  }

  /**
   * Returns an ArrayList of CardCells that have duplicate sums under the plus rule.
   * @param cardMap map of CardCells and their sums.
   * @return ArrayList of CardCells.
   */
  private ArrayList<CardCell> findDuplicateValueCards(Map<CardCell, Integer> cardMap) {
    Map<Integer, Integer> valueCount = new HashMap<>();
    for (Integer value : cardMap.values()) {
      valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
    }

    Set<Integer> duplicateValues = new HashSet<>();
    for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
      if (entry.getValue() > 1) {
        duplicateValues.add(entry.getKey());
      }
    }

    ArrayList<CardCell> duplicateCards = new ArrayList<>();
    for (Map.Entry<CardCell, Integer> entry : cardMap.entrySet()) {
      if (duplicateValues.contains(entry.getValue())) {
        duplicateCards.add(entry.getKey());
      }
    }

    return duplicateCards;
  }


}
