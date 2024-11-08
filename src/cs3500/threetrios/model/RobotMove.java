package cs3500.threetrios.model;

public class RobotMove {
  private final int row;
  private final int col;
  private final int handIdx;

  public RobotMove(int row, int col, int handIdx) {
    this.row = row;
    this.col = col;
    this.handIdx = handIdx;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getHandIdx() {
    return handIdx;
  }
}

