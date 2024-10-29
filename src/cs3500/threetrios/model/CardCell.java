package cs3500.threetrios.model;

/**
 * Represents a Card-carrying cell on the gameboard.
 */
public class CardCell implements Cell {

  private final CartPt location;
  private Card card;

  /**
   * Constructs a CardCell with the given Card contained and a location.
   * @param card can either be a Card or null.
   * @param location location of the Cell on the grid.
   */
  CardCell(Card card, CartPt location) {
    this.card = card;
    this.location = location;
  }

  @Override
  public CartPt getLocation() {
    return this.location;
  }

  /**
   * Returns a copy of the card in this cell.
   * @return Card this Cell's card.
   * @throws IllegalStateException if this cell has no card.
   */
  public Card getCard() {
    if (this.card == null) {
      throw new IllegalStateException("No card in this cell!");
    }
    return new Card(this.card.getName(), String.valueOf(this.card.getNorth()),
            String.valueOf(this.card.getSouth()),
            String.valueOf(this.card.getEast()),
            String.valueOf(this.card.getWest()));
  }

  /**
   * Sets the card field of this cell to the given Card.
   * @param card Card to be put in this cell.
   */
  public void setCard(Card card) {
    this.card = card;
  }

}
