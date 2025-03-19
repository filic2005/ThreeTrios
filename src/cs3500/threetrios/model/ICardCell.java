package cs3500.threetrios.model;

/**
 * Represents a Card-carrying cell on the gameboard.
 * Top left on the grid is the origin 0,0.
 * Vertical Axis represents columns and Horizontal Axis represents rows.
 * The rows and columns held in this class's fields correspond exactly
 * to the same coordinates on the grid.
 */
public interface ICardCell extends Cell {

  /**
   * Returns a copy of the card in this cell.
   * Will only ever be called internally by the view, so alias is passed
   * instead of a copy.
   * @return Card this Cell's card.
   */
  ICard getCard();

  /**
   * Sets the card field of this cell to the given Card.
   * @param card Card to be put in this cell.
   */
  void setCard(ICard card);

}
