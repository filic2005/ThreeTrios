package cs3500.threetrios.controller;

import cs3500.threetrios.model.IReadOnlyThreeTriosModel;
import cs3500.threetrios.model.RobotMove;
import cs3500.threetrios.model.ThreeTriosStrategy;

/**
 * This class represents an AI player that makes its decisions using strategies.
 */
public class MachinePlayer implements Player {

  private final PlayerColor color;
  private IReadOnlyThreeTriosModel model;
  private final ThreeTriosStrategy strategy;

  /**
   * Initializes a machine player.
   *
   * @param model the model the player is playing on.
   * @param color the color of this AI player.
   * @param strategy the strategy this AI player uses to make decisions.
   */
  public MachinePlayer(IReadOnlyThreeTriosModel model, PlayerColor color,
                       ThreeTriosStrategy strategy) {
    this.strategy = strategy;
    this.color = color;
    this.model = model;
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }

  /**
   * Machine player uses strategy to decide where to play.
   *
   * @return the hand index, and spot on board.
   */
  public RobotMove getNextMove() {
    return strategy.chooseMove(this.model, color.toString());
  }

}
