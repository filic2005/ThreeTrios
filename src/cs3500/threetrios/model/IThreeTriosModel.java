package cs3500.threetrios.model;

/**
 * Represents a game of ThreeTrios.
 */
public interface IThreeTriosModel extends IReadOnlyThreeTriosModel {


  /**
   * Takes in a location on the board and places the current player's chosen card there.
   * Then calls battle on that location in order to initiate the battle phase on the
   * surrounding cards.
   * @param row row to place card.
   * @param col col to place card.
   * @param handIdx index of card to be placed in whoever's turn it is hand.
   * @throws IllegalStateException if a Card can't be placed in the chosen spot.
   * @throws IllegalArgumentException if the spot chosen is outside the board.
   */
  void placeCard(int row, int col, int handIdx);
}

