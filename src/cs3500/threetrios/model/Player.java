package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * Represents a player in a game of ThreeTrios.
 */
public class Player {

  private final ArrayList<Card> cardList;
  private final PlayerColor color;

  /**
   * Constructs a new Player that can either be RED or BLUE.
   * @param color Color of this player, RED or BLUE.
   */
  Player(PlayerColor color) {
    this.cardList = new ArrayList<>();
    this.color = color;
  }

  /**
   * Adds a Card to this player's hand.
   * @param card Card to be added.
   */
  public void addCard(Card card) {
    cardList.add(card);
  }

  /**
   * Removes a Card from this player's hand.
   * @param card Card to be removed.
   */
  public void removeCard(Card card) {
    cardList.remove(card);
  }

  /**
   * Returns a copy of this player's hand.
   * @return ArrayList of Cards in hand
   */
  public ArrayList<Card> getHand() {
    return new ArrayList<>(cardList);
  }


}
