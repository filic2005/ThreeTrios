package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * Represents a player in a game of ThreeTrios.
 */
public class PlayerHand implements IPlayerHand {

  private final ArrayList<ICard> cardList;

  /**
   * Constructs a new Player with an empty hand.
   */
  public PlayerHand() {
    this.cardList = new ArrayList<ICard>();
  }

  public void addCard(ICard card) {
    cardList.add(card);
  }

  public void removeCard(ICard card) {
    cardList.remove(card);
  }

  public ArrayList<ICard> getHand() {
    return new ArrayList<>(cardList);
  }

}
