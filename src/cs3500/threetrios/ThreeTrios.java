package cs3500.threetrios;

import cs3500.threetrios.model.*;
import cs3500.threetrios.view.ThreeTriosViewGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ThreeTrios {
  public static void main(String[] args) {
    Random rand = new Random(1);
    try {
      ArrayList<ArrayList<Cell>> grid = new Reader().createGrid("NoHolesBoard");
      ArrayList<Card> cards = new Reader().createHands("17Cards");
      ThreeTriosModel model = new ThreeTriosModel(rand, grid, cards);
      ThreeTriosViewGUI view = new ThreeTriosViewGUI(model);
      ThreeTriosStrategy strategyRed = new CornersFirst();
      ThreeTriosStrategy strategyBlue = new CornersFirst();

      for (int i = 0; i < 2; i++) {
        model.placeCard(strategyRed.chooseMove(model, "R").getRow(),
                strategyRed.chooseMove(model, "R").getCol(),
                strategyRed.chooseMove(model, "R").getHandIdx());
        model.placeCard(strategyBlue.chooseMove(model, "B").getRow(),
                strategyBlue.chooseMove(model, "B").getCol(),
                strategyBlue.chooseMove(model, "B").getHandIdx());
      }
//      model.placeCard(strategyRed.chooseMove(model, "R").getRow(),
//              strategyRed.chooseMove(model, "R").getCol(),
//              strategyRed.chooseMove(model, "R").getHandIdx());

      view.setVisible(true);
    } catch(IOException ignored) {

    }
  }
}
