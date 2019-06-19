/**
 * Can perform various checks on a board state,
 * including determining which row/column/diagonal
 * has achieved a victory.
 */

class BoardCheck
{
    static boolean isCellClaimed(String[][] board, int r, int c)
    {
        if(board[r][c] != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    static boolean hasDiagonalsWon(String[][] board)
    {
        boolean out = false;
        if(board[0][0] != null && board[1][1] != null && board[2][2] != null)
        {
            if((board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X"))
            ||(board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")))
            {
                out = true;
                NewTicTacToeGame.topLeftBottomRightDiag = true;
            }
        }
        if(board[2][0] != null && board[1][1] != null && board[0][2] != null)
        {
            if((board[2][0].equals("X") && board[1][1].equals("X") && board[0][2].equals("X"))
            ||(board[2][0].equals("O") && board[1][1].equals("O") && board[0][2].equals("O")))
            {    
                out = true;
                NewTicTacToeGame.bottomLeftTopRightDiag = true;
            }
        }
        return out;
    }
    
    static boolean hasRowsWon(String[][] board)
    {
        boolean out = false;
        if(board[0][0] != null && board[0][1] != null && board[0][2] != null)
        {
            if((board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X"))
            ||(board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")))
                out = true;
        }
        if(board[1][0] != null && board[1][1] != null && board[1][2] != null)
        {
            if((board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X"))
            ||(board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")))
            {   
                out = true;
                NewTicTacToeGame.rowWin = 1;
            }
        }
        if(board[2][0] != null && board[2][1] != null && board[2][2] != null)
        {
            if((board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X"))
            ||(board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")))
            {
                out = true;
                NewTicTacToeGame.rowWin = 2;
            }
        }
        return out;
    }
    
    static boolean hasColsWon(String[][] board)
    {
        boolean out = false;
        if(board[0][0] != null && board[1][0] != null && board[2][0] != null)
        {
            if((board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X"))
            ||(board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")))
                out = true;
        }
        if(board[0][1] != null && board[1][1] != null && board[2][1] != null)
        {
            if((board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X"))
            ||(board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")))
            {
                out = true;
                NewTicTacToeGame.colWin = 1;
            }
        }
        if(board[0][2] != null && board[1][2] != null && board[2][2] != null)
        {
            if((board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X"))
            ||(board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")))
            {
                out = true;
                NewTicTacToeGame.colWin = 2;
            }
        }
        return out;
    }

    static boolean hasSomeoneWon(String[][] board){
        if(hasDiagonalsWon(board) || hasRowsWon(board) || hasColsWon(board))
            return true;
        else return false;
    }

    static boolean isGameOver(String[][] board)
    {
        if(hasDiagonalsWon(board) || hasRowsWon(board) || hasColsWon(board) || isBoardFull(board))
            return true;
        else return false;
    }
    
    static boolean isBoardFull(String[][] board)
    {
        boolean full = true;
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                if(board[i][j] == null)
                {
                    full = false;
                }
            }
        }
        return full;
    }
}