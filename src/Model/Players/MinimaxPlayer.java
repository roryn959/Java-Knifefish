package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;

public class MinimaxPlayer implements PlayerInterface {
    private Board board;
    private final int DEPTH = 5;
    private final EvaluationInterface evaluator;
    private int iterations;

    public MinimaxPlayer(Board board){
        this.board = board;
        this.evaluator = new SimpleEvaluation(this.board);
    }

    public Move findMove(){
        this.iterations = 0;
        Tuple<Move, Integer> outcome = this.minimax(this.DEPTH);
        System.out.print("Iterations: ");
        System.out.println(iterations);
        return outcome.x;
    }

    public Tuple<Move, Integer> minimax(int depth){
        this.iterations++;
        if (depth==0 || this.board.gameOver()){
            return new Tuple<>(null, this.evaluator.evaluate());
        }
        if (this.board.isWhiteTurn()){
            int bestScore = -40000;
            Move bestMove = null;
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> possibleMoves = this.board.generateMoves();
            for (Move m : possibleMoves){
                this.board.makeMove(m);
                outcome = this.minimax(depth-1);
                this.board.undoMove();
                if (outcome.y > bestScore){
                    // If we have found a better move
                    bestScore = outcome.y;
                    bestMove = m;
                }
            }
            return new Tuple<>(bestMove, bestScore);
        } else {
            int bestScore = 40000;
            Move bestMove = null;
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> possibleMoves = this.board.generateMoves();
            for (Move m : possibleMoves){
                this.board.makeMove(m);
                outcome = this.minimax(depth-1);
                this.board.undoMove();
                if (outcome.y < bestScore){
                    bestScore = outcome.y;
                    bestMove = m;
                }
            }
            return new Tuple<>(bestMove, bestScore);
        }
    }
}
