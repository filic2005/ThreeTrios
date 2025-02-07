package cs3500.threetrios.model;

/**
 * A class representing the fallen ace rule in ThreeTrios.
 */
public class FallenAceRule implements IBattleRule {

  private final IBattleRule rule;

  /**
   * Constructor for fallenAce.
   *
   * @param battleRule the previous rule.
   */
  public FallenAceRule(IBattleRule battleRule) {
    this.rule = battleRule;
  }

  @Override
  public boolean shouldFlip(int attacker, int defender) {
    if (attacker == 1 && defender == 10) {
      return true;
    }

    if (defender == 1 && attacker == 10) {
      return false;
    }

    return rule.shouldFlip(attacker, defender);
  }
}
