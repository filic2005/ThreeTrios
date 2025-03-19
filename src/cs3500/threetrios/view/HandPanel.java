package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.controller.Player;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * JPanel that represents a players hand in a GUI view.
 */
public class HandPanel extends JPanel implements IHandPanel {

  private final IReadOnlyThreeTriosModel model;
  private final Player player;
  private int highlightedIdx = -1;
  private final List<Features> features;

  /**
   * Constructor for a HandPanel,
   * sets all the info from the model needed and the dimensions of the hand.
   *
   * @param model the model it is representing.
   */
  public HandPanel(IReadOnlyThreeTriosModel model, Player player) {
    this.model = model;
    this.player = player;
    this.features = new ArrayList<Features>();
    this.setPreferredSize(new Dimension(120, 600)); // Example size
    this.addMouseListener(new MouseEventsListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int cardWidth = getWidth();
    int cardHeight = 0;
    if (!model.getPlayerHand(player.getColor().toString()).isEmpty()) {
      cardHeight = getHeight() / model.getPlayerHand(player.getColor().toString()).size();
    }

    for (int card = 0; card < model.getPlayerHand(player.getColor().toString()).size(); card++) {
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
    int cardHeight = getHeight() / model.getPlayerHand(player.getColor().toString()).size();

    ICard card = model.getPlayerHand(player.getColor().toString()).get(index);
    if (player.getColor().toString().equals("R")) {
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

  /**
   * Sets a card to highlighted and repaints when it is called upon.
   * Called when a Card is clicked on.
   *
   * @param index the card index that is highlighted.
   */
  public void highlightCard(int index) {
    this.highlightedIdx = index;
    repaint();
  }

  @Override
  public void addFeatureListener(Features feature) {
    this.features.add(feature);
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      int cellHeight = getHeight() / model.getPlayerHand(player.getColor().toString()).size();
      int row = e.getY() / cellHeight;

      if (row < model.getPlayerHand(player.getColor().toString()).size()) {
        if (model.getPlayerHand(player.getColor().toString()).get(row) != null) {
          highlightCard(row);
          features.get(0).selectCard(row);
          System.out.println("Clicked card num in hand: " + row);
          repaint();
        }
      }
    }
  }
}
