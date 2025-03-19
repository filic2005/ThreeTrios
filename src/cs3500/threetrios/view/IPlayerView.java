package cs3500.threetrios.view;


import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.ICard;

/**
 * An interface for a view of a Player.
 */
public interface IPlayerView extends IThreeTriosViewGUI {

  /**
   * Displays the view.
   *
   * @param show represents whether the view is meant to be displayed.
   */
  void display(boolean show);

  /**
   * Adds the controller as a feature listener.
   *
   * @param feature the observer (controller)
   */
  void addFeaturesListener(Features feature);

  /**
   * Displays a certain error message.
   *
   * @param errorMessage the error message to be displaued
   */
  void error(String errorMessage);

  /**
   * Repaints this view.
   */
  void refresh();

  /**
   * Resets the highlighted card.
   */
  void resetHighlight();

  /**
   * Enables the blur screen effect to indicate input is disabled.
   */
  void blurScreen(boolean blur);

  /**
   * Displays this message when the game is over.
   *
   * @param winner the winner of the game.
   */
  void displayEndGame(String winner);


  /**
   * Enables the view to clicks.
   *
   * @param enabled whether the view should be enabled to register clicks.
   */
  void setEnabled(boolean enabled);

  /**
   * Tells the BoardPanelHints (if it exists) which card was just selected.
   *
   * @param card the card that was selected.
   */
  void selectedCard(ICard card);
}
