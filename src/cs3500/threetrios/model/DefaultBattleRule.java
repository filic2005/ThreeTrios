package cs3500.threetrios.model;

/**
 * A class representing the base battle rules of Three Trios.
 */
public class DefaultBattleRule implements IBattleRule {

  /**
   * Empty constructor for making the rule.
   */
  public DefaultBattleRule() {
    //just to construct it.
  }

  @Override
  public boolean shouldFlip(int attacker, int defender) {
    return attacker > defender;
  }
}
