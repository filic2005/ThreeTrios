package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.model.BluePlayer;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.CardValues;
import cs3500.threetrios.model.CartPt;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.IThreeTriosModel;
import cs3500.threetrios.model.RedPlayer;

public class ThreeTriosModel implements IThreeTriosModel {

  //the outer arraylist represents the left column, inner represents the rows
  private ArrayList<ArrayList<Cell>> grid;
  private int rows;
  private int cols;
  private ArrayList<Card> cardList;
  private Random random;
  private BluePlayer bluePlayer;
  private RedPlayer redPlayer;
  private int gridCount;
  private boolean turn; //true is Red, false is Blue

  ThreeTriosModel() {
    this(new Random());
  }

  ThreeTriosModel(Random r) {
    this.random = r;
    grid = new ArrayList<ArrayList<Cell>>();
    cardList = new ArrayList<Card>();
    this.bluePlayer = new BluePlayer();
    this.redPlayer = new RedPlayer();
    this.turn = true;
  }

  @Override
  public void createGrid(String gridFile) throws FileNotFoundException {

    File gridConfig = new File(gridFile);
    FileReader reader = new FileReader(gridConfig);
    Scanner in = new Scanner(reader);

    rows = Integer.parseInt(in.next());
    cols = Integer.parseInt(in.next());

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        String input = in.next();
        if (input.equals("C")) {
          grid.get(row).add(col, new CardCell(null, new CartPt(row, col)));
        }
        if (input.equals("X")) {
          grid.get(row).add(col, new Hole(new CartPt(row, col)));
        }
      }
    }
  }

  public void createHands(String cardDB) throws FileNotFoundException {

    File cards = new File(cardDB);
    FileReader reader = new FileReader(cards);
    Scanner in = new Scanner(reader);

    while(in.hasNext()) {
      String text = in.nextLine();
      String[] cardInfo = text.split(" ");

      this.cardList.add(new Card(cardInfo[0], CardValues.valueOf(cardInfo[1]),
              CardValues.valueOf(cardInfo[2]), CardValues.valueOf(cardInfo[3]),
              CardValues.valueOf(cardInfo[4])));
    }

    Collections.shuffle(this.cardList, this.random);
  }

  public void dealToPlayers() {
    int totalCards = gridCount + 1;
    int playerCards = totalCards/2;
    for (int i = 0; i < playerCards; i++) {
      redPlayer.addCard(cardList.get(0));
    }
    for (int i = 0; i < playerCards; i++) {
      bluePlayer.addCard(cardList.get(0));
    }
  }

  public void numCardCellOnBoard() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid.get(i).get(j) instanceof CardCell) {
          gridCount++;
        }
      }
    }
  }

  public String getTurn() {
    if (turn) {
      return "RED";
    }
    return "BLUE";
  }

  //make it so we can't modify the grid from this method
  public ArrayList<ArrayList<Cell>> getGrid() {
    return new ArrayList<>(grid);
  }


  @Override
  public void selectCard(int row, int col) {

  }

  @Override
  public void placeCard(int row, int col) {

  }
}
