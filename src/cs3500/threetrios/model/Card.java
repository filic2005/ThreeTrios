package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a Card in the ThreeTrios game.
 */
public class Card implements ICard {

  private final String north;
  private final String east;
  private final String south;
  private final String west;
  private final String name;
  private boolean owner; //true is Red, false is Blue

  /**
   * Constructs a Card object given specific direction and owner values.
   * Since A represents the value 10, we believed that it would be redundant to represent
   * Card Values using an enum since it would require you to go back and forth to retrieve values
   * and set values, so we decided that it would be more efficient to ensure a correct range of
   * values manually rather than have java ensure it for us.
   *
   * @param north direction value
   * @param east direction value
   * @param south direction value
   * @param west direction value
   * @param name name of card
   */
  public Card(String name, String north, String south, String east, String west) {
    String values = "123456789A";
    if (!values.contains(north) || !values.contains(east)
            || !values.contains(south) || !values.contains(west)) {
      throw new IllegalArgumentException("Invalid directional value");
    }

    this.north = north;
    this.east = east;
    this.south = south;
    this.west = west;
    this.name = name;
    this.owner = true;
  }


  @Override
  public int getNorth() {
    if (Objects.equals(this.north, "A")) {
      return 10;
    }
    return Integer.parseInt(this.north);
  }

  @Override
  public int getWest() {
    if (Objects.equals(this.west, "A")) {
      return 10;
    }
    return Integer.parseInt(this.west);
  }

  @Override
  public int getSouth() {
    if (Objects.equals(this.south, "A")) {
      return 10;
    }
    return Integer.parseInt(this.south);
  }

  @Override
  public int getEast() {
    if (Objects.equals(this.east, "A")) {
      return 10;
    }
    return Integer.parseInt(this.east);
  }

  @Override
  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  @Override
  public String getOwner() {
    if (this.owner) {
      return "R";
    }
    return "B";
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return name + " " + north + " " + south
            + " " + east + " " + west;
  }

}
