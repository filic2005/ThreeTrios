package cs3500.threetrios.view;

import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import javax.swing.*;
import java.awt.*;

public class ThreeTriosViewGUI extends JFrame implements IThreeTriosViewGUI {
  private IReadOnlyThreeTriosModel model;
  private HandPanel leftHandPanel;
  private HandPanel rightHandPanel;
  private BoardPanel boardPanel;

  public ThreeTriosViewGUI(IReadOnlyThreeTriosModel model) {
    super();
    this.model = model;
    this.setSize(800,600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    boardPanel = new BoardPanel(model);
    leftHandPanel = new HandPanel(model, "RED");
    rightHandPanel = new HandPanel(model, "BLUE");

    this.add(leftHandPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(rightHandPanel, BorderLayout.EAST);

    this.setVisible(true);
  }


  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
