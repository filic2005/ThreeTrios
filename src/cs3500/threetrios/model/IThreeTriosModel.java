package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * Represents a game of ThreeTrios.
 */
public interface IThreeTriosModel {

  /**
   * Takes this model's list of cards and deals them out to both players.
   */
  void dealToPlayers();

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
   * @return ArrayList<ArrayList<Cell> copy of game grid.
   */
  ArrayList<ArrayList<Cell>> getGrid();


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
   * Returns the specified player's hand.
   * @param player String, either RED or BLUE
   * @return ArrayList<Card> of player's hand if valid input is given.
   * @throws IllegalArgumentException if invalid player name is given.
   */
  ArrayList<Card> getPlayerHand(String player);

  /**
   * Takes in coordinates for a Cell's Card and does battle with the adjacent Cards,
   * changing Card ownership accordingly and calling battle on Cards that got flipped.
   * @param row row of Card doing battle.
   * @param col col of Card doing battle.
   */
  void battle(int row, int col);
}

