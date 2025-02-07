package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
  public Reader() {
    //All data is collected using methods, this is just to create a File reader.
  }


  /**
   * Takes in a grid config file path and parses the file, returning a 2D ArrayList
   * representing the grid of the game.
   * @param gridFile text file path.
   * @return 2D ArrayList of the grid
   * @throws FileNotFoundException when the path is invalid.
   */
  public ArrayList<ArrayList<Cell>> createGrid(String gridFile) throws FileNotFoundException {

    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    File gridConfig = new File(gridFile);
    FileReader reader = new FileReader(gridConfig);
    Scanner in = new Scanner(reader);
    int cardCellCount = 0;

    if (!in.hasNextInt()) {
      throw new IllegalArgumentException("Expected integer for rows");
    }
    int rows = Integer.parseInt(in.next());

    if (!in.hasNextInt()) {
      throw new IllegalArgumentException("Expected integer for columns");
    }
    int cols = Integer.parseInt(in.next());

    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Rows and columns must be positive.");
    }
    in.nextLine();

    // Initialize the grid structure
    for (int i = 0; i < rows; i++) {
      grid.add(new ArrayList<>());
    }

    // Parse each line of the grid
    for (int row = 0; row < rows; row++) {
      if (!in.hasNextLine()) {
        throw new IllegalArgumentException("Expected more rows in grid data but reached end of "
                + "file");
      }
      String line = in.nextLine().trim();

      if (line.length() != cols) {
        throw new IllegalArgumentException("Row " + row + " does not match expected column "
                + "count of " + cols);
      }

      for (int col = 0; col < cols; col++) {
        char input = line.charAt(col);
        if (input == 'C') {
          grid.get(row).add(col, new CardCell(null, row, col));
          cardCellCount++;
        } else if (input == 'X') {
          grid.get(row).add(col, new Hole(row, col));
        } else {
          throw new IllegalArgumentException("Invalid character '" + input + "' at row " + row
                  + "," + " column " + col + ". Expected 'C' or 'X'.");
        }
      }
    }

    if (cardCellCount % 2 == 0) {
      throw new IllegalArgumentException("Card cell count must be odd.");
    }

    return grid;
  }

  /**
   * Takes in a text config file with Card information and parses it into a usable list
   * format, representing all possible cards in the game.
   * @param cardDB text file path.
   * @return an ArrayList of the Cards used in the game.
   * @throws FileNotFoundException when the path given is invalid.
   */
  public ArrayList<ICard> createHands(String cardDB) throws FileNotFoundException {
    ArrayList<ICard> cardList = new ArrayList<>();

    File cards = new File(cardDB);
    FileReader reader = new FileReader(cards);
    Scanner in = new Scanner(reader);

    while (in.hasNextLine()) {
      String text = in.nextLine().trim();

      if (text.isEmpty()) {
        continue;
      }

      String[] cardInfo = text.split(" ");

      if (cardInfo.length != 5) {
        throw new IllegalArgumentException("Invalid card format in line: " + text + ". "
                + "Expected 5 elements, found " + cardInfo.length);
      }

      cardList.add(new Card(cardInfo[0], cardInfo[1], cardInfo[2], cardInfo[3], cardInfo[4]));
    }

    return cardList;
  }
}