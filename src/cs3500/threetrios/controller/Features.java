package cs3500.threetrios.controller;

/**
 * The Features that the controller handles.
 * These methods are called when a player or AI makes a decision
 * about selecting a card and playing it to the board by way of strategy or clicking on the view.
 */
public interface Features {

  /**
   * Takes in hand index selected from view and
   * sets the hand index in the controller to that val.
   */
  void selectCard(int handIdx);

  /**
   * Takes in the spot on the board that was selected and places the saved hand index there.
   * If the hand index wasn't selected first, it tells the player.
   */
  boolean placeCard(int row, int col);

  /**
   * Model tells the controller whose turn it is, and the controller informs the view.
   *
   * @param turn whose turn it is.
   */
  void setTurn(boolean turn);

  /**
   * Model tells the controller the game is over and has it handle it.
   */
  void gameOver();
}
