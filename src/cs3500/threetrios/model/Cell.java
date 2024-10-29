package cs3500.threetrios.model;

import cs3500.threetrios.model.CartPt;

/**
 * Represents a Cell that can be either a CardCell or a Hole.
 */
public interface Cell {

  /**
   * Returns the location of this Cell on the board.
   * @return CartPt a point on the board.
   */
  CartPt getLocation();


}
