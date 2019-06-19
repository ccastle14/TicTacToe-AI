/**
 * A game of TicTacToe to be played between a human
 * and the unbeatable computer using the minimax
 * algorithm, or between two human players.
 *
 * @author Colin Cassell
 */

public class Runner {
    public static void main(String[] args) throws Exception{
        NewTicTacToeGame n = new NewTicTacToeGame();
        n.playAI();
        //n.playTwoPlayer();
    }
}
