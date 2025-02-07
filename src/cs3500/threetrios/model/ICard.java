package cs3500.threetrios.model;

/**
 * Represents a Card to be used in the game of ThreeTrios.
 * Card has four directional values and an owner, all of which can be accessed.
 */
public interface ICard {

  /**
   * Retrieves this Card's numerical North value.
   */
  int getNorth();

  /**
   * Retrieves this Card's numerical West value.
   */
  int getWest();

  /**
   * Retrieves this Card's numerical South value.
   */
  int getSouth();

  /**
   * Retrieves this Card's numerical East value.
   */
  int getEast();

  /**
   * Allows the caller to set the owner of this card.
   * @param owner true is RED, false is BLUE.
   */
  void setOwner(boolean owner);

  /**
   * Returns the owner of this card in one character.
   * @return String, R for RED, B for BLUE. Used to help print out game state in view.
   */
  String getOwner();

  /**
   * Returns the name of the card.
   * @return the cards name.
   */
  String getName();

  /**
   * Returns a visual representation of this Card's info.
   * @return String Card's toString.
   */
  String toString();

}
