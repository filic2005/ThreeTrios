package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.controller.Player;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Represents a GUI view for the game of ThreeTrios.
 */
public class ThreeTriosViewGUI extends JFrame implements IThreeTriosViewGUI {
  private BoardPanel boardPanel;
  private HandPanel leftHandPanel;
  private HandPanel rightHandPanel;

  /**
   * Sets up the JFrame for the view.
   * Initializes both plauer hand panels and the board panel.
   *
   * @param model The model the view is representing.
   */
  public ThreeTriosViewGUI(IReadOnlyThreeTriosModel model, Player player1, Player player2) {
    super();
    this.setSize(800,600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    BoardPanel boardPanel = new BoardPanel(model);
    HandPanel leftHandPanel = new HandPanel(model, player1);
    HandPanel rightHandPanel = new HandPanel(model, player2);

    this.add(leftHandPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(rightHandPanel, BorderLayout.EAST);

    this.display(true);
  }


  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void addFeaturesListener(Features feature) {
    this.boardPanel.addFeatureListener(feature);
    this.leftHandPanel.addFeatureListener(feature);
    this.rightHandPanel.addFeatureListener(feature);
  }
}
