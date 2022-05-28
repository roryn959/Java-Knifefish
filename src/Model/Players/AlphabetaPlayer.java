package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;

public class AlphabetaPlayer implements PlayerInterface {
    private Board board;
    private EvaluationInterface evaluator;
    private final int DEPTH = 6;
    public AlphabetaPlayer(Board board){
        this.board = board;
        this.evaluator = new SimpleEvaluation(board);
    }

    public Move findMove(){
        Tuple<Move, Integer> outcome = this.alphabeta(this.DEPTH, -40000, 40000);
        return outcome.x;
    }

    private Tuple<Move, Integer> alphabeta(int depth, int alpha, int beta){
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
                outcome = this.alphabeta(depth-1, alpha, beta);
                this.board.undoMove();
                if (outcome.y > bestScore){
                    bestScore = outcome.y;
                    bestMove = m;
                }
                if (bestScore >= beta){
                    // Beta cutoff
                    break;
                }
                // alpha = max(bestScore, alpha)
                if (bestScore>alpha){
                    alpha = bestScore;
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
                outcome = this.alphabeta(depth-1, alpha, beta);
                this.board.undoMove();
                if (outcome.y < bestScore){
                    bestScore = outcome.y;
                    bestMove = m;
                }
                if (bestScore <= alpha){
                    // Alpha cutoff
                    break;
                }
                // beta = min(bestScore, beta)
                if (bestScore < beta){
                    beta = bestScore;
                }
            }
            return new Tuple<>(bestMove, bestScore);
        }
    }

}
