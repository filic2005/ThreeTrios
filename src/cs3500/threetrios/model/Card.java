package cs3500.threetrios.model;

/**
 * Represents a Card in the ThreeTrios game.
 */
public class Card {

  private final CardValues north;
  private final CardValues east;
  private final CardValues south;
  private final CardValues west;
  private String name;
  private boolean owner; //true is Red, false is Blue

  /**
   * Constructs a Card object given specific direction and owner values.
   * @param north direction value
   * @param east direction value
   * @param south direction value
   * @param west direction value
   * @param name name of card
   */
  Card(String name, CardValues north, CardValues south, CardValues east, CardValues west) {
    this.north = north;
    this.east = east;
    this.south = south;
    this.west = west;
    this.name = name;
  }

  /**
   * Initializes a Card object with all direction values set to ONE and owner set to BLUE.
   */
  Card() {
    this.north = CardValues.ONE;
    this.east = CardValues.ONE;
    this.south = CardValues.ONE;
    this.west = CardValues.ONE;
    this.name = "";
    this.owner = true;
  }

  /**
   * Retrieves this Card's numerical North value.
   */
  public int getNorth() {
    return this.north.getValue();
  }

  /**
   * Retrieves this Card's numerical West value.
   */
  public int getWest() {
    return this.west.getValue();
  }

  /**
   * Retrieves this Card's numerical South value.
   */
  public int getSouth() {
    return this.south.getValue();
  }

  /**
   * Retrieves this Card's numerical East value.
   */
  public int getEast() {
    return this.east.getValue();
  }

  /**
   * Retrieves this card's name
   */
  public String getName() {
    return this.name;
  }

  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  public String getOwner() {
    if (owner) {
      return "R";
    }
    return "B";
  }

  public String toString() {
    return name + " " + north.getValue() + " " + south.getValue()
            + " " + east.getValue() + " " + west.getValue();
  }

}
