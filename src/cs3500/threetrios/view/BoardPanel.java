package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ICardCell;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * JPanel that represents the game board in a GUI view.
 */
public class BoardPanel extends JPanel implements IBoardPanel {

  protected final int rows;
  protected final int cols;
  private int highlightedRow = -1;
  private int highlightedCol = -1;
  private final ArrayList<ArrayList<Cell>> modelGrid;
  protected List<Features> features;

  /**
   * Constructor for a BoardPanel, sets all the info from the model needed.
   *
   * @param model the model it is representing.
   */
  public BoardPanel(IReadOnlyThreeTriosModel model) {
    this.rows = model.getRows();
    this.cols = model.getCols();
    this.modelGrid = model.getGrid();
    this.addMouseListener(new MouseEventsListener());
    this.features = new ArrayList<>();
  }

  public void refresh() {
    this.repaint();
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


        if (modelGrid.get(row).get(col) instanceof ICardCell) {
          if (((ICardCell) modelGrid.get(row).get(col)).getCard() == null) {
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

    ICard card = ((ICardCell) modelGrid.get(row).get(col)).getCard();
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

    String north = String.valueOf(card.getNorth());
    if (north.equals("10")) {
      north = "A";
    }
    String south = String.valueOf(card.getSouth());
    if (south.equals("10")) {
      south = "A";
    }
    String west = String.valueOf(card.getWest());
    if (west.equals("10")) {
      west = "A";
    }
    String east = String.valueOf(card.getEast());
    if (east.equals("10")) {
      east = "A";
    }

    g2.setColor(Color.BLACK);
    g2.drawString(north, northX, northY);
    g2.drawString(south, southX, southY);
    g2.drawString(west, westX, westY);
    g2.drawString(east, eastX, eastY);
  }

  @Override
  public void highlightCell(int row, int col) {
    this.highlightedRow = row;
    this.highlightedCol = col;
    repaint();
  }

  /**
   * Restarts the sequence if the color was guessed incorrectly.
   * Also informs the player.
   */
  @Override
  public void error(String errorMessage) {
    JOptionPane.showMessageDialog(
            null,
            errorMessage,
            "Error",
            JOptionPane.ERROR_MESSAGE
    );
    this.repaint();
  }

  @Override
  public void addFeatureListener(Features feature) {
    this.features.add(feature);
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      int cellWidth = getWidth() / cols;
      int cellHeight = getHeight() / rows;

      int col = e.getX() / cellWidth;
      int row = e.getY() / cellHeight;

      if (row < rows && col < cols) {
        if (modelGrid.get(row).get(col) instanceof ICardCell) {
          if (features.get(0).placeCard(row, col)) {
            highlightCell(row, col);
            System.out.println("Clicked cell at (" + row + ", " + col + ")");
            repaint();
          }
        }
      }
    }
  }
}


