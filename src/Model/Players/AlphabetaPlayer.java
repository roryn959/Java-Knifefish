package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;

public class AlphabetaPlayer implements PlayerInterface {
    private Board board;
    //private EvaluationInterface evaluator;
    private AdvancedEvaluation evaluator;
    private final int DEPTH = 5;
    private int iterations;

    public AlphabetaPlayer(Board board){
        this.board = board;
        this.evaluator = new AdvancedEvaluation(board);
    }

    public Move findMove(){
        if (this.board.getMoveCount()<6){
            return this.getBookMove();
        } else {
            this.iterations = 0;
            Tuple<Move, Integer> outcome = this.alphabeta(this.DEPTH, -40000, 40000);
            System.out.print("Iterations: ");
            System.out.println(this.iterations);
            System.out.print("Current evaluation: ");
            System.out.println(this.evaluator.evaluate());
            System.out.print("Predicted evaluation: ");
            System.out.println(outcome.y);
            return outcome.x;
        }
    }

    private Move getBookMove(){
        int moveNum = this.board.getMoveCount();
        Move move;

        if (moveNum==0){
            move = new Move(97, 76, false, false);
        } else if (moveNum==1){
            move = new Move(27, 46, false, false);
        } else if (moveNum==2){
            move = new Move(84, 74, false, false);
        } else if (moveNum==3){
            move = new Move(34, 44, false, false);
        } else if (moveNum==4){
            move = new Move(87, 77, false, false);
        } else if (moveNum==5){
            move = new Move(37, 47, false, false);
        } else {
            move = null;
        }
        return move;
    }

    private Tuple<Move, Integer> alphabeta(int depth, int alpha, int beta){
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
