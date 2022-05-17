package DataStructures;

public class Move {
    private int sourceSquare;
    private int destinationSquare;
    private boolean castleMove;
    private boolean enpassant;

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

    public void display() {
        System.out.println("Printing move: ");
        System.out.println(this.sourceSquare);
        System.out.println(this.destinationSquare);
        System.out.println(this.castleMove);
        System.out.println(this.enpassant);
    }
}
