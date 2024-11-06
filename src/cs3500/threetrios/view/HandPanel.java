package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

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
    this.setPreferredSize(new Dimension(200, 600)); // Example size
    this.addMouseListener(new MouseEventsListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int cardWidth = 0;
    int cardHeight = 0;

  }

  @Override
  public void highlightCard(int index) {
    this.highlightedIdx = index;
    repaint();
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {

    }
  }
}
