package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * This class represents a strategy that finds a card in hand and a spot on the board
 * that will flip the most opposing cards.
 */
public class FlipMaxCards implements ThreeTriosStrategy {
  @Override
  public RobotMove chooseMove(IReadOnlyThreeTriosModel model, String player) {
    ArrayList<ArrayList<Cell>> grid = model.getGrid();
    ArrayList<ICard> cards = model.getPlayerHand(player);
    int maxFlipped = -1;
    int maxRow = -1;
    int maxCol = -1;
    int handIdx = -1;
    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      for (int row = 0; row < grid.size(); row++) {
        for (int col = 0; col < grid.get(row).size(); col++) {
          if (grid.get(row).get(col) instanceof CardCell) {
            if (((CardCell) grid.get(row).get(col)).getCard() == null) {
              model.isLegalMove(row, col);
              int flipped = model.getCardsFlipped(row, col, card);
              if (flipped > maxFlipped) {
                maxFlipped = flipped;
                maxRow = row;
                maxCol = col;
                handIdx = i;
              } else if (flipped == maxFlipped) {
                if (row < maxRow) {
                  maxRow = row;
                  maxCol = col;
                  handIdx = i;
                } else if (row == maxRow) {
                  if (col < maxCol) {
                    maxCol = col;
                    handIdx = i;
                  }
                }
              }
            }
          }
        }
      }
    }
    if (maxRow == -1 || maxCol == -1) {
      for (int row = 0; row < grid.size(); row++) {
        for (int col = 0; col < grid.get(row).size(); col++) {
          if (grid.get(row).get(col) instanceof CardCell) {
            if (((CardCell) grid.get(row).get(col)).getCard() == null) {
              maxRow = row;
              maxCol = col;
              handIdx = 0;
            }
          }
        }
      }
    }
    return new RobotMove(maxRow, maxCol, handIdx);
  }
}
