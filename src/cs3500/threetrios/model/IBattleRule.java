package cs3500.threetrios.model;

/**
 * Represents a rule for determining card battles.
 */
public interface IBattleRule {

  /**
   * Determines if a defending card should be flipped based on the attacking card.
   *
   * @param attacker the attacking card.
   * @param defender the defending card.
   * @return true if the defender is flipped, false otherwise.
   */
  boolean shouldFlip(int attacker, int defender);
}
