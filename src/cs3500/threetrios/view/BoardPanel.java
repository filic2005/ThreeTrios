package cs3500.threetrios.view;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 */
public class BoardPanel extends JPanel implements IBoardPanel {

  private final IReadOnlyThreeTriosModel model;
  private final int rows;
  private final int cols;
  private int highlightedRow = -1;
  private int highlightedCol = -1;

  /**
   * @param model
   */
  public BoardPanel(IReadOnlyThreeTriosModel model) {
    this.model = model;
    this.rows = model.getRows();
    this.cols = model.getCols();
    this.addMouseListener(new MouseEventsListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int x = col * cellWidth;
        int y = row * cellHeight;

        // Highlight the cell if it's selected
        if (row == highlightedRow && col == highlightedCol) {
          g2.setColor(Color.YELLOW);
          g2.setStroke(new BasicStroke(4));
          g2.drawRect(x, y, cellWidth, cellHeight);
        } else {
          g2.setColor(Color.BLACK);
          g2.setStroke(new BasicStroke(1));
          g2.drawRect(x, y, cellWidth, cellHeight);
        }
      }
    }
  }

  @Override
  public void highlightCell(int row, int col) {
    this.highlightedRow = row;
    this.highlightedCol = col;
    repaint();
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      int cellWidth = getWidth() / cols;
      int cellHeight = getHeight() / rows;

      int col = e.getX() / cellWidth;
      int row = e.getY() / cellHeight;

      if (row < rows && col < cols) {
        ArrayList<ArrayList<Cell>> grid = model.getGrid();
        if (grid.get(row).get(col) instanceof CardCell) {
          highlightedRow = row;
          highlightedCol = col;
          System.out.println("Clicked cell at (" + row + ", " + col + ")");
          repaint();
        }
      }
    }
  }
}


