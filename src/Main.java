import Controller.Hand;

public class Main {
    public static void main(String[] args) {
        Hand hand = new Hand();
        if (args[0].equalsIgnoreCase("b")){
            hand.makeCPUMove();
        }
        hand.updateGUIBoard();
    }
}
