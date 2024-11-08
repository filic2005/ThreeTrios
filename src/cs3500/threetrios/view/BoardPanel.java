package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;
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
  private final ArrayList<ArrayList<Cell>> modelGrid;

  /**
   * @param model
   */
  public BoardPanel(IReadOnlyThreeTriosModel model) {
    this.model = model;
    this.rows = model.getRows();
    this.cols = model.getCols();
    this.modelGrid = model.getGrid();
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


        if (modelGrid.get(row).get(col) instanceof CardCell) {
          if (((CardCell) modelGrid.get(row).get(col)).getCard() == null) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(4));
            g2.fillRect(x, y, cellWidth, cellHeight);
          } else {
            drawCard(g2, row, col, x, y);
          }
        } else {
          g2.setColor(Color.GRAY);
          g2.setStroke(new BasicStroke(4));
          g2.fillRect(x, y, cellWidth, cellHeight);
        }

        // Highlight the cell if it's selected
        if (row == highlightedRow && col == highlightedCol) {
          g2.setColor(Color.GREEN);
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

  private void drawCard(Graphics2D g2, int row, int col, int x, int y) {
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    Card card = ((CardCell) modelGrid.get(row).get(col)).getCard();
    if (card.getOwner().equals("R")) {
      g2.setColor(Color.RED);
    } else {
      g2.setColor(Color.BLUE);
    }
    g2.setStroke(new BasicStroke(4));
    g2.fillRect(x, y, cellWidth, cellHeight);

    int northX = x + cellWidth / 2 - 10;
    int northY = y + 20;
    int southX = x + cellWidth / 2 - 10;
    int southY = y + cellHeight - 5;
    int westX = x + 5;
    int westY = y + cellHeight / 2 + 5;
    int eastX = x + cellWidth - 25;
    int eastY = y + cellHeight / 2 + 5;

    g2.setColor(Color.BLACK);
    g2.drawString(String.valueOf(card.getNorth()), northX, northY);
    g2.drawString(String.valueOf(card.getSouth()), southX, southY);
    g2.drawString(String.valueOf(card.getWest()), westX, westY);
    g2.drawString(String.valueOf(card.getEast()), eastX, eastY);
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
        if (modelGrid.get(row).get(col) instanceof CardCell) {
          highlightedRow = row;
          highlightedCol = col;
          System.out.println("Clicked cell at (" + row + ", " + col + ")");
          repaint();
        }
      }
    }
  }
}


