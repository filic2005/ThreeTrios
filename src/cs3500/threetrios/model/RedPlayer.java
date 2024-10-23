package cs3500.threetrios.model;

import java.util.ArrayList;

import cs3500.threetrios.model.Card;

public class RedPlayer {

  private ArrayList<Card> cardList;

  RedPlayer() {
    this.cardList = new ArrayList<Card>();
  }

  public void addCard(Card card) {
    cardList.add(card);
  }

  public void removeCard(Card card) {
    cardList.remove(card);
  }

}
