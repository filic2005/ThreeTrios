package cs3500.threetrios;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.IBattleRule;
import cs3500.threetrios.model.FallenAceRule;
import cs3500.threetrios.model.Reader;
import cs3500.threetrios.model.ReverseBattleRule;
import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.view.ThreeTriosView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the model variants.
 */
public class SpecialRuleModelTest {

  ThreeTriosModel reverseModel;
  ThreeTriosModel fallenModel;
  ThreeTriosModel comboModel;

  @Before
  public void init() {
    try {
      IBattleRule reverse = new ReverseBattleRule(new DefaultBattleRule());
      IBattleRule fallen = new FallenAceRule(new DefaultBattleRule());
      IBattleRule combo = new ReverseBattleRule(new FallenAceRule(new DefaultBattleRule()));


      ArrayList<ArrayList<Cell>> noHolesBoard = new Reader()
              .createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      ArrayList<ICard> cards = new Reader()
              .createHands("src/cs3500/threetrios/model/17Cards");

      ArrayList<ArrayList<Cell>> noHolesBoard2 = new Reader()
              .createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      ArrayList<ICard> cards2 = new Reader()
              .createHands("src/cs3500/threetrios/model/17Cards");

      ArrayList<ArrayList<Cell>> noHolesBoard3 = new Reader()
              .createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      ArrayList<ICard> cards3 = new Reader()
              .createHands("src/cs3500/threetrios/model/17Cards");


      reverseModel = new ThreeTriosModel(new Random(1), noHolesBoard, cards, reverse);
      fallenModel = new ThreeTriosModel(new Random(1), noHolesBoard3, cards3, fallen);
      comboModel = new ThreeTriosModel(new Random(1), noHolesBoard2, cards2, combo);
    } catch (IOException ignored) {
    }
  }

  @Test
  public void testBattleMechanicReverse() {
    reverseModel.placeCard(0, 0, 0);
    assertEquals("R", ((CardCell)reverseModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", reverseModel.getTurn());
    reverseModel.placeCard(0, 1, 0);
    assertEquals("B", ((CardCell)reverseModel.getGrid().get(0).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleMechanicFallen() {
    fallenModel.placeCard(0, 0, 0);
    assertEquals("R", ((CardCell)fallenModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", fallenModel.getTurn());
    fallenModel.placeCard(0, 1, 0);
    assertEquals("B", ((CardCell)fallenModel.getGrid().get(0).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleMechanicCombo() {
    comboModel.placeCard(0, 0, 0);
    assertEquals("R", ((CardCell)comboModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", comboModel.getTurn());
    comboModel.placeCard(0, 1, 0);
    assertEquals("B", ((CardCell)comboModel.getGrid().get(0).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleRecursionReverse() {
    ThreeTriosView view = new ThreeTriosView(reverseModel, new StringBuilder());
    System.out.println(reverseModel.getPlayerHand("R"));
    System.out.println(reverseModel.getPlayerHand("B"));
    reverseModel.placeCard(0, 0, 0);
    reverseModel.placeCard(0, 1, 0);
    reverseModel.placeCard(1, 0, 0);
    reverseModel.placeCard(1, 1, 0);

    assertEquals("B", ((CardCell) reverseModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) reverseModel.getGrid().get(0).get(1)).getCard().getOwner());
    assertEquals("R", ((CardCell) reverseModel.getGrid().get(1).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) reverseModel.getGrid().get(1).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleRecursionFallen() {
    ThreeTriosView view = new ThreeTriosView(fallenModel, new StringBuilder());
    System.out.println(fallenModel.getPlayerHand("R"));
    System.out.println(fallenModel.getPlayerHand("B"));
    fallenModel.placeCard(0, 0, 0);
    fallenModel.placeCard(0, 1, 0);
    fallenModel.placeCard(1, 0, 0);
    fallenModel.placeCard(1, 1, 0);

    assertEquals("B", ((CardCell) fallenModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) fallenModel.getGrid().get(0).get(1)).getCard().getOwner());
    assertEquals("R", ((CardCell) fallenModel.getGrid().get(1).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) fallenModel.getGrid().get(1).get(1)).getCard().getOwner());
  }

  @Test
  public void testBattleRecursionCombo() {
    ThreeTriosView view = new ThreeTriosView(comboModel, new StringBuilder());
    System.out.println(comboModel.getPlayerHand("R"));
    System.out.println(comboModel.getPlayerHand("B"));
    comboModel.placeCard(0, 0, 0);
    comboModel.placeCard(0, 1, 0);
    comboModel.placeCard(1, 0, 0);
    comboModel.placeCard(1, 1, 0);

    assertEquals("B", ((CardCell) comboModel.getGrid().get(0).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) comboModel.getGrid().get(0).get(1)).getCard().getOwner());
    assertEquals("R", ((CardCell) comboModel.getGrid().get(1).get(0)).getCard().getOwner());
    assertEquals("B", ((CardCell) comboModel.getGrid().get(1).get(1)).getCard().getOwner());
  }
}
