package cs3500.threetrios.adapters;

import cs3500.threetrios.provider.gamecomponents.BoardCell;
import cs3500.threetrios.provider.gamecomponents.CardTT;
import cs3500.threetrios.provider.gamecomponents.HoleTT;

/**
 * Adapter class to represent an instance of provider's Hole.
 */
public class ProviderHoleAdapter implements HoleTT {

  /**
   * Empty constructor that just serves to initialize object.
   */
  public ProviderHoleAdapter() {
    //doesn't need anything, just to initialize an instance of it
  }

  @Override
  public CardTT getCard() {
    return null;
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
