package cs3500.threetrios.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import cs3500.threetrios.model.Card;

public interface IThreeTriosModel {

  void createGrid(String gridFile) throws FileNotFoundException;

  /**
   * Do battle around the card that was placed.
   * @param card
   */
  //void battle(Card card);

  /**
   * Selects a card to be placed.
   * @param row row of the card.
   * @param col col of the card.
   */
  void selectCard(int row, int col);

  /**
   * Places the selected card in a valid location and remove from hand.
   * @param row row to place card.
   * @param col col to place card.
   */
  void placeCard(int row, int col);

  String getTurn();

  ArrayList<ArrayList<Cell>> getGrid();


}

