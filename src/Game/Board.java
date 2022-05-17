package Game;

import DataStructures.Move;

public class Board implements BoardInterface {
    private int[] board;
    private boolean[] castlePermissions;
    // permissions -> [white queenside, white kingside, black queenside, black kingside]
    private boolean inProgress;
    private int result;
    /*
    Results:
    -1 -> black win
    0 -> draw
    1 -> white win
 */
    private boolean whiteTurn;

    public Board(){
        this.board = BoardConstants.getStartingBoard();
        this.castlePermissions = new boolean[] {true, true, true, true};
        this.inProgress = true;
        this.whiteTurn = true;
    }

    public int[] getBoard(){
        return this.board.clone();
    }

    public boolean[] getCastlePermissions(){
        return this.castlePermissions.clone();
    }

    public boolean isInProgress(){
        return this.inProgress;
    }

    public int getResult(){
        return this.result;
    }

    public boolean isWhiteTurn(){
        return this.whiteTurn;
    }

    public void makeMove(Move m){
        if (m.isCastleMove()){
            this.makeCastleMove(m);
        } else if (m.isEnpassant()){
            this.makeEnpassantMove(m);
        } else {
            this.makeNormalMove(m);
        }
    }

    private void makeCastleMove(Move m){
        // Move source square will be 0 if white, 1 if black.
        // Move destination square will be 0 if queenside, 1 if kingside
        if (m.getSourceSquare()==0){
            if (m.getDestinationSquare()==0){
                this.board[95] = 0;
                this.board[93] = 6;
                this.board[91] = 0;
                this.board[94] = 4;
            } else {
                this.board[95] = 0;
                this.board[97] = 6;
                this.board[98] = 0;
                this.board[96] = 4;
            }
            this.castlePermissions[0] = false;
            this.castlePermissions[1] = false;
        } else {
            if (m.getDestinationSquare()==0){
                this.board[25] = 0;
                this.board[23] = 6;
                this.board[21] = 0;
                this.board[24] = 4;
            } else {
                this.board[25] = 0;
                this.board[27] = 6;
                this.board[28] = 0;
                this.board[26] = 4;
            }
            this.castlePermissions[2] = false;
            this.castlePermissions[3] = false;
        }
    }

    private void makeEnpassantMove(Move m){
        this.board[m.getDestinationSquare()] = this.board[m.getSourceSquare()];
        this.board[m.getSourceSquare()] = 0;

        // If it's white making a move
        if (this.board[m.getDestinationSquare()] > 0){
            this.board[m.getDestinationSquare()+10] = 0;
        } else {
            this.board[m.getDestinationSquare()-10] = 0;
        }
    }

    private void makeNormalMove(Move m){
        this.board[m.getDestinationSquare()] = this.board[m.getSourceSquare()];
        this.board[m.getSourceSquare()] = 0;

        int pieceThatMoved = this.board[m.getDestinationSquare()];
        // If it was the king that made the move, remove castle perms
        if (pieceThatMoved == 6){
            this.castlePermissions[0] = false;
            this.castlePermissions[1] = false;
        } else if (pieceThatMoved == -6){
            this.castlePermissions[2] = false;
            this.castlePermissions[3] = false;
        } else if (pieceThatMoved == 4){
            // If piece that moved was rook, remove corresponding castle perms
            if (m.getSourceSquare() == 91){
                this.castlePermissions[0] = false;
            } else if (m.getSourceSquare() == 98){
                this.castlePermissions[1] = false;
            }
        } else if (pieceThatMoved == -4){
            if (m.getSourceSquare() == 21){
                this.castlePermissions[2] = false;
            } else if (m.getSourceSquare() == 28){
                this.castlePermissions[3] = false;
            }
        }
    }

    public void undoMove(){
        System.out.println("implement undo move");
    }

    public void display(){
        System.out.println("\n\n*** Displaying board ***");
        if (!this.inProgress) {
            System.out.print("Game terminated with outcome ");
            System.out.println(this.result);
        }

        System.out.print("Castling permissions: ");
        for (int i=0; i<this.castlePermissions.length; i++){
            System.out.print(this.castlePermissions[i]);
        }
        System.out.print('\n');

        for (int i=0; i<this.board.length; i++){
            if (this.board[i] != 69){
                System.out.print(this.board[i]);
                if (i%10 == 8){
                    System.out.print('\n');
                }
            }
        }
    }
}
