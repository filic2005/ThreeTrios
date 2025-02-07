package cs3500.threetrios.view;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.ICardCell;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * An implementation of a BoardPanel that gives card placement hints.
 */
public class BoardPanelHints extends BoardPanel implements IBoardPanelHints {

  private boolean toggleHint;
  private final IReadOnlyThreeTriosModel model;
  private ICard card;

  /**
   * Constructor for a BoardPanel, sets all the info from the model needed.
   *
   * @param model the model it is representing.
   */
  public BoardPanelHints(IReadOnlyThreeTriosModel model) {
    super(model);
    this.model = model;
    this.addKeyListener(new KeyPressListener());
    this.setFocusable(true);
    toggleHint = false;
  }

  @Override
  public void setCard(ICard card) {
    this.card = card;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (toggleHint && card != null) {
      Graphics2D g2d = (Graphics2D) g;
      int cellWidth = getWidth() / cols;
      int cellHeight = getHeight() / rows;

      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          Cell spot = model.getGrid().get(row).get(col);
          // Calculate flips if the selected card is placed here
          if (spot instanceof ICardCell && ((ICardCell) spot).getCard() == null) {
            int flips = model.getCardsFlipped(row, col, card);
            int x = col * cellWidth + 5;
            int y = (row + 1) * cellHeight - 5;
            g2d.setColor(Color.RED);
            g2d.drawString(String.valueOf(flips), x, y); // Display number of flips
          }
        }
      }
    }
  }

  private class KeyPressListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_H) {
        toggleHint = !toggleHint;
        repaint();
      }
    }
  }
}
