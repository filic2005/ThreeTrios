package cs3500.threetrios.model;

/**
 * Represents a Card-carrying cell on the gameboard.
 * Top left on the grid is the origin 0,0.
 * Vertical Axis represents columns and Horizontal Axis represents rows.
 * The rows and columns held in this class's fields correspond exactly
 * to the same coordinates on the grid.
 */
public class CardCell implements ICardCell {

  private ICard card; //make Optional? Lets us not use null for empty cells.
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

  @Override
  public ICard getCard() {
    if (this.card == null) {
      return null;
    }
    return this.card;
  }

  @Override
  public void setCard(ICard card) {
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
