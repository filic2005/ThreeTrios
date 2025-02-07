package cs3500.threetrios;

import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Cell;


import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/**
 * Represents a mock of a ThreeTriosModel that keeps track of all cells checked by the strategy.
 */
public class MockModelAllPositions extends ThreeTriosModel {
  private final List<int[]> checkedCoordinates = new ArrayList<>();


  /**
   * Initializes a MockModelAllPositions without a given random.
   *
   * @param grid the grid for the model.
   * @param cards the cards used in the model.
   */
  public MockModelAllPositions(ArrayList<ArrayList<Cell>> grid, ArrayList<ICard> cards) {
    this(new Random(), grid, cards);
  }

  /**
   * Initializes a MockModelAllPositions with a given random.
   *
   * @param random the given random
   * @param grid the grid for the model.
   * @param cards the cards used in the model.
   */
  public MockModelAllPositions(Random random, ArrayList<ArrayList<Cell>> grid,
                               ArrayList<ICard> cards) {
    super(random, grid, cards, new DefaultBattleRule());
  }

  /**
   * Records coordinates checked for move legality.
   * This helps to verify if strategies are checking all necessary cells.
   */
  @Override
  public void isLegalMove(int row, int col) {
    checkedCoordinates.add(new int[]{row, col});
  }

  /**
   * Returns checkedCoordinates as a String to prevent memory issues.
   *
   * @return the List of Strings representing checked coords.
   */
  public List<String> getCheckedCoordinates() {
    List<String> readableCoordinates = new ArrayList<>();
    for (int[] coord : checkedCoordinates) {
      readableCoordinates.add(Arrays.toString(coord));  // Convert int[] to a readable string
    }
    return readableCoordinates;
  }
}


