package cs3500.threetrios;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.MachinePlayer;
import cs3500.threetrios.controller.HumanPlayer;
import cs3500.threetrios.controller.Player;
import cs3500.threetrios.controller.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Reader;
import cs3500.threetrios.model.RobotMove;
import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.CornersFirst;
import cs3500.threetrios.model.FlipMaxCards;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Mock tests for the controller, as well as tests for Player implementations.
 */
public class ControllerTest {
  @Test
  public void testSetTurn() {
    StringBuilder log = new StringBuilder();
    MockModelForControllerTest mockModel = new MockModelForControllerTest(log);
    MockViewForControllerTest mockView = new MockViewForControllerTest(log, mockModel);
    ThreeTriosController controller = new ThreeTriosController(mockModel, null, mockView);

    controller.setTurn(true);

    String expectedLog = "addFeaturesListener called\n"
                + "addFeaturesListener called\n"
                + "setEnabled called with enabled=true\n"
                + "blurScreen called with blur=false\n"
                + "refresh called\n";


    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testGameOver() {
    StringBuilder log = new StringBuilder();
    MockModelForControllerTest mockModel = new MockModelForControllerTest(log);
    MockViewForControllerTest mockView = new MockViewForControllerTest(log, mockModel);
    ThreeTriosController controller = new ThreeTriosController(mockModel, null, mockView);

    controller.gameOver();

    String expectedLog =
                "addFeaturesListener called\n"
                + "addFeaturesListener called\n"
                + "whoWonGame called\n"
                + "setEnabled called with enabled=false\n"
                + "blurScreen called with blur=true\n"
                + "displayEndGame called with winner=Player RED\n";

    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testPlaceCard() {
    StringBuilder log = new StringBuilder();
    MockModelForControllerTest mockModel = new MockModelForControllerTest(log);
    MockViewForControllerTest mockView = new MockViewForControllerTest(log, mockModel);
    ThreeTriosController controller = new ThreeTriosController(mockModel, null, mockView);

    controller.selectCard(1);
    controller.placeCard(0, 0);

    String expectedLog =
                "addFeaturesListener called\n"
                + "addFeaturesListener called\n"
                + "placeCard called with row=0, col=0, handIdx=1\n"
                + "resetHighlight called\n";

    assertEquals(expectedLog, log.toString());
  }

  //PLAYER TESTS

  @Test
  public void testHumanPlayer() {
    Player human = new HumanPlayer(PlayerColor.R);
    assertEquals(PlayerColor.R, human.getColor());
    assertEquals("R", human.getColor().toString());
  }

  @Test
  public void testMachinePlayerColor() {
    StringBuilder log = new StringBuilder();
    MockModelForControllerTest mockModel = new MockModelForControllerTest(log);
    MachinePlayer machine = new MachinePlayer(mockModel, PlayerColor.R, new FlipMaxCards());
    assertEquals(PlayerColor.R, machine.getColor());
    assertEquals("R", machine.getColor().toString());
  }

  @Test
  public void testMachinePlayerFlipMaxCards() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());
    MachinePlayer machine = new MachinePlayer(model, PlayerColor.R, new FlipMaxCards());
    RobotMove move = machine.getNextMove();
    assertEquals(0, move.getHandIdx());
    assertEquals(0, move.getCol());
    assertEquals(0, move.getRow());
  }

  @Test
  public void testMachinePlayerCornersFirst() throws FileNotFoundException {
    Random random = new Random(1);
    ThreeTriosModel model = new ThreeTriosModel(random,
            new Reader().createGrid("src/cs3500/threetrios/model/NoHolesBoard"),
            new Reader().createHands("src/cs3500/threetrios/model/17Cards"),
            new DefaultBattleRule());
    MachinePlayer machine = new MachinePlayer(model, PlayerColor.R, new CornersFirst());
    RobotMove move = machine.getNextMove();

    //plays card that exposes least to the bottom right corner
    assertEquals(1, move.getHandIdx());
    assertEquals(2, move.getCol());
    assertEquals(2, move.getRow());
  }
}
