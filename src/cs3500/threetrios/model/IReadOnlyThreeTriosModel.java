package cs3500.threetrios.model;

import java.util.ArrayList;

public interface IReadOnlyThreeTriosModel {
  /**
   * Counts the number of CardCells on the model's grid and
   * alters the gridCount field to reflect that.
   */
  int numCardCellOnBoard();

  /**
   * Returns a String representation of the turn field.
   * @return String, RED if true, BLUE if false.
   */
  String getTurn();

  /**
   * Returns a copy of the grid for observation.
   * @return a 2D ArrayList of Card copy of game grid.
   */
  ArrayList<ArrayList<Cell>> getGrid();

  /**
   * Returns the specified player's hand.
   * @param player String, either RED or BLUE
   * @return an ArrayList of Card of player's hand if valid input is given.
   * @throws IllegalArgumentException if invalid player name is given.
   */
  ArrayList<Card> getPlayerHand(String player);


  /**
   * Determines whether the game is over.
   * @return boolean true if the game is over, false if not.
   */
  boolean isGameOver();

  /**
   * Determines who won the game, if it's over.
   * @return String based on who won the game.
   * @throws IllegalStateException if game isn't over yet.
   */
  String whoWonGame();
}
