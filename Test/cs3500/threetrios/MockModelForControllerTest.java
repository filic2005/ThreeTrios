package cs3500.threetrios;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.IThreeTriosModel;

import java.util.ArrayList;

/**
 * Mock model that logs all the methods used by controller.
 */
public class MockModelForControllerTest implements IThreeTriosModel {

  private StringBuilder log;

  /**
   * Constructor for a mock model.
   *
   * @param log the log it takes in.
   */
  public MockModelForControllerTest(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addFeaturesListener(Features features) {
    log.append("addFeaturesListener called\n");
  }

  @Override
  public void startGame() {
    //not needed
  }

  @Override
  public void placeCard(int row, int col, int handIdx) {
    log.append("placeCard called with row=")
            .append(row)
            .append(", col=")
            .append(col)
            .append(", handIdx=")
            .append(handIdx)
            .append("\n");
  }

  @Override
  public int numCardCellOnBoard() {
    return 0;
  }

  @Override
  public String getTurn() {
    return "";
  }

  @Override
  public ArrayList<ArrayList<Cell>> getGrid() {
    return null;
  }

  @Override
  public ArrayList<ICard> getPlayerHand(String player) {
    return null;
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver called\n");
    return false;
  }

  @Override
  public int playerOwnedCards(boolean player, ArrayList<ArrayList<Cell>> tempGrid) {
    return 0;
  }

  @Override
  public int getRows() {
    return 0;
  }

  @Override
  public int getCols() {
    return 0;
  }

  @Override
  public int getCardsFlipped(int row, int col, ICard card) {
    return 0;
  }

  @Override
  public void isLegalMove(int row, int col) {
    //return nothing for mock.
  }

  @Override
  public ArrayList<ArrayList<Cell>> getGridCopy() {
    return null;
  }

  @Override
  public String whoWonGame() {
    log.append("whoWonGame called\n");
    return "Player RED";
  }
}
