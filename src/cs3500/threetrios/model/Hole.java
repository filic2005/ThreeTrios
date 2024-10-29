package cs3500.threetrios.model;


/**
 * Represents a Hole on the ThreeTrios game board where no Cards can be played.
 */
public class Hole implements Cell {

  private final CartPt location;

  /**
   * Constructs a Hole with a passed location.
   * @param location location of the hole on the board.
   */
  Hole(CartPt location) {
    this.location = location;
  }

  @Override
  public CartPt getLocation() {
    return this.location;
  }

}
