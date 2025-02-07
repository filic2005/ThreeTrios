package cs3500.threetrios;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.controller.HumanPlayer;
import cs3500.threetrios.controller.PlayerColor;
import cs3500.threetrios.model.IThreeTriosModel;
import cs3500.threetrios.view.PlayerView;

/**
 * Mocks the view by appending to a log whenever a method is called by the controller.
 */
public class MockViewForControllerTest extends PlayerView {
  private StringBuilder log;

  /**
   * Constructor for a mock view.
   *
   * @param log the log it appends to
   * @param model the model the constructor needs
   */
  public MockViewForControllerTest(StringBuilder log, IThreeTriosModel model) {
    super(model, new HumanPlayer(PlayerColor.R), new HumanPlayer(PlayerColor.B));
    this.log = log;
  }


  public void addFeaturesListener(Features feature) {
    log.append("addFeaturesListener called\n");
  }

  @Override
  public void error(String errorMessage) {
    log.append("error called with message: ").append(errorMessage).append("\n");
  }

  @Override
  public void setEnabled(boolean enabled) {
    log.append("setEnabled called with enabled=").append(enabled).append("\n");
  }

  @Override
  public void blurScreen(boolean blur) {
    log.append("blurScreen called with blur=").append(blur).append("\n");
  }

  @Override
  public void displayEndGame(String winner) {
    log.append("displayEndGame called with winner=").append(winner).append("\n");
  }

  @Override
  public void refresh() {
    log.append("refresh called\n");
  }

  @Override
  public void resetHighlight() {
    log.append("resetHighlight called\n");
  }
}
