package cs3500.threetrios.view;

import cs3500.threetrios.model.ICard;

/**
 * Interface for a BoardPanel that can toggle hints for placing cards.
 */
public interface IBoardPanelHints {

  /**
   * The card that is selected for placement hints.
   *
   * @param card the selected card.
   */
  void setCard(ICard card);
}
