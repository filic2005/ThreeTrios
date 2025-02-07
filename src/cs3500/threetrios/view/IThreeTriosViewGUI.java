package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;


/**
 * Interface for the Frame of the GUI view for a game of ThreeTrios.
 * This Frame represents both game hands and the board.
 */
public interface IThreeTriosViewGUI {

  /**
   * Makes the view visible.
   *
   * @param show represents whether the view is meant to be displayed.
   */
  void display(boolean show);

  /**
   * Adds an observer to the view so that it can communicate with it when things are clicked.
   *
   * @param feature the observer (controller)
   */
  void addFeaturesListener(Features feature);
}
