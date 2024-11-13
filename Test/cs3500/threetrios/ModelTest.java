package cs3500.threetrios;

import cs3500.threetrios.model.*;
import cs3500.threetrios.view.ThreeTriosView;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Test class for ThreeTrios implementation.
 */
public class ModelTest {

  ThreeTriosModel model;

  @Before
  public void init() {
    ArrayList<ArrayList<Cell>> NoHolesBoard = null;
    ArrayList<Card> cards = null;
    try {
      NoHolesBoard = new Reader().createGrid("NoHolesBoard");
      cards = new Reader().createHands("17Cards");
    } catch (IOException ignored) {
    }
    model = new ThreeTriosModel(new Random(1), NoHolesBoard, cards);
  }

  @Test
  public void testConstructorValidFiles() {
    assertNotNull(model.getGrid());
    assertEquals("R", model.getTurn());
    assertEquals(9, model.numCardCellOnBoard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidGridFile() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("BadFormatBoard"), new Reader().createHands("17Cards"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidCardDBFile() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("NoHolesBoard"), new Reader().createHands("BadFormatCards"));
  }

  @Test
  public void testDealToPlayers() {
    assertEquals(5, model.getPlayerHand("R").size());
    assertEquals(5, model.getPlayerHand("B").size());
  }

  @Test
  public void testGetTurn() {
    assertEquals("R", model.getTurn());
    model.placeCard(1, 1, 0);
    assertEquals("B", model.getTurn());
  }

  @Test
  public void testGetPlayerHandInvalidPlayer() {
    assertThrows(IllegalArgumentException.class, () -> model.getPlayerHand("GREEN"));
  }

  @Test
  public void testPlaceCardOutOfBounds() {
    assertThrows(IllegalArgumentException.class,
        () -> model.placeCard(-1, 0, 0));
    assertThrows(IllegalArgumentException.class,
        () -> model.placeCard(model.getGrid().size(), 0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInOccupiedCell() {
    model.placeCard(1, 1, 0);
    model.placeCard(1, 1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInvalidCellType() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("ConnectedHolesBoard"),
            new Reader().createHands("22Cards"));
    model.placeCard(0, 5, 0);
  }

  @Test
  public void testBattleMechanic() {
    Random random = new Random(1);
    ArrayList<ArrayList<Cell>> noHolesBoard = null;
    ArrayList<Card> cards22 = null;
    try {
      noHolesBoard = new Reader().createGrid("NoHolesBoard");
      cards22 = new Reader().createHands("22Cards");
    } catch (IOException ignored) {
    }
    ThreeTriosModel model2 = new ThreeTriosModel(random, noHolesBoard, cards22);
    model2.placeCard(0, 0, 0);
    assertEquals("R", ((CardCell)model2.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", model2.getTurn());
    model2.placeCard(0, 1, 0);
    assertEquals("B", ((CardCell)model2.getGrid().get(0).get(1)).getCard().getOwner());
  }

  @Test
  public void testNumCardCellOnBoard() {
    assertEquals(9, model.numCardCellOnBoard());
  }

  @Test(expected = FileNotFoundException.class)
  public void testInvalidPathNameInConstructor() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("invalid_path"),
            new Reader().createHands("invalid_path"));
  }

  @Test
  public void testBattleRecursion() {
    ThreeTriosView view = new ThreeTriosView(model, new StringBuilder());
    System.out.println(model.getPlayerHand("R"));
    System.out.println(model.getPlayerHand("B"));
    model.placeCard(0, 0, 0);
    model.placeCard(0, 1, 0);
    model.placeCard(1, 0, 0);
    model.placeCard(1, 1, 0);

    assertEquals("B", ((CardCell) model.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) model.getGrid().get(0).get(1)).getCard().getOwner());
    assertEquals("B", ((CardCell) model.getGrid().get(1).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) model.getGrid().get(1).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleRecursionCombo() {
    System.out.println(model.getPlayerHand("R"));
    System.out.println(model.getPlayerHand("B"));
    model.placeCard(0, 0, 0);
    model.placeCard(1, 1, 0);
    model.placeCard(1, 0, 0);
    model.placeCard(2, 0, 1);

    assertEquals("R", ((CardCell) model.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) model.getGrid().get(1).get(1)).getCard().getOwner());
    assertEquals("R", ((CardCell) model.getGrid().get(1).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) model.getGrid().get(2).get(0)).getCard().getOwner());
  }

  @Test
  public void testInitialGameStateView() {
    Appendable ap = new StringBuilder();
    ThreeTriosView view = new ThreeTriosView(model, ap);

    try {
      view.render();
    } catch (IOException ignored) {
    }

    String expected = "Player: RED\n"
            + "___\n"
            + "___\n"
            + "___\n"
            + "Hand:\n"
            + "Card12 5 6 A 3\n"
            + "Card10 9 4 2 A\n"
            + "Card4 7 A 3 5\n"
            + "Card1 5 3 A 2\n"
            + "Card3 8 1 4 A\n";

    assertEquals(expected, ap.toString());
  }

  @Test
  public void testPlayedGameStateView() {
    model.placeCard(0, 0, 0);
    model.placeCard(0, 1, 0);
    model.placeCard(1, 0, 0);
    model.placeCard(1, 1, 0);

    Appendable ap = new StringBuilder();
    ThreeTriosView view = new ThreeTriosView(model, ap);

    try {
      view.render();
    } catch (IOException ignored) {
    }

    String expected = "Player: RED\n"
            + "BB_\n"
            + "BB_\n"
            + "___\n"
            + "Hand:\n"
            + "Card4 7 A 3 5\n"
            + "Card1 5 3 A 2\n"
            + "Card3 8 1 4 A\n";
    assertEquals(expected, ap.toString());
  }

  @Test
  public void testGameTieAndOver() throws FileNotFoundException {
    Random random = new Random(1);

    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("1x1TestBoard"),
            new Reader().createHands("17Cards"));
    Appendable ap = new StringBuilder();

    System.out.println(model.getPlayerHand("R"));
    System.out.println(model.getPlayerHand("B"));

    model.placeCard(0, 0, 0);
    assertEquals(true, model.isGameOver());
    assertEquals("The game is a TIE.", model.whoWonGame());
  }

  @Test
  public void testGameWinAndOver() throws FileNotFoundException {
    model.placeCard(0, 0, 0);
    model.placeCard(1, 0, 0);
    model.placeCard(2, 0, 0);
    model.placeCard(0, 1, 0);
    model.placeCard(1, 1, 0);
    model.placeCard(2, 1, 0);
    model.placeCard(0, 2, 0);
    model.placeCard(1, 2, 0);
    model.placeCard(2, 2, 0);
    assertEquals(true, model.isGameOver());
    assertEquals("Player RED Wins.", model.whoWonGame());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenBoard() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("2x2Board"),
            new Reader().createHands("17Cards"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBadFormatBoard() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("BadFormatBoard"),
            new Reader().createHands("17Cards"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadFormatCards() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader().createGrid("2x2Board"),
            new Reader().createHands("BadFormatCards"));
  }



}
