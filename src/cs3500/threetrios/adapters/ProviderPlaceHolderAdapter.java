package cs3500.threetrios.adapters;

import cs3500.threetrios.provider.gamecomponents.BoardCell;
import cs3500.threetrios.provider.gamecomponents.CardTT;
import cs3500.threetrios.provider.gamecomponents.PlaceHolderTT;

/**
 * Adapter Class for converting our empty CardCell class to theirs.
 */
public class ProviderPlaceHolderAdapter implements PlaceHolderTT {

  CardTT card;

  /**
   * Creates an instance of a CardCell that can have a card added to it.
   *
   * @param card The card being added, can be null if space is empty.
   */
  public ProviderPlaceHolderAdapter(CardTT card) {
    this.card = card;
  }

  @Override
  public CardTT getCard() {
    return this.card;
  }

  @Override
  public String toStringForGrid() {
    return "";
  }

  @Override
  public BoardCell getBoardCell() {
    return null;
  }
}
