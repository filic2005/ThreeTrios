package cs3500.threetrios.model;

import java.util.ArrayList;


/**
 * Represents a player in a game of ThreeTrios.
 * The player holds a hand of ICards.
 */
public interface IPlayerHand {

  /**
   * Adds a Card to this player's hand.
   * @param card Card to be added.
   */
  public void addCard(ICard card);

  /**
   * Removes a Card from this player's hand.
   * @param card Card to be removed.
   */
  public void removeCard(ICard card);

  /**
   * Returns a copy of this player's hand.
   * @return ArrayList of Cards in hand
   */
  public ArrayList<ICard> getHand();

}
