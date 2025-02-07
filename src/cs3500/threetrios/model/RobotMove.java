package cs3500.threetrios.model;

/**
 * Represents a move made by a strategy.
 * It includes a point on the board, and a hand index
 * for the card to be placed at that point.
 */
public class RobotMove {
  private final int row;
  private final int col;
  private final int handIdx;

  /**
   * Constructor for a strategy's move,
   * sets the point on the board and hand index.
   *
   * @param row the row value on the board.
   * @param col the column value on the board.
   * @param handIdx the index of the card to be placed.
   */
  public RobotMove(int row, int col, int handIdx) {
    this.row = row;
    this.col = col;
    this.handIdx = handIdx;
  }

  /**
   * Gets the moves row.
   *
   * @return the row of the spot on the board that is being played to.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the moves column.
   *
   * @return the column of the spot on the board that is being played to.
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the moves handIdx.
   *
   * @return the index of the card in the hand that is being placed.
   */
  public int getHandIdx() {
    return handIdx;
  }
}

