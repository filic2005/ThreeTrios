package cs3500.threetrios.controller;

/**
 * This class represents a player that is a human making its own decisions.
 */
public class HumanPlayer implements Player {

  private final PlayerColor color;

  /**
   * Initializes a human player with their color.
   *
   * @param color the player's color.
   */
  public HumanPlayer(PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }
}
