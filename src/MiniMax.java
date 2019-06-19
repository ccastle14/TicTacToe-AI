/**
 * Builds tree of board-state nodes
 */


class MiniMax
{
    /**
     * Recursively computes every possible series of plays
     * and assigns a score to each state based on whether
     * it results in the computer winning, the player winning,
     * or a draw.
     *
     * @param currentNode
     */
    void main(Node currentNode)
    {
        if(BoardCheck.hasSomeoneWon(currentNode.getState()))
        {
            if(currentNode.isXsTurn())
            {
                currentNode.setScore(-10);
            }
            else
            {
                currentNode.setScore(10);
            }
        }
        else if(BoardCheck.isBoardFull(currentNode.getState()))
        {
            currentNode.setScore(0);
        }
        else
        {
            for(int i = 0; i < 3; i++) //these loops cycle through all spaces
            {
                for(int j = 0; j < 3; j++)
                {
                    if(!BoardCheck.isCellClaimed(currentNode.getState(),i,j))
                    {
                        String[][] arr = new String[3][3];
                        arr = makeCopy(currentNode.getState());
                        Node nn = new Node(arr);
                        nn.setTurn(currentNode.isXsTurn());
                        if(nn.isXsTurn())
                            nn.play(i,j,"X");
                        else
                            nn.play(i,j,"O");
                        currentNode.addNode(nn);
                        main(nn);
                    }
                }
            }
        }
    }

    //makes a copy of a state to allow creation of child nodes without altering original
    private String[][] makeCopy(String[][] state)
    {
        String[][] out = new String[3][3];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                out[i][j] = state[i][j];
            }
        }
        return out;
    }
}