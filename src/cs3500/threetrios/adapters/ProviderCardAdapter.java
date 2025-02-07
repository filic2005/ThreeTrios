package cs3500.threetrios.adapters;

import cs3500.threetrios.model.ICard;
import cs3500.threetrios.provider.gamecomponents.AttackValue;
import cs3500.threetrios.provider.gamecomponents.BoardCell;
import cs3500.threetrios.provider.gamecomponents.CardTT;
import cs3500.threetrios.provider.gamecomponents.Direction;

import java.awt.Color;
import java.util.Map;

/**
 * An adapter class for making our card into our providers card.
 */
public class ProviderCardAdapter implements CardTT {

  ICard card;

  /**
   * Initializes an adapter for the providers card class.
   *
   * @param card our card that needs to be adapted.
   */
  public ProviderCardAdapter(ICard card) {
    this.card = card;
  }

  @Override
  public Map<Direction, AttackValue> getAttackValues() {

    AttackValue north = intToAV(card.getNorth());
    AttackValue east = intToAV(card.getEast());
    AttackValue west = intToAV(card.getWest());
    AttackValue south = intToAV(card.getSouth());

    return Map.of(
            Direction.NORTH, north,
            Direction.SOUTH, south,
            //had to swap east and west because their impl initialized in a different order.
            Direction.EAST, west,
            Direction.WEST, east);
  }

  private AttackValue intToAV(int val) {
    switch (val) {
      case 1:
        return AttackValue.ONE;
      case 2:
        return AttackValue.TWO;
      case 3:
        return AttackValue.THREE;
      case 4:
        return AttackValue.FOUR;
      case 5:
        return AttackValue.FIVE;
      case 6:
        return AttackValue.SIX;
      case 7:
        return AttackValue.SEVEN;
      case 8:
        return AttackValue.EIGHT;
      case 9:
        return AttackValue.NINE;
      case 10:
        return AttackValue.TEN;
      default:
        throw new IllegalArgumentException("Invalid value for AttackValue: " + val);
    }
  }

  @Override
  public Color getColor() {
    String owner = card.getOwner();
    if (owner.equals("R")) {
      return Color.RED;
    }
    return Color.blue;
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public void changeColor(Color color) {
    if (color == Color.RED) {
      card.setOwner(true);
    }
    card.setOwner(false);
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
