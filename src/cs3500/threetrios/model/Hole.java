package cs3500.threetrios.model;


/**
 * Represents a Hole on the ThreeTrios game board where no Cards can be played.
 */
public class Hole implements Cell {


  int row;
  int col;

  /**
   * Constructs a Hole with a passed location.
   */
  Hole(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }
}
