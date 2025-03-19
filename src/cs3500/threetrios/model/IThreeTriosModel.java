package cs3500.threetrios.model;

import cs3500.threetrios.controller.Features;

/**
 * Represents a game of ThreeTrios.
 */
public interface IThreeTriosModel extends IReadOnlyThreeTriosModel {

  /**
   * Sets initial turn in controller to the correct one for each player.
   */
  void startGame();

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

  /**
   * Adds an observer (the controller) to this model
   * so that the model can communicate with the controller.
   *
   * @param listener the controller being added.
   */
  void addFeaturesListener(Features listener);
}

