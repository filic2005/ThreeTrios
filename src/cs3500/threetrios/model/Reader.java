package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that takes in config text files for ThreeTrios and renders them into
 * usable list formats.
 */
public class Reader {

  /**
   * Constructs a blank Reader object, all pertinent info is passed into methods
   * so this is blank.
   */
  Reader() {}


  /**
   * Takes in a grid config file path and parses the file, returning a 2D ArrayList
   * representing the grid of the game.
   * @param gridFile text file path.
   * @return 2D ArrayList of the grid
   * @throws FileNotFoundException when the path is invalid.
   * If the file given isn't the correct format, nothing is done.
   */
  public ArrayList<ArrayList<Cell>> createGrid(String gridFile) throws FileNotFoundException {

    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    File gridConfig = new File(gridFile);
    FileReader reader = new FileReader(gridConfig);
    Scanner in = new Scanner(reader);

    int rows = Integer.parseInt(in.next());
    int cols = Integer.parseInt(in.next());
    in.nextLine();

    for (int i = 0; i < rows; i++) {
      grid.add(new ArrayList<>());
    }

    for (int row = 0; row < rows; row++) {
      String line = in.nextLine();
      for (int col = 0; col < cols; col++) {
        Character input = line.charAt(col);
        if (input.equals('C')) {
          grid.get(row).add(col, new CardCell(null, row, col));
        }
        if (input.equals('X')) {
          grid.get(row).add(col, new Hole(row, col));
        }
      }
    }

    return grid;
  }

  /**
   * Takes in a text config file with Card information and parses it into a usable list
   * format, representing all possible cards in the game.
   * @param cardDB text file path.
   * @param random Random object used to shuffle the cards.
   * @return an ArrayList of the Cards used in the game.
   * @throws FileNotFoundException when the path given is invalid.
   */
  public ArrayList<Card> createHands(String cardDB, Random random) throws FileNotFoundException {

    ArrayList<Card> cardList = new ArrayList<>();

    File cards = new File(cardDB);
    FileReader reader = new FileReader(cards);
    Scanner in = new Scanner(reader);

    while(in.hasNext()) {
      String text = in.nextLine();
      String[] cardInfo = text.split(" ");

      cardList.add(new Card(cardInfo[0], cardInfo[1],
              cardInfo[2], cardInfo[3],
              cardInfo[4]));
    }

    Collections.shuffle(cardList, random);

    return cardList;
  }

}
