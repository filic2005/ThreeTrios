package cs3500.threetrios.model;

/**
 * A class representing a rule in ThreeTrios that reverses the other rules given to it.
 */
public class ReverseBattleRule implements IBattleRule {

  private final IBattleRule rule;

  /**
   * Reverses the battle rule given to it.
   *
   * @param battleRule the rule this is adding onto.
   */
  public ReverseBattleRule(IBattleRule battleRule) {
    this.rule = battleRule;
  }

  @Override
  public boolean shouldFlip(int battler, int opponent) {
    return !rule.shouldFlip(battler, opponent);
  }
}
