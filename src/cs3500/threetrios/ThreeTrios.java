package cs3500.threetrios;

import cs3500.threetrios.model.*;
import cs3500.threetrios.view.ThreeTriosViewGUI;

import java.util.Random;

public class ThreeTrios {
  public static void main(String[] args) {
    Random rand = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(rand, "NoHolesBoard", "17Cards");
    ThreeTriosViewGUI view = new ThreeTriosViewGUI(model);



    ThreeTriosStrategy strategyRed = new CornersFirst();
    ThreeTriosStrategy strategyBlue = new CornersFirst();

    for (int i = 0; i < 3; i++) {
      model.placeCard(strategyRed.chooseMove(model, "R").getRow(),
              strategyRed.chooseMove(model, "R").getCol(),
              strategyRed.chooseMove(model, "R").getHandIdx());
      model.placeCard(strategyBlue.chooseMove(model, "B").getRow(),
              strategyBlue.chooseMove(model, "B").getCol(),
              strategyBlue.chooseMove(model, "B").getHandIdx());
    }
//    model.placeCard(strategyRed.chooseMove(model, "R").getRow(),
//            strategyRed.chooseMove(model, "R").getCol(),
//            strategyRed.chooseMove(model, "R").getHandIdx());

    view.setVisible(true);
  }
}
