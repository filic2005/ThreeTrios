package cs3500.threetrios.model;

import cs3500.threetrios.model.CartPt;
import cs3500.threetrios.model.Cell;

public class Hole implements Cell {

  private CartPt location;

  Hole(CartPt location) {
    this.location = location;
  }

  @Override
  public CartPt getLocation() {
    return this.location;
  }

}
