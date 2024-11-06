package cs3500.threetrios;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosViewGUI;

public class ThreeTrios {
  public static void main(String[] args) {
    ThreeTriosModel model = new ThreeTriosModel("SeparatedHolesBoard", "22Cards");
    ThreeTriosViewGUI view = new ThreeTriosViewGUI(model);
    view.setVisible(true);
  }
}
