package cs3500.threetrios.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.IThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosModel;

public class ThreeTriosView {

  private final IThreeTriosModel<?> model;
  private final Appendable appendable;

  public ThreeTriosView() {

  }

  public ThreeTriosView(IThreeTriosModel model, Appendable ap) {
    this.appendable = ap;
    this.model = model;
  }

  public String toString() {

    StringBuilder gameState = new StringBuilder();

    gameState.append("Player: ").append(model.getTurn()).append("\n");

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

    Player current;
    if (Objects.equals(model.getTurn(), "RED")) {

    }



  }

  public void render() throws IOException {}

}
