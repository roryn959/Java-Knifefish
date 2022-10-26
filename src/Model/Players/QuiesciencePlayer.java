package Model.Players;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;
import Model.DataStructures.Tuple;
import Model.Game.Board;

public class QuiesciencePlayer implements PlayerInterface {
    private Board board;
    private EvaluationInterface evaluator;
    private final int DEPTH = 4;
    private final int Q_DEPTH = 5;
    private int iterations;
    private int qiterations;

    public QuiesciencePlayer(Board board){
        this.board = board;
        this.evaluator = new AdvancedEvaluation(board);
    }

    public Move findMove(){
        Move move;
        if (this.board.getMoveCount()<6){
            move = this.getBookMove();
        } else {
            this.iterations = 0;
            this.qiterations = 0;
            move = this.alphabeta(this.DEPTH, -40000, 40000).x;
            System.out.print("Iterations: ");
            System.out.println(iterations);
            System.out.print("Q iterations: ");
            System.out.println(this.qiterations);
        }
        return move;
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
        // If terminal node or we've reached the max depth
        if (this.board.gameOver()){
            return new Tuple<>(null, this.evaluator.evaluate());
        }
        // If we have reached the first limit of our search
        if (depth==0){
            return this.quiescence(alpha, beta, this.Q_DEPTH);
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
                if (bestScore>=beta){
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

    private Tuple<Move, Integer> quiescence(int alpha, int beta, int depth){
        this.qiterations++;

        // Check if terminal node
        if (this.board.gameOver()){
            return new Tuple<>(null, this.evaluator.evaluate());
        }
        int stand_pat = this.evaluator.evaluate();

        if (this.board.isWhiteTurn()){
            if (stand_pat >= beta){
                return new Tuple<>(null, beta);
            }
            if (alpha < stand_pat){
                alpha = stand_pat;
            }
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> captures = this.board.generateCaptures();
            if (captures.getLength()==0){
                return new Tuple<>(null, stand_pat);
            }
            for (Move m : captures){
                this.board.makeMove(m);
                outcome = this.quiescence(alpha, beta, depth-1);
                this.board.undoMove();
                if (outcome.y >= beta){
                    return new Tuple<>(null, beta);
                }
                if (outcome.y > alpha){
                    alpha = outcome.y;
                }
            }
            return new Tuple<>(null, alpha);
        }
        else {
            if (stand_pat <= alpha){
                return new Tuple<>(null, alpha);
            }
            if (beta > stand_pat){
                beta = stand_pat;
            }
            Tuple<Move, Integer> outcome;
            LinkedList_<Move> captures = this.board.generateCaptures();
            if (captures.getLength()==0){
                return new Tuple<>(null, stand_pat);
            }
            for (Move m : captures){
                this.board.makeMove(m);
                outcome = this.quiescence(alpha, beta, depth-1);
                this.board.undoMove();
                if (outcome.y <= alpha){
                    return new Tuple<>(null, alpha);
                }
                if (outcome.y < beta){
                    beta = outcome.y;
                }
            }
            return new Tuple<>(null, beta);
        }
    }
}
