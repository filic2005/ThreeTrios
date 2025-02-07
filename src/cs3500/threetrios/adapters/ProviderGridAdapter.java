package cs3500.threetrios.adapters;

import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICardCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.provider.gamecomponents.GridTT;
import cs3500.threetrios.provider.gamecomponents.PlaceHolderTT;
import cs3500.threetrios.provider.gamecomponents.CardTT;
import cs3500.threetrios.provider.gamecomponents.HoleTT;
import cs3500.threetrios.provider.gamecomponents.BoardCell;
import cs3500.threetrios.provider.gamecomponents.Direction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is an adapter for converting our Grid ArrayList into their Grid class.
 */
public class ProviderGridAdapter implements GridTT {

  ArrayList<ArrayList<Cell>> grid;

  /**
   * Initializes the Adapter.
   *
   * @param grid our grid ArrayList.
   */
  public ProviderGridAdapter(ArrayList<ArrayList<Cell>> grid) {
    this.grid = grid;
  }

  @Override
  public CardTT getCard(int row, int col) {
    ICard card = (ICard) grid.get(row).get(col);
    return new ProviderCardAdapter(card);
  }

  @Override
  public List<List<BoardCell>> gridList() {
    ArrayList<List<BoardCell>> boardCellGrid = new ArrayList<>();

    for (int i = 0; i < grid.size(); i++) {
      List<BoardCell> row = new ArrayList<>();
      for (int j = 0; j < grid.get(i).size(); j++) {
        Cell cell = grid.get(i).get(j);
        if (cell instanceof Hole) {
          HoleTT hole = new ProviderHoleAdapter();
          row.add(hole);
        } else if (cell instanceof ICardCell) {
          if (((ICardCell) cell).getCard() == null) {
            CardTT card = new ProviderCardAdapter(((ICardCell) cell).getCard());
            PlaceHolderTT empty = new ProviderPlaceHolderAdapter(card);
            row.add(empty);
          }

          if (((ICardCell) cell).getCard() != null) {
            CardTT card = new ProviderCardAdapter(((ICardCell) cell).getCard());
            row.add(card);
          }
        }

      }
      boardCellGrid.add(row);
    }
    return boardCellGrid;
  }

  @Override
  public void setGrid(int row, int col, CardTT card) {
    //not used
  }

  @Override
  public int numCardCells() {
    return 0;
  }

  @Override
  public boolean checkOutOfBounds(int row, int col) {
    return false;
  }

  @Override
  public void compareAdjacentCells(int row, int col) {
    //not used
  }

  @Override
  public boolean emptyCardCells() {
    return false;
  }

  @Override
  public boolean isCoordinateUnoccupied(int row, int col) {
    return false;
  }

  @Override
  public String toStringForView() {
    return "";
  }

  @Override
  public BoardCell getCell(int row, int col) {
    return null;
  }

  @Override
  public GridTT copy() {
    return null;
  }

  @Override
  public Map<Point, List<Direction>> leastFlippablePositions() {
    return Map.of();
  }
}
