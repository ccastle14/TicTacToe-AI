import java.util.*;

/**
 * Each node represents a game state,
 * stores data such as the board.
 */

class Node
{
    private String[][] state;
    private boolean xTurn;
    private int score;
    private ArrayList<Node> children = new ArrayList<Node>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();
    private int maxIndex;
    
    Node()
    {
        xTurn = true;
        state = new String[3][3];
        score = -1;
    }
    
    Node(String[][] gameState)
    {
        xTurn = true;
        state = gameState;
        score = -1;
    }
    
    void play(int row, int col, String play)
    {
        state[row][col] = play;
        xTurn = !xTurn;
    }
    
    String[][] getState()
    {
        return state;
    }
    
    boolean isXsTurn()
    {
        return xTurn;
    }
    
    void setTurn(boolean t)
    {
        xTurn = t;
    }
    
    void setScore(int num)
    {
        score = num;
    }
    
    void addNode(Node ni)
    {
        children.add(ni);
    }
    
    ArrayList<Node> getSuccessors()
    {
        return children;
    }

    private int thisScore()
    {
        return score;
    }

    /**
     * Returns the score assigned to a particular node.
     * Each node's score will either be the minimum or
     * the maximum of its children's scores, depending
     * on whether it's X's or O's turn.
     *
     * The goal is to minimize its opponent's score
     * while maximimizing its own score, and each
     * layer of nodes in the tree represents
     * either X's or O's turn.
     *
     * @param n
     * @return
     */
    int getScore(Node n)
    {
        xTurn = !xTurn;
        if(n.thisScore() != -1)
        {
            xTurn = !xTurn;
            return n.thisScore();
        }
        else
        {
            ArrayList<Integer> tempScores = new ArrayList<Integer>();
            for(int i = 0; i < n.getSuccessors().size(); i++)
            {
                tempScores.add(getScore(n.getSuccessors().get(i)));
            }
            if(!xTurn)
            {
                int min = tempScores.get(0);
                for(int i = 0; i < tempScores.size(); i++)
                {
                    if(tempScores.get(i) < min)
                        min = tempScores.get(i);
                }
                xTurn = !xTurn;
                return min;
            }
            else
            {
                int max = tempScores.get(0);
                for(int i = 0; i < tempScores.size(); i++)
                {
                    if(tempScores.get(i) > max)
                        max = tempScores.get(i);
                }
                xTurn = !xTurn;
                return max;
            }
        }
    }

    /**
     * Returns the board-state containing the best play
     *
     * @param childs
     * @return
     */
    String[][] minMax(ArrayList<Node> childs)
    {
        for(int i = 0; i < childs.size(); i++)
        {
            scores.add(getScore(childs.get(i)));
        }
        int max = scores.get(0);
        for(int i = 0; i < scores.size(); i++)
        {
            if(scores.get(i) > max)
            {
                max = scores.get(i);
                maxIndex = i;
            }
        }
        return childs.get(maxIndex).getState();
    }
}