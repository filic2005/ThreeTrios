package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosView;


public class Main {
  public static void main(String[] str) throws FileNotFoundException {

    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    ThreeTriosView view = new ThreeTriosView(model, new StringBuilder());
    System.out.println(model.getPlayerHand("RED"));
    System.out.println(model.getPlayerHand("BLUE"));
    model.placeCard(0, 0, 0);
    System.out.print(view.toString());

    model.placeCard(0, 1, 0);
    System.out.print(view.toString());

    model.placeCard(1, 0, 0);
    System.out.print(view.toString());

    model.placeCard(1, 1, 0);

    System.out.print(view.toString());
  }
}
