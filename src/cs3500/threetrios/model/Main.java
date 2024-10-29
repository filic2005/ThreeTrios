package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.view.ThreeTriosView;


public class Main {
  public static void main(String[] str) throws FileNotFoundException {

    IThreeTriosModel model = new ThreeTriosModel(new Random(1),
            "src/cs3500/threetrios/model/gridFile1.txt", "src/cs3500/threetrios/model/cardDB1.txt");

    ThreeTriosView view = new ThreeTriosView(model, new StringBuilder());

    System.out.print(view);

    model.placeCard(0, 0, 0);

    System.out.print(view);

    model.placeCard(1, 0, 1);

    System.out.print(view);
  }
}
