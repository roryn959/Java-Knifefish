package View;

import Controller.Hand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessGUI {
    ArrayList<JButton> boardButtons;
    ArrayList<JPanel> buttonPanels;
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
        // QS
        JButton queensideCastleButton = new JButton("Long Castle");
        queensideCastleButton.setFont(new Font("Arial", Font.PLAIN, 30));
        queensideCastleButton.addActionListener(e -> {
            this.notifyHandOfCastle(0);
        });
        castleButtonsPanel.add(queensideCastleButton);
        // UNDO
        JButton undoButton = new JButton("Undo Last Move");
        undoButton.setFont(new Font("Arial", Font.PLAIN, 30));
        undoButton.addActionListener(e -> {
            this.notifyHandOfUndo();
        });
        castleButtonsPanel.add(undoButton);
        // KS
        JButton kingsideCastleButton = new JButton("Short Castle");
        kingsideCastleButton.setFont(new Font("Arial", Font.PLAIN, 30));
        kingsideCastleButton.addActionListener(e -> {
            this.notifyHandOfCastle(1);
        });
        castleButtonsPanel.add(kingsideCastleButton);

        // Create board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        this.boardButtons = new ArrayList<>();
        this.buttonPanels = new ArrayList<>();
        for (int i=0; i<120; i++){
            if (i<21 || i>98 || i%10==0 || i%10==9){
                this.boardButtons.add(null);
                this.buttonPanels.add(null);
            } else {
                JPanel buttonPanel = new JPanel();
                JButton button = new JButton();

                // Add clicking function
                int finalI = i;
                button.addActionListener(e -> {
                    this.boardButtonClicked(finalI);
                });

                // Format button style
                button.setFont(new Font("Arial", Font.PLAIN, 50));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);

                buttonPanel.add(button);
                boardPanel.add(buttonPanel);
                this.boardButtons.add(button);
                this.buttonPanels.add(buttonPanel);
            }
        }
        frame.getContentPane().add(castleButtonsPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        this.colourBoardDefault();
    }

    private void colourBoardDefault(){
        for (int i=0; i<120; i++){
            JPanel panel = this.buttonPanels.get(i);
            if (panel!=null) {
                if ((i / 10 + i % 10) % 2 != 0) {
                    panel.setBackground(new Color(255, 239, 204));
                } else {
                    panel.setBackground(new Color(179, 124, 87));
                }
            }
        }
    }

    private void boardButtonClicked(int square){
        if (fromSquare==null){
            fromSquare = square;
            this.highlightSquare(square);
        } else {
            toSquare = square;
            this.colourBoardDefault();
            this.notifyHandOfMove();
            this.fromSquare = null;
            this.toSquare = null;
        }
    }

    private void notifyHandOfMove(){
        this.hand.submitMove(this.fromSquare, this.toSquare);
    }

    private void notifyHandOfCastle(int side){
        this.hand.submitCastle(side);
    }

    private void notifyHandOfUndo(){
        this.hand.undoMove();
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

    public void highlightSquare(int i){
        this.buttonPanels.get(i).setBackground(Color.ORANGE);
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
            return " ";
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