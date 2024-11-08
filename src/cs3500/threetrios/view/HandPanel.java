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
public class HandPanel extends JPanel implements IHandPanel {

  private final IReadOnlyThreeTriosModel model;
  private final String player;
  private int highlightedIdx = -1;

  /**
   *
   * @param model
   */
  public HandPanel(IReadOnlyThreeTriosModel model, String player) {
    this.model = model;
    this.player = player;
    this.setPreferredSize(new Dimension(150, 600)); // Example size
    this.addMouseListener(new MouseEventsListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int cardWidth = getWidth();
    int cardHeight = getHeight() / model.getPlayerHand(player).size();

    for (int card = 0; card < model.getPlayerHand(player).size(); card++) {
      int x = 0;
      int y = card * cardHeight;

      drawCard(g2, card, x, y);

      // Highlight the cell if it's selected
      if (card == highlightedIdx) {
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(x, y, cardWidth, cardHeight);
      } else {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x, y, cardWidth, cardHeight);
      }
    }
  }

  private void drawCard(Graphics2D g2, int index, int x, int y) {
    int cardWidth = getWidth();
    int cardHeight = getHeight() / model.getPlayerHand(player).size();

    Card card = model.getPlayerHand(player).get(index);
    if (player.equals("RED")) {
      g2.setColor(Color.RED);
    } else {
      g2.setColor(Color.BLUE);
    }
    g2.setStroke(new BasicStroke(4));
    g2.fillRect(x, y, cardWidth, cardHeight);

    int northX = x + cardWidth / 2 - 10;
    int northY = y + 20;
    int southX = x + cardWidth / 2 - 10;
    int southY = y + cardHeight - 5;
    int westX = x + 5;
    int westY = y + cardHeight / 2 + 5;
    int eastX = x + cardWidth - 25;
    int eastY = y + cardHeight / 2 + 5;

    g2.setColor(Color.BLACK);
    g2.drawString(String.valueOf(card.getNorth()), northX, northY);
    g2.drawString(String.valueOf(card.getSouth()), southX, southY);
    g2.drawString(String.valueOf(card.getWest()), westX, westY);
    g2.drawString(String.valueOf(card.getEast()), eastX, eastY);
  }

  @Override
  public void highlightCard(int index) {
    this.highlightedIdx = index;
    repaint();
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      int cellHeight = getHeight() / model.getPlayerHand(player).size();
      int row = e.getY() / cellHeight;

      if (row < model.getPlayerHand(player).size()) {
        if (model.getPlayerHand(player).get(row) != null) {
          highlightedIdx = row;
          System.out.println("Clicked card num in hand: " + row);
          repaint();
        }
      }
    }
  }
}
