package cs3500.threetrios;

import cs3500.threetrios.controller.Player;
import cs3500.threetrios.controller.MachinePlayer;
import cs3500.threetrios.controller.HumanPlayer;
import cs3500.threetrios.controller.PlayerColor;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.PlusModelVariant;
import cs3500.threetrios.model.SameModelVariant;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Reader;
import cs3500.threetrios.model.IBattleRule;
import cs3500.threetrios.model.DefaultBattleRule;
import cs3500.threetrios.model.ReverseBattleRule;
import cs3500.threetrios.model.FallenAceRule;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.FlipMaxCards;
import cs3500.threetrios.model.CornersFirst;
import cs3500.threetrios.view.PlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main class to run a game of ThreeTrios.
 * Sets up the View and Model, and runs the game and any commands to play the game.
 */
public class ThreeTrios {
  /**
   * Sets up the View and Model, and runs the game and any commands to play the game.
   * In order to quick start from the command line in the JAR file,
   * pick two of the following in the command line:
   * - human
   * - strategy1
   * - strategy2
   * example:
   * java -jar out/artifacts/ThreeTrios_jar/ThreeTrios.jar human strategy1
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {

    if (args.length < 2) {
      throw new IllegalArgumentException("Need two arguments");
    }

    try {
      ArrayList<ArrayList<Cell>> grid = new Reader()
              .createGrid("src/cs3500/threetrios/model/NoHolesBoard");
      ArrayList<ICard> cards = new Reader().createHands("src/cs3500/threetrios/model/10Cards");

      IBattleRule rule = new DefaultBattleRule();
      int sameOrPlusVariant = -1; //0 for same strategy, 1 for plus strategy, -1 for normal

      if (args.length > 2) {
        for (int i = args.length - 1; i >= 2; i--) {
          switch (args[i]) {
            case "reverse":
              rule = new ReverseBattleRule(rule);
              break;
            case "fallen-ace":
              rule = new FallenAceRule(rule);
              break;
            case "same":
              sameOrPlusVariant = 0;
              break;
            case "plus":
              sameOrPlusVariant = 1;
              break;
            default:
              throw new IllegalArgumentException("Unknown strategy " + args[1]);
          }
        }
      }

      //rand for reproducibility, can remove if needed.
      Random rand = new Random(1);
      ThreeTriosModel model;

      switch (sameOrPlusVariant) {
        case -1:
          model = new ThreeTriosModel(rand, grid, cards, rule);
          break;
        case 0:
          model = new SameModelVariant(rand, grid, cards, rule);
          break;
        case 1:
          model = new PlusModelVariant(rand, grid, cards, rule);
          break;
        default:
          model = new ThreeTriosModel(rand, grid, cards, rule);
          break;
      }

      Player player1 = null;
      Player player2 = null;

      switch (args[0]) {
        case "human":
          player1 = new HumanPlayer(PlayerColor.R);
          break;
        case "strategy1":
          player1 = new MachinePlayer(model, PlayerColor.R, new FlipMaxCards());
          break;
        case "strategy2":
          player1 = new MachinePlayer(model, PlayerColor.R, new CornersFirst());
          break;
        default:
          throw new IllegalArgumentException("Unknown strategy " + args[0]);
      }

      switch (args[1]) {
        case "human":
          player2 = new HumanPlayer(PlayerColor.B);
          break;
        case "strategy1":
          player2 = new MachinePlayer(model, PlayerColor.B, new FlipMaxCards());
          break;
        case "strategy2":
          player2 = new MachinePlayer(model, PlayerColor.B, new CornersFirst());
          break;
        default:
          throw new IllegalArgumentException("Unknown strategy " + args[1]);
      }


      PlayerView viewPlayer1 = new PlayerView(model, player1, player2);
      //ProviderModelAdapter adaptedModel = new ProviderModelAdapter(model);
      //TTGraphicsView adaptedView = new TTGraphicsView(adaptedModel);
      //ProviderViewAdapter viewPlayer2 = new ProviderViewAdapter(adaptedView);
      PlayerView viewPlayer2 = new PlayerView(model, player2, player1);

      Features controller1 = new ThreeTriosController(model, player1, viewPlayer1);
      Features controller2 = new ThreeTriosController(model, player2, viewPlayer2);

      model.startGame();
    } catch (IOException ignored) {
      throw new IllegalArgumentException("Could not read files");
    }
  }
}
