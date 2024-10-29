package cs3500.threetrios.model;

/**
 * Represents a cartesian coordinate location.
 */
public class CartPt {

  public int row;
  public int col;

  /**
   * Constructs a point using given coordinates.
   * @param row row of the point, 0-indexed and starting from the top of the grid
   * @param col column of the point, 0-indexed and starting from the left of the grid
   */
  CartPt(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Returns the row of this point.
   * @return int row.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns the column of this point.
   * @return int column.
   */
  public int getCol() {
    return this.col;
  }

}
