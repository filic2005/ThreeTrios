package cs3500.threetrios;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Reader;
import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.ThreeTriosStrategy;
import cs3500.threetrios.model.RobotMove;
import cs3500.threetrios.model.FlipMaxCards;
import cs3500.threetrios.model.CornersFirst;
import cs3500.threetrios.view.ThreeTriosView;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    ArrayList<ArrayList<Cell>> noHolesBoard = null;
    ArrayList<ICard> cards = null;
    try {
      noHolesBoard = new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      cards = new Reader().createHands("src/cs3500/threetrios/model/17Cards");
    } catch (IOException ignored) {
    }
    model = new ThreeTriosModel(new Random(1), noHolesBoard, cards, new DefaultBattleRule());
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
            new Reader().createGrid("src/cs3500/threetrios/model/BadFormatBoard"), new Reader()
            .createHands("src/cs3500/threetrios/model/17Cards"), new DefaultBattleRule());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidCardDBFile() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/BadFormatCards"),
            new DefaultBattleRule());
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
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("src/cs3500/threetrios/model/ConnectedHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/22Cards"),
            new DefaultBattleRule());
    model.placeCard(0, 5, 0);
  }

  @Test
  public void testBattleMechanic() {
    Random random = new Random(1);
    ArrayList<ArrayList<Cell>> noHolesBoard = null;
    ArrayList<ICard> cards22 = null;
    try {
      noHolesBoard = new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      cards22 = new Reader().createHands("src/cs3500/threetrios/model/22Cards");
    } catch (IOException ignored) {
    }
    ThreeTriosModel model2 = new ThreeTriosModel(random, noHolesBoard, cards22,
            new DefaultBattleRule());
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
            new Reader().createHands("invalid_path"), new DefaultBattleRule());
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

    ThreeTriosModel model = new ThreeTriosModel(random, new Reader()
            .createGrid("src/cs3500/threetrios/model/1x1TestBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());

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
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader()
            .createGrid("src/cs3500/threetrios/model/2x2Board"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBadFormatBoard() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader()
            .createGrid("src/cs3500/threetrios/model/BadFormatBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadFormatCards() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, new Reader()
            .createGrid("src/cs3500/threetrios/model/2x2Board"),
            new Reader().createHands("src/cs3500/threetrios/model/BadFormatCards"),
            new DefaultBattleRule());
  }

  @Test
  public void testChecksAllFourCorners() throws FileNotFoundException {
    MockModelAllPositions mockModel = new MockModelAllPositions(new Random(1),
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"));

    ThreeTriosStrategy strategy2 = new CornersFirst();
    RobotMove move = strategy2.chooseMove(mockModel, "R");

    List<String> expectedCorners = List.of("[0, 0]","[0, 2]","[2, 0]","[2, 2]");

    System.out.println(mockModel.getCheckedCoordinates());
    for (String corner : expectedCorners) {
      assertEquals(true, mockModel.getCheckedCoordinates().contains(corner));
    }
  }

  @Test
  public void testChecksAllCells() throws FileNotFoundException {
    MockModelAllPositions mockModel = new MockModelAllPositions(new Random(1),
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"));
    ThreeTriosStrategy strategy1 = new FlipMaxCards();
    strategy1.chooseMove(mockModel, "R");

    List<String> allCoordinates = new ArrayList<>();
    for (int i = 0; i < mockModel.getRows(); i++) {
      for (int j = 0; j < mockModel.getCols(); j++) {
        allCoordinates.add(new String("[" + i + ", " + j + "]"));
      }
    }

    System.out.println(mockModel.getCheckedCoordinates());
    for (String coord : allCoordinates) {
      assertEquals(true,mockModel.getCheckedCoordinates().contains(coord));
    }
  }

  @Test
  public void testChoosesMostValuableLocation() throws FileNotFoundException {
    MockModelSetsPerfectPos mockModel = new MockModelSetsPerfectPos(new Random(1),
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"));

    ICard targetCard = mockModel.getPlayerHand("R").get(0);
    mockModel.setFlipCount(2, 2, targetCard, 3);
    mockModel.setFlipCount(1, 1, targetCard, 1);
    mockModel.setFlipCount(0, 0, targetCard, 2);

    FlipMaxCards strategy = new FlipMaxCards();
    RobotMove chosenMove = strategy.chooseMove(mockModel, "R");

    assertEquals(2, chosenMove.getRow());
    assertEquals(2, chosenMove.getCol());
    assertEquals(0, chosenMove.getHandIdx());
  }

  @Test
  public void testFlipMaxCardsStrategy() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());
    ThreeTriosStrategy strategyRed = new FlipMaxCards();

    assertEquals(0, strategyRed.chooseMove(model, "R").getRow());
    assertEquals(0, strategyRed.chooseMove(model, "R").getCol());
    assertEquals(0, strategyRed.chooseMove(model, "R").getHandIdx());

    model.placeCard(strategyRed.chooseMove(model, "R").getRow(),
            strategyRed.chooseMove(model, "R").getCol(),
            strategyRed.chooseMove(model, "R").getHandIdx());

    assertEquals(1, strategyRed.chooseMove(model, "B").getRow());
    assertEquals(0, strategyRed.chooseMove(model, "B").getCol());
    assertEquals(1, strategyRed.chooseMove(model, "B").getHandIdx());

    model.placeCard(strategyRed.chooseMove(model, "B").getRow(),
            strategyRed.chooseMove(model, "B").getCol(),
            strategyRed.chooseMove(model, "B").getHandIdx());
  }
}
