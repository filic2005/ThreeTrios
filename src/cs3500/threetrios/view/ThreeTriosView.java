package cs3500.threetrios.view;

import java.io.IOException;
import java.util.ArrayList;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.IThreeTriosModel;

/**
 * Represents a rudimentary text view of a game of ThreeTrios.
 */
public class ThreeTriosView {

  private final IThreeTriosModel model;
  private final Appendable appendable;

  /**
   * Constructs a view of the given model of ThreeTrios.
   * @param model game model.
   * @param ap appendable to add renderings to.
   */
  public ThreeTriosView(IThreeTriosModel model, Appendable ap) {
    this.appendable = ap;
    this.model = model;
  }

  /**
   * Constructs a visual representation of the gamestate.
   * @return String to be printed to show gamestate.
   */
  public String toString() {

    StringBuilder gameState = new StringBuilder();

    String turn = model.getTurn();

    if (turn.equals("R")) {
      gameState.append("Player: ").append("RED").append("\n");
    } else {
      gameState.append("Player: ").append("BLUE").append("\n");
    }

    ArrayList<ArrayList<Cell>> grid = model.getGrid();

    for (int row = 0; row < grid.size(); row++) {
      for (int col = 0; col < grid.get(0).size(); col++) {

        if (grid.get(row).get(col) instanceof CardCell) {

          if (((CardCell) grid.get(row).get(col)).getCard() != null) {
            gameState.append(((CardCell) grid.get(row).get(col)).getCard().getOwner());
          } else if (((CardCell) grid.get(row).get(col)).getCard() == null) {
            gameState.append("_");
          }
        }

        if (grid.get(row).get(col) instanceof Hole) {
          gameState.append(" ");
        }
      }
      gameState.append("\n");
    }

    gameState.append("Hand:\n");

    for (Card card : model.getPlayerHand(model.getTurn())) {
      gameState.append(card.toString() + "\n");
    }

    return gameState.toString();
  }

  /**
   * Renders a view of the model to the appendable.
   * @throws IOException if invalid input passed to appendable.
   */
  public void render() throws IOException {
    this.appendable.append(this.toString());
  }

}
