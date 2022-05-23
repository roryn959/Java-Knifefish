package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.Game.Board;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer {
    public static final Move findMove(Board board){
        LinkedList_<Move> possibleMoves = board.generateMoves();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, possibleMoves.getLength());
        Move randomMove = possibleMoves.getItemByIndex(randomIndex);
        return randomMove;
    }
}
