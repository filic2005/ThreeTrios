package cs3500.threetrios.model;

import java.util.ArrayList;

public class BluePlayer {

  private ArrayList<Card> cardList;

  BluePlayer() {
    this.cardList = new ArrayList<Card>();
  }

  public void addCard(Card card) {
    cardList.add(card);
  }

  public void removeCard(Card card) {
    cardList.remove(card);
  }

}
