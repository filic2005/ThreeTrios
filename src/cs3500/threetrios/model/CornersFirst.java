package cs3500.threetrios.model;

import java.util.ArrayList;

public class CornersFirst implements ThreeTriosStrategy {
  @Override
  public RobotMove chooseMove(IReadOnlyThreeTriosModel model, String player) {
    ArrayList<ArrayList<Cell>> grid = model.getGrid();
    ArrayList<Card> cards = model.getPlayerHand(player);
    double maxFlipValue = -1;
    int maxRow = -1;
    int maxCol = -1;
    int maxHandIdx = -1;
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);

      for (int row = 0; row < grid.size(); row++) {
        for (int col = 0; col < grid.get(row).size(); col++) {

          if (grid.get(row).get(col) instanceof CardCell) {

            if ((((CardCell) grid.get(row).get(col)).getCard() == null) && isCornerPiece(grid, row, col)) {
              double flipVal = getFlipVal(card, grid, row, col);
              if (flipVal > maxFlipValue) {
                maxFlipValue = flipVal;
                maxRow = row;
                maxCol = col;
                maxHandIdx = i;
              } else if (flipVal == maxFlipValue) {
                if (row < maxRow) {
                  maxRow = row;
                  maxCol = col;
                  maxHandIdx = i;
                } else if (row == maxRow) {
                  if (col < maxCol) {
                    maxCol = col;
                    maxHandIdx = i;
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
              maxHandIdx = 0;
            }
          }
        }
      }
    }

    return new RobotMove(maxRow, maxCol, maxHandIdx);
  }

  private boolean isCornerPiece(ArrayList<ArrayList<Cell>> grid, int row, int col) {
    if (grid.get(row).get(col) instanceof CardCell) {

      if (row + 1 > grid.size() || (row + 1 < grid.size() && grid.get(row + 1).get(col) instanceof Hole)) {
        if ((col - 1 < 0 || grid.get(row).get(col - 1) instanceof Hole) ||
                (col + 1 > grid.get(row).size()
                        || (col + 1 < grid.get(row).size() && grid.get(row).get(col + 1) instanceof Hole))) {
          return true;
        }
      }

      if (row - 1 < 0 || grid.get(row - 1).get(col) instanceof Hole) {
        if ((col - 1 < 0 || grid.get(row).get(col - 1) instanceof Hole) ||
                (col + 1 > grid.get(row).size()
                        || (col + 1 < grid.get(row).size() && grid.get(row).get(col + 1) instanceof Hole))) {
          return true;
        }
      }
    }
    return false;
  }

  private double getFlipVal(Card card, ArrayList<ArrayList<Cell>> grid, int row, int col) {
    int totalVal = 0;

    if (grid.get(row).get(col) instanceof CardCell) {
      if (row + 1 > grid.size() || (row + 1 < grid.size() && grid.get(row + 1).get(col) instanceof Hole)) {
        if (col - 1 < 0 || grid.get(row).get(col - 1) instanceof Hole) {
          totalVal = card.getEast() + card.getNorth();
        } else if (col + 1 > grid.get(row).size()
                || (col + 1 < grid.get(row).size() && grid.get(row).get(col + 1) instanceof Hole)) {
          totalVal = card.getWest() + card.getNorth();
        }
      }

      if (row - 1 < 0 || grid.get(row - 1).get(col) instanceof Hole) {
        if (col - 1 < 0 || grid.get(row).get(col - 1) instanceof Hole) {
          totalVal = card.getSouth() + card.getEast();
        } else if (col + 1 > grid.get(row).size()
                || (col + 1 < grid.get(row).size() && grid.get(row).get(col + 1) instanceof Hole)) {
          totalVal = card.getWest() + card.getSouth();
        }
      }
    }


    return (double) totalVal / 2;
  }
}
