package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.IReadOnlyThreeTriosModel;
import cs3500.threetrios.controller.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

/**
 * A view representing the swing view for each player of ThreeTrios.
 */
public class PlayerView extends JFrame implements IPlayerView {
  private final BoardPanel boardPanel;
  private final HandPanel leftHandPanel;
  private final JPanel blurOverlay;

  /**
   * Sets up the JFrame for the view.
   * Initializes both players hand panels and the board panel.
   *
   * @param model The model the view is representing.
   */
  public PlayerView(IReadOnlyThreeTriosModel model, Player thisPLayer, Player enemyPlayer) {
    super();
    this.setSize(700,700);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    String title = thisPLayer.getColor().toString();
    if (title.equals("R")) {
      title = "Player RED";
    } else {
      title = "Player BLUE";
    }
    this.setTitle(title);
    this.setLayout(new BorderLayout());
    boardPanel = new BoardPanelHints(model);
    leftHandPanel = new HandPanel(model, thisPLayer);
    EnemyPanel rightHandPanel = new EnemyPanel(model, enemyPlayer);

    this.add(leftHandPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(rightHandPanel, BorderLayout.EAST);

    this.blurOverlay = new BlurOverlay();
    boardPanel.setOpaque(false);
    blurOverlay.setOpaque(false);
    blurOverlay.setVisible(false);
    setGlassPane(blurOverlay);
    this.display(true);
  }

  @Override
  public void selectedCard(ICard card) {
    if (boardPanel instanceof BoardPanelHints) {
      ((BoardPanelHints) boardPanel).setCard(card);
    }
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void addFeaturesListener(Features feature) {
    this.boardPanel.addFeatureListener(feature);
    this.leftHandPanel.addFeatureListener(feature);
  }

  @Override
  public void error(String errorMessage) {
    this.boardPanel.error(errorMessage);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void resetHighlight() {
    this.boardPanel.highlightCell(-1, -1);
    this.leftHandPanel.highlightCard(-1);
  }

  @Override
  public void blurScreen(boolean blur) {
    blurOverlay.setVisible(blur);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    boardPanel.requestFocusInWindow();
  }

  @Override
  public void displayEndGame(String winner) {
    JOptionPane.showMessageDialog(
            null,
            winner
            + "\n Please exit.",
            "GAME OVER",
            JOptionPane.ERROR_MESSAGE
    );
    this.repaint();
  }
}
