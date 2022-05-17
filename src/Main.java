import DataStructures.Move;
import Game.Board;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        //e4
        Move m0 = new Move(85, 65, false, false);
        //e5
        Move m1 = new Move(35, 55, false, false);
        //Nf3
        Move m2 = new Move(97, 76, false, false);
        //c5
        Move m3 = new Move(33, 53, false, false);
        //Ba6
        Move m4 = new Move(96, 41, false, false);
        //c4
        Move m5 = new Move(53, 63, false, false);
        //white ks castle
        Move m6 = new Move(98, 97, false, false);
        //h6
        Move m7 = new Move(38, 48, false, false);
        //d4
        Move m8 = new Move(84, 64, false, false);
        //cxd5
        Move m9 = new Move(63, 74, false, true);
        //Ke7
        Move m10 = new Move(25, 35, false, false);

        Move[] moves = new Move[] {m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10};
        board.display();
        for (Move m : moves){
            board.makeMove(m);
            board.display();
        }
    }
}
