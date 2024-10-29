package cs3500.threetrios.model;

/**
 * Enum to represent the different values a Card can hold.
 */
public enum CardValues {

  ONE('1'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'),
  SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), A('A');

  private final char value;

  /**
   * Initializes the enum with the given value.
   * @param newValue card value provided.
   */
  CardValues(final char newValue) {
    value = newValue;
  }

  /**
   * Used to retrieve each Enum's numerical value.
   * @return value CardValue.
   */
  public char getValue() { return value; }

  /**
   * Returns the integer value of the constant in order to let A be valued
   * at 10.
   * @return int value of the Enum.
   */
  public int getIntValue() {
    if (value == 'A') {
      return 10;
    }
    return Character.getNumericValue(value);
  }

}
