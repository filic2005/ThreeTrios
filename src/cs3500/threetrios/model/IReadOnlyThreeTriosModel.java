package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * Interface representing a ThreeTrios model that can only be read from, and not altered.
 */
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
  ArrayList<ICard> getPlayerHand(String player);



  /**
   * Determines whether the game is over.
   * @return boolean true if the game is over, false if not.
   */
  boolean isGameOver();

  /**
   * Returns all cards owned by a player.
   *
   * @param player the player that's cards are being listed.
   * @param tempGrid the current game grid that is being checked for a player's cards.
   * @return all the player's owned cards in the game.
   */
  int playerOwnedCards(boolean player, ArrayList<ArrayList<Cell>> tempGrid);

  /**
   * Gets the number of rows in the grid.
   *
   * @return the number of rows.
   */
  int getRows();

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns.
   */
  int getCols();

  /**
   * Gets the number of cards that are flipped when a certain card is played to a certain spot.
   *
   * @param row the row of the spot being played to.
   * @param col the column of the spot being plaued to.
   * @param card the card being played to that spot.
   * @return the number of cards flipped to the owner of the card that was played.
   */
  int getCardsFlipped(int row, int col, ICard card);

  /**
   * Checks if a certain move is legal on the board.
   *
   * @param row the row of that move.
   * @param col the column of that move.
   */
  void isLegalMove(int row, int col);

  /**
   * Gets a copy of the grid.
   * Outer ArrayList is the Columns, Inner is the rows.
   *
   * @return a copy of the grid.
   */
  ArrayList<ArrayList<Cell>> getGridCopy();

  /**
   * Determines who won the game, if it's over.
   * @return String based on who won the game.
   * @throws IllegalStateException if game isn't over yet.
   */
  String whoWonGame();
}
