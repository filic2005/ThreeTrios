package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;

/**
 * Interface for a HandPanel in a gui for ThreeTrios.
 */
public interface IHandPanel {

  /**
   * Adds an observer to the panel so that it can communicate with it when things are clicked.
   *
   * @param feature the observer (controller)
   */
  void addFeatureListener(Features feature);
}
