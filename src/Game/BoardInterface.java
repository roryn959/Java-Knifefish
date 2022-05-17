package Game;

import DataStructures.Move;

public interface BoardInterface {
    public int[] getBoard();
    public boolean[] getCastlePermissions();
    public boolean isInProgress();
    public int getResult();
    public boolean isWhiteTurn();
    public void makeMove(Move m);
    public void undoMove();

}
