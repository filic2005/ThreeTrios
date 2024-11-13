package cs3500.threetrios;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosView;

import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Test class for ThreeTrios implementation.
 */
public class ModelTest {

  @Test
  public void testConstructorValidFiles() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertNotNull(model.getGrid());
    assertEquals("R", model.getTurn());
    assertEquals(9, model.numCardCellOnBoard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidGridFile() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            "invalid_grid.txt", "test_cards.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidCardDBFile() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            "test_grid.txt", "invalid_cards.txt");
  }

  @Test
  public void testDealToPlayers() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertEquals(5, model.getPlayerHand("R").size());
    assertEquals(5, model.getPlayerHand("B").size());
  }

  @Test
  public void testGetTurn() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertEquals("R", model.getTurn());
    model.placeCard(1, 1, 0);
    assertEquals("B", model.getTurn());
  }

  @Test
  public void testGetPlayerHandInvalidPlayer() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertThrows(IllegalArgumentException.class, () -> model.getPlayerHand("GREEN"));
  }

  @Test
  public void testPlaceCardOutOfBounds() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertThrows(IllegalArgumentException.class,
        () -> model.placeCard(-1, 0, 0));
    assertThrows(IllegalArgumentException.class,
        () -> model.placeCard(model.getGrid().size(), 0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInOccupiedCell() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    model.placeCard(1, 1, 0);
    model.placeCard(1, 1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInvalidCellType() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "ConnectedHolesBoard",
            "22Cards");
    model.placeCard(0, 5, 0);
  }

  @Test
  public void testBattleMechanic() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "22Cards");
    model.placeCard(0, 0, 0);
    assertEquals("R", ((CardCell)model.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", model.getTurn());
    model.placeCard(0, 1, 0);
    assertEquals("B", ((CardCell)model.getGrid().get(0).get(1)).getCard().getOwner());
  }

  @Test
  public void testNumCardCellOnBoard() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
    assertEquals(9, model.numCardCellOnBoard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPathNameInConstructor() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "invalid_path",
            "invalid_path");
  }

  @Test
  public void testBattleRecursion() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
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
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
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
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
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
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "NoHolesBoard", "17Cards");
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
  public void testGameTieAndOver() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "1x1TestBoard", "17Cards");
    Appendable ap = new StringBuilder();

    System.out.println(model.getPlayerHand("R"));
    System.out.println(model.getPlayerHand("B"));

    model.placeCard(0, 0, 0);
    assertEquals(true, model.isGameOver());
    assertEquals("The game is a TIE.", model.whoWonGame());
  }

  @Test
  public void testGameWinAndOver() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "2x2Board", "17Cards");
    Appendable ap = new StringBuilder();
    model.placeCard(1, 0, 1);
    model.placeCard(0, 0, 1);
    model.placeCard(0, 1, 0);
    model.placeCard(1, 1, 0);
    assertEquals(true, model.isGameOver());
    assertEquals("Player RED Wins.", model.whoWonGame());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBadFomatBoard() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "BadFormatBoard", "17Cards");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadFormatCards() {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random, "2x2Board",
            "BadFormatCards");
  }

}
