package cs3500.threetrios.model;

/**
 * Interface for a Strategy in ThreeTrios.
 * One method that will choose a move, and return a RobotMove representing that move.
 */
public interface ThreeTriosStrategy {
  /**
   * Chooses the best move based on the strategy's rules.
   *
   * @param model the model the strategy is basing its move on.
   * @param player the player the strategy is finding a move for.
   * @return the coordinates of the point on the board and the hand index.
   */
  RobotMove chooseMove(IReadOnlyThreeTriosModel model, String player);
}
