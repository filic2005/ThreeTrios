package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;

/**
 * Interface for a BoardPanel in a GUI View for ThreeTrios.
 */
public interface IBoardPanel {
  /**
   * Sets a card to highlighted and repaints when it is called upon.
   * Called when a Card is clicked on.
   *
   * @param row the row of the highlighted cell.
   * @param col the col of the highlighted cell.
   */
  void highlightCell(int row, int col);

  /**
   * Adds an observer to the panel so that it can communicate with it when things are clicked.
   *
   * @param feature the observer (controller)
   */
  void addFeatureListener(Features feature);

  /**
   * Restarts the sequence if the color was guessed incorrectly.
   * Also informs the player.
   */
  void error(String errorMessage);
}
