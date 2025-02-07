package cs3500.threetrios.model;

import java.util.ArrayList;

/**
 * This class represents a strategy that makes the
 * best play with a card to a corner on the board.
 * The best play means playing to an available corner on the board, where the exposed values
 * of the card are the highest, making them harder to flip.
 */
public class CornersFirst implements ThreeTriosStrategy {
  @Override
  public RobotMove chooseMove(IReadOnlyThreeTriosModel model, String player) {
    ArrayList<ArrayList<Cell>> grid = model.getGrid();
    ArrayList<ICard> cards = model.getPlayerHand(player);
    double maxFlipValue = -1;
    int maxRow = -1;
    int maxCol = -1;
    int maxHandIdx = -1;
    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      for (int row = 0; row < grid.size(); row++) {
        for (int col = 0; col < grid.get(row).size(); col++) {
          if (grid.get(row).get(col) instanceof CardCell) {
            if ((((CardCell) grid.get(row).get(col)).getCard() == null)
                    && isCornerPiece(grid, row, col)) {
              model.isLegalMove(row, col);
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
    if (!(grid.get(row).get(col) instanceof CardCell)) {
      return false;
    }
    int numRows = grid.size();
    int numCols = grid.get(row).size();
    return (isBlocked(row - 1, col, grid, numRows, numCols)
            && isBlocked(row, col - 1, grid, numRows, numCols))
            || (isBlocked(row - 1, col, grid, numRows, numCols)
                    && isBlocked(row, col + 1, grid, numRows, numCols))
            || (isBlocked(row + 1, col, grid, numRows, numCols)
                    && isBlocked(row, col - 1, grid, numRows, numCols))
            || (isBlocked(row + 1, col, grid, numRows, numCols)
                    && isBlocked(row, col + 1, grid, numRows, numCols))
            || (isBlocked(row - 1, col, grid, numRows, numCols)
                    && isBlocked(row + 1, col, grid, numRows, numCols))
            || (isBlocked(row, col - 1, grid, numRows, numCols)
                    && isBlocked(row, col + 1, grid, numRows, numCols));
  }

  private boolean isBlocked(int r, int c, ArrayList<ArrayList<Cell>> grid,
                            int numRows, int numCols) {
    return r < 0 || r >= numRows || c < 0 || c >= numCols || grid.get(r).get(c) instanceof Hole;
  }

  private double getFlipVal(ICard card, ArrayList<ArrayList<Cell>> grid, int row, int col) {
    int totalVal = 0;

    int numRows = grid.size();
    int numCols = grid.get(row).size();

    if (grid.get(row).get(col) instanceof CardCell) {
      if (isBlocked(row - 1, col, grid, numRows, numCols)
              && isBlocked(row, col - 1, grid, numRows, numCols)) {
        totalVal += card.getSouth() + card.getEast();
      }
      else if (isBlocked(row - 1, col, grid, numRows, numCols)
              && isBlocked(row, col + 1, grid, numRows, numCols)) {
        totalVal += card.getSouth() + card.getWest();
      }
      else if (isBlocked(row + 1, col, grid, numRows, numCols)
              && isBlocked(row, col - 1, grid, numRows, numCols)) {
        totalVal += card.getNorth() + card.getEast();
      }
      else if (isBlocked(row + 1, col, grid, numRows, numCols)
              && isBlocked(row, col + 1, grid, numRows, numCols)) {
        totalVal += card.getNorth() + card.getWest();
      }
      else if (isBlocked(row - 1, col, grid, numRows, numCols)
              && isBlocked(row + 1, col, grid, numRows, numCols)) {
        totalVal += card.getWest() + card.getEast();
      }
      else if (isBlocked(row, col - 1, grid, numRows, numCols)
              && isBlocked(row, col + 1, grid, numRows, numCols)) {
        totalVal += card.getNorth() + card.getSouth();
      }
    }
    return (double) totalVal / 2;
  }
}
