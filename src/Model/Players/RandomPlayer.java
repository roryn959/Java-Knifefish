package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.Game.Board;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer implements PlayerInterface {
    private Board board;
    public RandomPlayer(Board board){
        this.board = board;
    }

    public Move findMove(){
        LinkedList_<Move> possibleMoves = this.board.generateMoves();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, possibleMoves.getLength());
        Move randomMove = possibleMoves.getItemByIndex(randomIndex);
        return randomMove;
    }
}
