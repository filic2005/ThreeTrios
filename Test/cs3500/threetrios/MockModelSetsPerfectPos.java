package cs3500.threetrios;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.ThreeTriosModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

/**
 * A Mock of a ThreeTriosModel that alters legal positions on the board.
 */
public class MockModelSetsPerfectPos extends ThreeTriosModel {

  private final Map<String, Integer> flipValues; // maps "row,col,card" to flip count

  /**
   * Sets up a MockModelSetsPerfectPos without a given random.
   *
   * @param grid the grid used in the model.
   * @param cards the cards used in the model.
   */
  public MockModelSetsPerfectPos(ArrayList<ArrayList<Cell>> grid, ArrayList<ICard> cards) {
    this(new Random(), grid, cards);
  }

  /**
   * Sets up a MockModelSetsPerfectPos with a given random.
   *
   * @param rand the given random.
   * @param grid the grid used in the model.
   * @param cards the cards used in the model.
   */
  public MockModelSetsPerfectPos(Random rand, ArrayList<ArrayList<Cell>> grid,
                                 ArrayList<ICard> cards) {
    super(rand, grid, cards, new DefaultBattleRule());
    this.flipValues = new HashMap<>();
  }

  /**
   * Adds a certain flip count to a position on the board + hand index.
   *
   * @param row the row of the position on the board.
   * @param col the col of the position on the board.
   * @param card the card that is being played to that position.
   * @param count the number of cards flipped.
   */
  public void setFlipCount(int row, int col, ICard card, int count) {
    flipValues.put(row + "," + col + "," + card.getName(), count);
  }

  @Override
  public int getCardsFlipped(int row, int col, ICard card) {
    return flipValues.getOrDefault(row + "," + col + "," + card.getName(), 0);
  }
}
