package Model.Game;

import Model.DataStructures.LinkedList_;
import Model.DataStructures.Move;

import java.util.HashMap;

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

    private HashMap<Integer, LinkedList_<Integer>> piecePositions;

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
                this.board[95] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.WHITE_KING).remove(95);
                this.board[93] = BoardConstants.WHITE_KING;
                this.piecePositions.get(BoardConstants.WHITE_KING).add(93);
                this.board[91] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.WHITE_ROOK).remove(91);
                this.board[94] = BoardConstants.WHITE_ROOK;
                this.piecePositions.get(BoardConstants.WHITE_ROOK).add(94);
            } else {
                this.board[95] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.WHITE_KING).remove(95);
                this.board[97] = BoardConstants.WHITE_KING;
                this.piecePositions.get(BoardConstants.WHITE_KING).add(97);
                this.board[98] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.WHITE_ROOK).remove(98);
                this.board[96] = BoardConstants.WHITE_ROOK;
                this.piecePositions.get(BoardConstants.WHITE_ROOK).add(96);
            }
            this.castlePermissions[0] = false;
            this.castlePermissions[1] = false;
        } else {
            if (m.getDestinationSquare()==0){
                this.board[25] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.BLACK_KING).remove(0);
                this.board[23] = BoardConstants.WHITE_KING;
                this.piecePositions.get(BoardConstants.BLACK_KING).add(23);
                this.board[21] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.BLACK_ROOK).remove(21);
                this.board[24] = BoardConstants.WHITE_ROOK;
                this.piecePositions.get(BoardConstants.BLACK_ROOK).add(24);
            } else {
                this.board[25] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.BLACK_KING).remove(0);
                this.board[27] = BoardConstants.WHITE_KING;
                this.piecePositions.get(BoardConstants.BLACK_KING).add(27);
                this.board[28] = BoardConstants.EMPTY;
                this.piecePositions.get(BoardConstants.BLACK_ROOK).remove(28);
                this.board[26] = BoardConstants.WHITE_ROOK;
                this.piecePositions.get(BoardConstants.BLACK_ROOK).add(26);
            }
            this.castlePermissions[2] = false;
            this.castlePermissions[3] = false;
        }
    }

    private void makeEnpassantMove(Move m){
        int takingPiece = this.board[m.getSourceSquare()];
        this.board[m.getDestinationSquare()] = takingPiece;
        this.piecePositions.get(takingPiece).add(m.getDestinationSquare());
        this.board[m.getSourceSquare()] = BoardConstants.EMPTY;
        this.piecePositions.get(takingPiece).remove(m.getSourceSquare());

        // If it's white making a move
        if (takingPiece > 0){
            this.board[m.getDestinationSquare()+10] = BoardConstants.EMPTY;
            this.piecePositions.get(takingPiece*-1).remove(m.getDestinationSquare()+10);
        } else {
            this.board[m.getDestinationSquare()-10] = BoardConstants.EMPTY;
            this.piecePositions.get(takingPiece*-1).remove(m.getDestinationSquare() -10);
        }
    }

    private void makeNormalMove(Move m){
        int movingPiece = this.board[m.getSourceSquare()];
        int capturedPiece = this.board[m.getDestinationSquare()];

        // If the move is a capture, then remove the square from captured piece's positions
        if (capturedPiece != BoardConstants.EMPTY){
            this.piecePositions.get(capturedPiece).remove(m.getDestinationSquare());
        }
        System.out.println(this.piecePositions.get(BoardConstants.BLACK_KING).getFirstItem());
        // Execute move
        this.board[m.getDestinationSquare()] = this.board[m.getSourceSquare()];
        this.board[m.getSourceSquare()] = BoardConstants.EMPTY;
        this.piecePositions.get(movingPiece).remove(m.getSourceSquare());
        this.piecePositions.get(movingPiece).add(m.getDestinationSquare());

        // If it was the king that made the move, remove castle perms
        if (movingPiece == BoardConstants.WHITE_KING){
            this.castlePermissions[0] = false;
            this.castlePermissions[1] = false;
        } else if (movingPiece == BoardConstants.BLACK_KING){
            this.castlePermissions[2] = false;
            this.castlePermissions[3] = false;
        } else if (movingPiece == BoardConstants.WHITE_ROOK){
            // If piece that moved was rook, remove corresponding castle perms
            if (m.getSourceSquare() == 91){
                this.castlePermissions[0] = false;
            } else if (m.getSourceSquare() == 98){
                this.castlePermissions[1] = false;
            }
        } else if (movingPiece == BoardConstants.BLACK_ROOK){
            if (m.getSourceSquare() == 21){
                this.castlePermissions[2] = false;
            } else if (m.getSourceSquare() == 28){
                this.castlePermissions[3] = false;
            }
        } else if (movingPiece == BoardConstants.WHITE_PAWN){
            // Add en passant square if applicable
            if (m.getSourceSquare() - m.getDestinationSquare() == 20){
                this.enPassantSquare = m.getDestinationSquare() + 10;
            }
        } else if (movingPiece == BoardConstants.BLACK_PAWN){
            if (m.getDestinationSquare() - m.getSourceSquare() == 20){
                this.enPassantSquare = m.getSourceSquare() + 10;
            }
        }
    }

    public LinkedList_<Move> generateMoves(){
        LinkedList_<Move> moves = new LinkedList_<>();
        LinkedList_<Integer> positions;
        int newPosition, pieceAtNewSquare, newSquare;
        if (this.whiteTurn){
            // White pawn moves
            positions = this.piecePositions.get(BoardConstants.WHITE_PAWN);
            for (Integer position : positions){
                // If square ahead of pawn is empty, can move there
                if (this.board[position-10] == BoardConstants.EMPTY){
                    moves.add(new Move(position, position-10, false, false));
                    // If both two squares ahead of pawn empty, can move
                    if (this.board[position-20] == BoardConstants.EMPTY){
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
            positions = this.piecePositions.get(BoardConstants.WHITE_KNIGHT);
            for (int position : positions){
                for (int difference : BoardConstants.KNIGHTMOVES){
                    newSquare = position + difference;
                    if (this.board[newSquare] <= 0){
                        moves.add(new Move(position, newSquare, false, false));
                    }
                }
            }
            // White bishop moves
            positions = this.piecePositions.get(BoardConstants.WHITE_BISHOP);
            for (int position : positions){
                // For all different directions the bishop can go
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
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
            positions = this.piecePositions.get(BoardConstants.WHITE_ROOK);
            for (int position : positions){
                // For all the different positions the rook can go
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
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
            positions = this.piecePositions.get(BoardConstants.WHITE_QUEEN);
            for (int position : positions){
                // Bishop-like queen moves
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
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
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
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
            int position = this.piecePositions.get(BoardConstants.WHITE_KING).getFirstItem(); // There's only one king
            for (int direction : BoardConstants.KINGMOVES){
                newSquare = position + direction;
                if (this.board[newSquare] <= 0){
                    moves.add(new Move(position, newSquare, false, false));
                }
            }
            // White queenside castle
            if (this.castlePermissions[0]){
                // If white has no pieces blocking the squares
                if (this.board[92]==BoardConstants.EMPTY && this.board[93]==BoardConstants.EMPTY && this.board[94]==BoardConstants.EMPTY){
                    // If black is not attacking king crossover squares
                    if (!this.isAttacked(93, false) && !this.isAttacked(94, false) && !this.isAttacked(95, false)){
                        moves.add(new Move(0, 0, true, false));
                    }
                }
            }
            // White kingside castle
            if (this.castlePermissions[1]){
                // If no pieces blocking
                if (this.board[96]==BoardConstants.EMPTY && this.board[97]==BoardConstants.EMPTY){
                    if (!this.isAttacked(95, false) && !this.isAttacked(96, false) && !this.isAttacked(97, false)){
                        moves.add(new Move(0, 1, true, false));
                    }
                }
            }
        } else {
            // Black pawn moves
            positions = this.piecePositions.get(BoardConstants.BLACK_PAWN);
            for (Integer position : positions){
                // If square ahead of pawn is empty, can move there
                if (this.board[position+10] == BoardConstants.EMPTY){
                    moves.add(new Move(position, position+10, false, false));
                    // If both two squares ahead of pawn empty, can move
                    if (this.board[position+20] == BoardConstants.EMPTY){
                        moves.add(new Move(position, position+20, false, false));
                    }
                }
                // Check pawn captures
                newSquare = position+9;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare > 0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                    moves.add(new Move(position, newSquare, false, false));
                } else if (position+9 == this.enPassantSquare){
                    moves.add(new Move(position, newSquare, false, true));
                }
                newSquare = position+11;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare > 0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                    moves.add(new Move(position, newSquare, false, false));
                } else if (newSquare == this.enPassantSquare){
                    moves.add(new Move(position, newSquare, false, true));
                }
            }
            // Black knight moves
            positions = this.piecePositions.get(BoardConstants.BLACK_KNIGHT);
            for (int position : positions){
                for (int difference : BoardConstants.KNIGHTMOVES){
                    newSquare = position + difference;
                    pieceAtNewSquare = this.board[newSquare];
                    if (pieceAtNewSquare >= 0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                        moves.add(new Move(position, newSquare, false, false));
                    }
                }
            }
            // Black bishop moves
            positions = this.piecePositions.get(BoardConstants.BLACK_BISHOP);
            for (int position : positions){
                // For all different directions the bishop can go
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black rook moves
            positions = this.piecePositions.get(BoardConstants.BLACK_ROOK);
            for (int position : positions){
                // For all the different positions the rook can go
                for (int direction : BoardConstants.ROOKMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black queen moves
            positions = this.piecePositions.get(BoardConstants.BLACK_QUEEN);
            for (int position : positions){
                // Bishop-like queen moves
                for (int direction : BoardConstants.BISHOPMOVES){
                    newPosition = position + direction;
                    while (true){
                        pieceAtNewSquare = this.board[newPosition];
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
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
                        if (pieceAtNewSquare == BoardConstants.EMPTY){
                            moves.add(new Move(position, newPosition, false, false));
                            newPosition = newPosition+direction;
                            continue;
                        } else if (pieceAtNewSquare>0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                            moves.add(new Move(position, newPosition, false, false));
                        }
                        break;
                    }
                }
            }
            // Black king moves
            int position = this.piecePositions.get(BoardConstants.BLACK_KING).getFirstItem(); // There's only one king
            for (int direction : BoardConstants.KINGMOVES){
                newSquare = position + direction;
                pieceAtNewSquare = this.board[newSquare];
                if (pieceAtNewSquare >= 0 && pieceAtNewSquare != BoardConstants.OUTSIDE_BOARD){
                    moves.add(new Move(position, newSquare, false, false));
                }
            }
            // Black queenside castle
            if (this.castlePermissions[2]){
                // If black has no pieces blocking the squares
                if (this.board[22]==BoardConstants.EMPTY && this.board[23]==BoardConstants.EMPTY && this.board[24]==BoardConstants.EMPTY){
                    // If white is not attacking king crossover squares
                    if (!this.isAttacked(23, true) && !this.isAttacked(24, true) && !this.isAttacked(25, true)){
                        moves.add(new Move(1, 0, true, false));
                    }
                }
            }
            // Black kingside castle
            if (this.castlePermissions[3]){
                // If no pieces blocking
                if (this.board[26]==BoardConstants.EMPTY && this.board[27]==BoardConstants.EMPTY){
                    if (!this.isAttacked(25, true) && !this.isAttacked(26, true) && !this.isAttacked(27, true)){
                        moves.add(new Move(1, 1, true, false));
                    }
                }
            }
        }
        return moves;
    }

    public boolean isAttacked(int square, boolean byWhite){
        int newSquare;
        int pieceAtSquare;
        if (byWhite){
            // Check is white is attacking

            // Check if white queen or bishop is on the diagonals
            for (int direction : BoardConstants.BISHOPMOVES){
                newSquare = square + direction;
                while (true){
                    pieceAtSquare = this.board[newSquare];
                    if (pieceAtSquare==BoardConstants.EMPTY){
                        newSquare = newSquare + direction;
                    } else if (pieceAtSquare==BoardConstants.WHITE_BISHOP || pieceAtSquare==BoardConstants.WHITE_QUEEN){
                        return true;
                    } else {
                        // At this stage either we have reached the edge of the board, or there is a piece there,
                        // but it's not a black bishop or queen so no attack from that diagonal.
                        break;
                    }
                }
            }
            // Check if white queen or rook on rank or file
            for (int direction : BoardConstants.ROOKMOVES){
                newSquare = square + direction;
                while (true){
                    pieceAtSquare = this.board[newSquare];
                    if (pieceAtSquare==BoardConstants.EMPTY){
                        newSquare = newSquare + direction;
                    } else if (pieceAtSquare==BoardConstants.WHITE_ROOK || pieceAtSquare==BoardConstants.WHITE_QUEEN){
                        return true;
                    } else {
                        break;
                    }
                }
            }
            // Check for white knight
            for (int direction : BoardConstants.KNIGHTMOVES){
                pieceAtSquare = this.board[square+direction];
                if (pieceAtSquare==BoardConstants.WHITE_KNIGHT){
                    return true;
                }
            }
            // Check for white pawns
            if (this.board[square+9]==BoardConstants.WHITE_PAWN){
                // If there is a pawn down-left
                return true;
            } else if (this.board[square+11]==BoardConstants.WHITE_PAWN){
                return true;
            }
            // Check for white king
            for (int direction : BoardConstants.KINGMOVES){
                pieceAtSquare = this.board[square+direction];
                if (pieceAtSquare==BoardConstants.WHITE_KING){
                    return true;
                }
            }
            return false;
        } else {
            // Check if black is attacking square

            // Check if black queen or bishop is on the diagonals
            for (int direction : BoardConstants.BISHOPMOVES){
                newSquare = square + direction;
                while (true){
                    pieceAtSquare = this.board[newSquare];
                    if (pieceAtSquare==BoardConstants.EMPTY){
                        newSquare = newSquare + direction;
                    } else if (pieceAtSquare==BoardConstants.BLACK_BISHOP || pieceAtSquare==BoardConstants.BLACK_QUEEN){
                        return true;
                    } else {
                        // At this stage either we have reached the edge of the board, or there is a piece there,
                        // but it's not a black bishop or queen so no attack from that diagonal.
                        break;
                    }
                }
            }
            // Check if black queen or rook on rank or file
            for (int direction : BoardConstants.ROOKMOVES){
                newSquare = square + direction;
                while (true){
                    pieceAtSquare = this.board[newSquare];
                    if (pieceAtSquare==BoardConstants.EMPTY){
                        newSquare = newSquare + direction;
                    } else if (pieceAtSquare==BoardConstants.BLACK_ROOK || pieceAtSquare==BoardConstants.BLACK_QUEEN){
                        return true;
                    } else {
                        break;
                    }
                }
            }
            // Check for black knight
            for (int direction : BoardConstants.KNIGHTMOVES){
                pieceAtSquare = this.board[square+direction];
                if (pieceAtSquare==BoardConstants.BLACK_KNIGHT){
                    return true;
                }
            }
            // Check for black pawns
            if (this.board[square-9]==BoardConstants.BLACK_PAWN){
                // If there is a pawn down-left
                return true;
            } else if (this.board[square-11]==BoardConstants.BLACK_PAWN){
                return true;
            }
            // Check for black king
            for (int direction : BoardConstants.KINGMOVES){
                pieceAtSquare = this.board[square+direction];
                if (pieceAtSquare==BoardConstants.BLACK_KING){
                    return true;
                }
            }
            return false;
        }
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
            if (this.board[i] != BoardConstants.OUTSIDE_BOARD){
                System.out.print(this.board[i]);
                if (i%10 == 8){
                    System.out.print('\n');
                }
            }
        }
        /*
        LinkedList_<Move> moves = this.generateMoves();
        System.out.println("Possible moves in this position:");
        for (Move m : moves){
            m.display();
        }

         */
    }
}
