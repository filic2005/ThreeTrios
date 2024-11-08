package cs3500.threetrios;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosViewGUI;

import java.util.Random;

public class ThreeTrios {
  public static void main(String[] args) {
    Random rand = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(rand, "SeparatedHolesBoard", "17Cards");
    ThreeTriosViewGUI view = new ThreeTriosViewGUI(model);
    model.placeCard(1,1,0);
    model.placeCard(1,0,0);
    view.setVisible(true);
  }
}
