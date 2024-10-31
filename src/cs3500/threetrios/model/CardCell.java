package cs3500.threetrios.model;

/**
 * Represents a Card-carrying cell on the gameboard.
 */
public class CardCell implements Cell {

  private Card card; //make Optional? Lets us not use null for empty cells.
  private final int row;
  private final int col;

  /**
   * Constructs a CardCell with the given Card contained and a location.
   * @param card can either be a Card or null.
   * @param row of cell
   * @param col of cell
   */
  CardCell(Card card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  /**
   * Returns a copy of the card in this cell.
   * Will only ever be called internally by the view, so alias is passed
   * instead of a copy.
   * @return Card this Cell's card.
   */
  public Card getCard() {
    if (this.card == null) {
      return null;
    }
    return this.card;
  }

  /**
   * Sets the card field of this cell to the given Card.
   * @param card Card to be put in this cell.
   */
  public void setCard(Card card) {
    this.card = card;
  }


  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }
}
