package View;

import Controller.Hand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessGUI {
    ArrayList<JButton> boardButtons;
    Integer fromSquare, toSquare;
    Hand hand;

    public ChessGUI(Hand hand){
        this.fromSquare = null;
        this.toSquare = null;
        this.hand = hand;

        // Create main frame
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 700);

        // Create top castle buttons panel
        JPanel castleButtonsPanel = new JPanel();
        JButton queensideCastleButton = new JButton("Long Castle");
        queensideCastleButton.setFont(new Font("Arial", Font.PLAIN, 30));
        JButton kingsideCastleButton = new JButton("Short Castle");
        kingsideCastleButton.setFont(new Font("Arial", Font.PLAIN, 30));
        castleButtonsPanel.add(queensideCastleButton);
        castleButtonsPanel.add(kingsideCastleButton);

        // Create board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        this.boardButtons = new ArrayList<>();
        for (int i=0; i<120; i++){
            if (i<21 || i>98 || i%10==0 || i%10==9){
                this.boardButtons.add(null);
            } else {
                JButton button = new JButton();
                int finalI = i;
                button.addActionListener(e -> {
                    this.boardButtonClicked(finalI);
                });
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                boardPanel.add(button);
                this.boardButtons.add(button);
            }
        }

        frame.getContentPane().add(castleButtonsPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void boardButtonClicked(int square){
        if (fromSquare==null){
            fromSquare = square;
        } else {
            toSquare = square;
            this.notifyHandOfMove();
            this.fromSquare = null;
            this.toSquare = null;
        }
    }

    private void notifyHandOfMove(){
        this.hand.submitMove(this.fromSquare, this.toSquare);
    }

    public void updateBoard(int[] board){
        int pieceAtSquare;
        String pieceSymbol;
        for (int i=0; i<120; i++){
            pieceAtSquare = board[i];
            if (pieceAtSquare!=69){
                pieceSymbol = this.getSymbolOfPiece(pieceAtSquare);
                this.boardButtons.get(i).setText(pieceSymbol);
            }
        }
    }

    private String getSymbolOfPiece(int piece){
        if (piece==-6){
            return "♚";
        } else if (piece==-5){
            return "♛";
        } else if (piece==-4){
            return "♜";
        } else if (piece==-3){
            return "♝";
        } else if (piece==-2){
            return "♞";
        } else if (piece==-1){
            return "♟";
        } else if (piece==0){
            return "";
        } else if (piece==1){
            return "♙";
        } else if (piece==2){
            return "♘";
        } else if (piece==3){
            return "♗";
        } else if (piece==4){
            return "♖";
        } else if (piece==5){
            return "♕";
        } else if (piece==6){
            return "♔";
        } else {
            return null;
        }
    }
}