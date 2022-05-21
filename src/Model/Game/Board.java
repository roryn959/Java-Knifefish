package Model.Game;

import Model.DataStructures.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Board implements BoardInterface {
    private int[] board;
    private boolean[] castlePermissions;
    // permissions -> [white queenside, white kingside, black queenside, black kingside]
    private int enPassantSquare;
    private boolean inProgress;
    private int result;
    /*
    Results:
    -1 -> black win
    0 -> draw
    1 -> white win
 */
    private boolean whiteTurn;

    private HashMap<Integer, ArrayList<Integer>> piecePositions;

    public Board(){
        this.board = BoardConstants.getStartingBoard();
        this.piecePositions = BoardConstants.getStartingPiecePositions();
        this.castlePermissions = new boolean[] {true, true, true, true};
        this.inProgress = true;
        this.whiteTurn = true;
        this.enPassantSquare = 0; // 0 means no en passant squares are present
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
        this.enPassantSquare = 0;
        if (m.isCastleMove()){
            this.makeCastleMove(m);
        } else if (m.isEnpassant()){
            this.makeEnpassantMove(m);
        } else {
            this.makeNormalMove(m);
        }
        this.whiteTurn = !this.whiteTurn;
    }

    private void makeCastleMove(Move m){
        // Move source square will be 0 if white, 1 if black.
        // Move destination square will be 0 if queenside, 1 if kingside
        if (m.getSourceSquare()==0){
            if (m.getDestinationSquare()==0){
                this.board[95] = 0;
                this.piecePositions.get(6).remove(0);
                this.board[93] = 6;
                this.piecePositions.get(6).add(93);
                this.board[91] = 0;
                this.piecePositions.get(4).remove((Integer) 91);
                this.board[94] = 4;
                this.piecePositions.get(4).add(94);
            } else {
                this.board[95] = 0;
                this.piecePositions.get(6).remove(0);
                this.board[97] = 6;
                this.piecePositions.get(6).add(97);
                this.board[98] = 0;
                this.piecePositions.get(4).remove((Integer) 98);
                this.board[96] = 4;
                this.piecePositions.get(4).add(96);
            }
            this.castlePermissions[0] = false;
            this.castlePermissions[1] = false;
        } else {
            if (m.getDestinationSquare()==0){
                this.board[25] = 0;
                this.piecePositions.get(-6).remove(0);
                this.board[23] = 6;
                this.piecePositions.get(-6).add(23);
                this.board[21] = 0;
                this.piecePositions.get(-4).remove((Integer) 21);
                this.board[24] = 4;
                this.piecePositions.get(-4).add(24);
            } else {
                this.board[25] = 0;
                this.piecePositions.get(-6).remove(0);
                this.board[27] = 6;
                this.piecePositions.get(-6).add(27);
                this.board[28] = 0;
                this.piecePositions.get(-4).remove((Integer) 28);
                this.board[26] = 4;
                this.piecePositions.get(-4).add(26);
            }
            this.castlePermissions[2] = false;
            this.castlePermissions[3] = false;
        }
    }

    private void makeEnpassantMove(Move m){
        int takingPiece = this.board[m.getSourceSquare()];
        this.board[m.getDestinationSquare()] = takingPiece;
        this.piecePositions.get(takingPiece).add(m.getDestinationSquare());
        this.board[m.getSourceSquare()] = 0;
        this.piecePositions.get(takingPiece).remove((Integer) m.getSourceSquare());

        // If it's white making a move
        if (takingPiece > 0){
            this.board[m.getDestinationSquare()+10] = 0;
            this.piecePositions.get(takingPiece*-1).remove((Integer) m.getDestinationSquare()+10);
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
        } else if (pieceThatMoved == 1){
            // Add en passant square if applicable
            if (m.getSourceSquare() - m.getDestinationSquare() == 20){
                this.enPassantSquare = m.getDestinationSquare() + 10;
            }
        } else if (pieceThatMoved == -1){
            if (m.getDestinationSquare() - m.getSourceSquare() == 20){
                this.enPassantSquare = m.getSourceSquare() + 10;
            }
        }
    }

    public ArrayList<Move> generateMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> positions;
        int newPosition, pieceAtNewSquare, newSquare;
        if (this.whiteTurn){
            // White pawn moves
            positions = this.piecePositions.get(1);
            for (Integer position : positions){
                // If square ahead of pawn is empty, can move there
                if (this.board[position-10] == 0){
                    moves.add(new Move(position, position-10, false, false));
                    // If both two squares ahead of pawn empty, can move
                    if (this.board[position-20] == 0){
                        moves.add(new Move(position, position-20, false, false));
                    }
                }
                // Check pawn captures
                if (this.board[position-9] < 0){
                    moves.add(new Move(position, position-9, false, false));
                } else if (position-9 == this.enPassantSquare){
                    moves.add(new Move(position, position-9, false, true));
                }
                if (this.board[position-11] < 0){
                    moves.add(new Move(position, position-11, false, false));
                } else if (position-11 == this.enPassantSquare){
                    moves.add(new Move(position, position-11, false, true));
                }
            }
            // White knight moves
            positions = this.piecePositions.get(2);
            for (int position : positions){
                for (int difference : BoardConstants.KNIGHTMOVES){
                    newSquare = position + difference;
                    if (this.board[newSquare] <= 0){
                        moves.add(new Move(position, newSquare, false, false));
                    }
                }
            }
            // White bishop moves
            positions = this.piecePositions.get(3);
            for (int position : positions){
                // For all different directions the bishop can go
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare<0){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // White rook moves
            positions = this.piecePositions.get(4);
            for (int position : positions){
                // For all the different positions the rook can go
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare<0){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // White queen moves
            positions = this.piecePositions.get(5);
            for (int position : positions){
                // Bishop-like queen moves
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare<0){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
                // Rook-like queen moves
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare<0){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // White king moves
            int position = this.piecePositions.get(6).get(0); // There's only one king
            for (int direction : BoardConstants.KINGMOVES){
                newSquare = position + direction;
                if (this.board[newSquare] <= 0){
                    moves.add(new Move(position, newSquare, false, false));
                }
            }
        } else {
            // Black pawn moves
            positions = this.piecePositions.get(-1);
            for (Integer position : positions){
                // If square ahead of pawn is empty, can move there
                if (this.board[position+10] == 0){
                    moves.add(new Move(position, position+10, false, false));
                    // If both two squares ahead of pawn empty, can move
                    if (this.board[position+20] == 0){
                        moves.add(new Move(position, position+20, false, false));
                    }
                }
                // Check pawn captures
                newSquare = position+9;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare > 0 && pieceAtNewSquare != 69){
                    moves.add(new Move(position, newSquare, false, false));
                } else if (position+9 == this.enPassantSquare){
                    moves.add(new Move(position, newSquare, false, true));
                }
                newSquare = position+11;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare > 0 && pieceAtNewSquare != 69){
                    moves.add(new Move(position, newSquare, false, false));
                } else if (newSquare == this.enPassantSquare){
                    moves.add(new Move(position, newSquare, false, true));
                }
            }
            // Black knight moves
            positions = this.piecePositions.get(-2);
            for (int position : positions){
                for (int difference : BoardConstants.KNIGHTMOVES){
                    newSquare = position + difference;
                    pieceAtNewSquare = this.board[newSquare];
                    if (pieceAtNewSquare >= 0 && pieceAtNewSquare != 69){
                        moves.add(new Move(position, newSquare, false, false));
                    }
                }
            }
            // Black bishop moves
            positions = this.piecePositions.get(-3);
            for (int position : positions){
                // For all different directions the bishop can go
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != 69){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black rook moves
            positions = this.piecePositions.get(-4);
            for (int position : positions){
                // For all the different positions the rook can go
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != 69){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black queen moves
            positions = this.piecePositions.get(-5);
            for (int position : positions){
                // Bishop-like queen moves
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != 69){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
                // Rook-like queen moves
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == 0){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != 69){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black king moves
            int position = this.piecePositions.get(-6).get(0); // There's only one king
            for (int direction : BoardConstants.KINGMOVES){
                newSquare = position + direction;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare >= 0 && pieceAtNewSquare != 69){
                    moves.add(new Move(position, newSquare, false, false));
                }
            }
        }

        return moves;
    }

    public void undoMove(){
        System.out.println("implement undo move");
    }

    public void display(){
        System.out.println("\n\n*** Displaying board ***");
        if (!this.inProgress) {
            System.out.print("Model.Game terminated with outcome ");
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

        ArrayList<Move> moves = this.generateMoves();
        System.out.println("Possible moves in this position:");
        for (Move m : moves){
            m.display();
        }
    }
}