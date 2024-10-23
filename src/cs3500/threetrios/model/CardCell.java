package cs3500.threetrios.model;

/**
 * Represents a Card-carrying cell on the gameboard.
 */
public class CardCell implements Cell {

  private CartPt location;
  private Card card;
  private boolean isEmpty;

  CardCell(Card card, CartPt location) {
    this.card = card;
    this.location = location;
    this.isEmpty = this.card == null;
  }

  @Override
  public CartPt getLocation() {
    return this.location;
  }

  public Card getCard() {
    return this.card;
  }

}
