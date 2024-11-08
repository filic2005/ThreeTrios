package cs3500.threetrios.model;

public interface ThreeTriosStrategy {
  /**
   *
   *
   * @param model
   * @param player
   * @return
   */
  RobotMove chooseMove(IReadOnlyThreeTriosModel model, String player);
}
