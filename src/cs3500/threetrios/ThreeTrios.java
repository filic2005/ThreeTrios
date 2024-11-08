package cs3500.threetrios;

import cs3500.threetrios.model.FlipMaxCards;
import cs3500.threetrios.model.RobotMove;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosStrategy;
import cs3500.threetrios.view.ThreeTriosViewGUI;

import java.util.Random;

public class ThreeTrios {
  public static void main(String[] args) {
    Random rand = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(rand, "SeparatedHolesBoard", "17Cards");
    ThreeTriosViewGUI view = new ThreeTriosViewGUI(model);
    model.placeCard(1,1,0);
    ThreeTriosStrategy strategy = new FlipMaxCards();
    RobotMove move = strategy.chooseMove(model, "BLUE");
    model.placeCard(move.getRow(), move.getCol(), move.getHandIdx());
    view.setVisible(true);
  }
}
