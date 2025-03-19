package cs3500.threetrios.controller;

import cs3500.threetrios.model.ICard;
import cs3500.threetrios.model.IThreeTriosModel;
import cs3500.threetrios.provider.controller.TripleTriadController;
import cs3500.threetrios.view.IPlayerView;
import cs3500.threetrios.model.RobotMove;

import java.util.ArrayList;

/**
 * This class represents a controller for each player.
 *
 * IGNORE TripleTriadController, this was for an adapter pattern assignment in class.
 */
public class ThreeTriosController implements Features, TripleTriadController {

  private final IThreeTriosModel model;
  private final IPlayerView view;
  private final Player player;
  private int handIdx;


  /**
   * Initializes a controller for a Player in ThreeTrios.
   *
   *
   * @param model the model the controller is getting info from.
   * @param player the view the controller is sending info to and getting info from.
   * @param view the view that is sending and receiving info from the controller.
   */
  public ThreeTriosController(IThreeTriosModel model, Player player, IPlayerView view) {
    this.view = view;
    this.model = model;
    this.player = player;
    this.view.addFeaturesListener(this);
    this.handIdx = -1;
    this.model.addFeaturesListener(this);
  }

  @Override
  public void selectCard(int handIdx) {
    this.handIdx = handIdx;
    ArrayList<ICard> hand = model.getPlayerHand(player.getColor().toString());
    view.selectedCard(hand.get(handIdx));
  }

  @Override
  public boolean placeCard(int row, int col) {
    if (this.handIdx != -1) {
      try {
        model.placeCard(row, col, handIdx);
      } catch (Exception e) {
        view.error("Cannot place a card here!");
      }
      this.view.resetHighlight();
      this.handIdx = -1;
      return true;
    } else {
      view.error("Pick a card from the hand first.");
      return false;
    }
  }

  @Override
  public void setTurn(boolean turn) {
    if (turn && player instanceof MachinePlayer) {
      this.robotPlay();
    }
    this.view.setEnabled(turn);
    this.view.blurScreen(!turn);
    this.view.refresh();
  }

  @Override
  public void gameOver() {
    String winner = model.whoWonGame();
    this.view.setEnabled(false);
    this.view.blurScreen(true);
    this.view.displayEndGame(winner);
  }

  private void robotPlay() {
    if (!model.isGameOver()) {
      RobotMove move = ((MachinePlayer) player).getNextMove();

      this.selectCard(move.getHandIdx());
      this.placeCard(move.getRow(), move.getCol());
    }
  }

  @Override
  public int handleBoardClick(int x, int y, int viewWidth, int viewHeight, int selectedCardIndex) {

    int boardWidth = viewWidth / 2;
    int handWidth = viewWidth / 4;

    int cellWidth = viewHeight / model.getRows(); // Board cell width
    int cellHeight = boardWidth / model.getCols(); // Board cell height

    // Determine if the click is on the Red Player's hand (left side)
    if (x < handWidth && player.getColor() == PlayerColor.R) {
      int cardHeight = viewHeight / Math.max(1, model.getPlayerHand("R").size());
      int cardIndex = y / cardHeight;
      this.selectCard(cardIndex);
      view.refresh();
      return cardIndex;
    }

    // Determine if the click is on the Blue Player's hand (right side)
    if (x >= (viewWidth - handWidth) && player.getColor() == PlayerColor.B) {
      int cardHeight = viewHeight / Math.max(1, model.getPlayerHand("B").size());
      int cardIndex = y / cardHeight;
      this.selectCard(cardIndex);
      view.refresh();
      return cardIndex;
    }

    // Determine if the click is on the board
    if (x >= handWidth && x < (viewWidth - handWidth)) {
      int col = (x - handWidth) / cellHeight;
      int row = y / cellWidth;

      if (selectedCardIndex != -1) {
        this.placeCard(row, col);
      }
      view.refresh();
      return -1; // Reset selectedCardIndex after placing the card
    }

    // If the click is outside the defined areas, do nothing
    view.refresh();
    return selectedCardIndex;
  }

}
