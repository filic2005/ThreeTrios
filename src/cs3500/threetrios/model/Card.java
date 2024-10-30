package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a Card in the ThreeTrios game.
 */
public class Card {

  private final String north;
  private final String east;
  private final String south;
  private final String west;
  private final String name;
  private boolean owner; //true is Red, false is Blue

  /**
   * Constructs a Card object given specific direction and owner values.
   * @param north direction value
   * @param east direction value
   * @param south direction value
   * @param west direction value
   * @param name name of card
   */
  Card(String name, String north, String south, String east, String west) {
    this.north = north;
    this.east = east;
    this.south = south;
    this.west = west;
    this.name = name;
    this.owner = true;
  }

  /**
   * Retrieves this Card's numerical North value.
   */
  public int getNorth() {
    if (Objects.equals(this.north, "A")) {
      return 10;
    }
    return Integer.parseInt(this.north);
  }

  /**
   * Retrieves this Card's numerical West value.
   */
  public int getWest() {
    if (Objects.equals(this.west, "A")) {
      return 10;
    }
    return Integer.parseInt(this.west);
  }

  /**
   * Retrieves this Card's numerical South value.
   */
  public int getSouth() {
    if (Objects.equals(this.south, "A")) {
      return 10;
    }
    return Integer.parseInt(this.south);
  }

  /**
   * Retrieves this Card's numerical East value.
   */
  public int getEast() {
    if (Objects.equals(this.east, "A")) {
      return 10;
    }
    return Integer.parseInt(this.east);
  }

  /**
   * Retrieves this card's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Allows the caller to set the owner of this card.
   * @param owner true is RED, false is BLUE.
   */
  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  /**
   * Returns the owner of this card in one character.
   * @return String, R for RED, B for BLUE. Used to help print out game state in view.
   */
  public String getOwner() {
    if (!owner) {
      return "B";
    }
    return "R";
  }

  /**
   * Returns a visual representation of this Card's info.
   * @return String Card's toString.
   */
  public String toString() {
    return name + " " + north + " " + south
            + " " + east + " " + west;
  }

}
