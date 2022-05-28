package Model.DataStructures;

public class Move {
    private final int sourceSquare;
    private final int destinationSquare;
    private final boolean castleMove;
    private final boolean enpassant;

    public Move(int sourceSquare, int destinationSquare, boolean castleMove, boolean enpassant){
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.castleMove = castleMove;
        this.enpassant = enpassant;
    }

    public int getSourceSquare(){
        return this.sourceSquare;
    }

    public int getDestinationSquare(){
        return this.destinationSquare;
    }

    public boolean isCastleMove(){
        return this.castleMove;
    }
    public boolean isEnpassant(){
        return this.enpassant;
    }

    public boolean isEquivalent(Move m){
        return this.sourceSquare==m.getSourceSquare() && this.destinationSquare==m.getDestinationSquare() && this.castleMove==m.isCastleMove() && this.enpassant==m.isEnpassant();
    }

    public void display() {
        System.out.println(this.sourceSquare);
        System.out.println(this.destinationSquare);
        System.out.println(this.castleMove);
        System.out.println(this.enpassant);
    }
}
