import java.awt.Color;
import java.util.Scanner;


/**
 * Creates board and runs game in two-player or AI mode,
 * then handles the end of the game.
 */


class NewTicTacToeGame
{

    private int WINDOW_SIZE;
    private int LINE_BUFFER;
    private int TILE_SIZE;

    private boolean isXsTurn;
    private static String[][] board;
    private Color blue = new Color(0,0,255, 50);
    private Color red = new Color(255,0,0,50);

    static boolean topLeftBottomRightDiag;
    static boolean bottomLeftTopRightDiag;
    static int rowWin;
    static int colWin;

    private boolean firstTime;

    private Scanner sc;

    private Node currentNode;




    NewTicTacToeGame()
    {
        WINDOW_SIZE = 600;
        LINE_BUFFER = 25;
        TILE_SIZE = WINDOW_SIZE / 3;
        isXsTurn = true;
        sc = new Scanner(System.in);
    }


    void playAI() throws Exception
    {
        firstTime = true;
        currentNode = new Node();
        
        drawBoard();
        while (!BoardCheck.isGameOver(board)) {
            if (!isXsTurn) {
                while (true) {
                    if (mouseClicked()) {
                        double x = StdDraw.mouseX();
                        double y = StdDraw.mouseY();
                        int[] coords = determineSquare(x, y);
                        if (!BoardCheck.isCellClaimed(board, coords[0], coords[1])) {
                            drawShape("O", coords[0], coords[1]);
                            currentNode.play(coords[0], coords[1], "O");
                            Thread.sleep(500);
                            break;
                        }
                    }
                }
            } else
                executeAI();
            isXsTurn = !isXsTurn;
        }
        gameOverHandler(false);
    }


    void playTwoPlayer() throws Exception
    {
        drawBoard();
        while (!BoardCheck.isGameOver(board))
        {
            if (mouseClicked())
            {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                int[] coords = determineSquare(x, y);
                if (!BoardCheck.isCellClaimed(board, coords[0], coords[1]))
                {
                    if(isXsTurn)
                        drawShape("X", coords[0], coords[1]);
                    else
                        drawShape("O", coords[0], coords[1]);
                    isXsTurn = !isXsTurn;
                }
            }
        }
        gameOverHandler(true);
    }

    /**
     * Handles the visuals when the game ends,
     * and asks whether the player wants to play
     * again or switch game modes.
     *
     * @param isTwoPlayer
     * @throws Exception
     */
    private void gameOverHandler(boolean isTwoPlayer) throws Exception{
        sc = new Scanner(System.in);
        topLeftBottomRightDiag = false;
        rowWin = 0;
        colWin = 0;
        if(BoardCheck.hasSomeoneWon(board))
        {
            if(!isXsTurn)
            {
                StdDraw.setPenColor(blue);
                StdDraw.filledSquare(0,0,1000);
                StdDraw.show();
            }
            else
            {
                StdDraw.setPenColor(red);
                StdDraw.filledSquare(0,0,1000);
                StdDraw.show();
            }
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(.1);
            if(BoardCheck.hasDiagonalsWon(board))
            {
                if(topLeftBottomRightDiag && bottomLeftTopRightDiag)
                {
                    StdDraw.line(600,0,0,600);
                    StdDraw.line(0,0,600,600);
                }
                else if(topLeftBottomRightDiag)
                {
                    StdDraw.line(600,0,0,600);
                }
                else
                {
                    StdDraw.line(0,0,600,600);
                }
            }
            if(BoardCheck.hasRowsWon(board))
            {
                if(rowWin == 0)
                    StdDraw.line(0,500,600,500);
                else if(rowWin == 1)
                    StdDraw.line(0,300,600,300);
                else if(rowWin == 2)
                    StdDraw.line(0,100,600,100);
            }
            if(BoardCheck.hasColsWon(board))
            {
                if(colWin == 0)
                    StdDraw.line(100,0,100,600);
                else if(colWin == 1)
                    StdDraw.line(300,0,300,600);
                else if(colWin == 2)
                    StdDraw.line(500,0,500,600);
            }
            StdDraw.show();
            Thread.sleep(1000);
        }
        else
        {
            Thread.sleep(1000);
            System.out.println("It's a draw!");
        }

        if(isTwoPlayer) {
            System.out.println("Play again? Type 'Yes' or 'AI' for AI");
            String answer = sc.nextLine();

            if(answer.equalsIgnoreCase("Yes"))
            {
                isXsTurn = true;
                playTwoPlayer();
            }
            else if(answer.equalsIgnoreCase("AI"))
            {
                isXsTurn = true;
                playAI();
            }
        }
        else{
            System.out.println("Play again? Type 'Yes' or 'Two' for two player");
            String answer = sc.nextLine();

            if(answer.equalsIgnoreCase("Yes"))
            {
                isXsTurn = true;
                playAI();
            }
            else if(answer.equalsIgnoreCase("Two"))
            {
                isXsTurn = true;
                playTwoPlayer();
            }
        }

    }

    private void drawBoard()
    {
        StdDraw.setCanvasSize(WINDOW_SIZE, WINDOW_SIZE);
        StdDraw.setScale(0, WINDOW_SIZE);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(WINDOW_SIZE / 3, LINE_BUFFER, WINDOW_SIZE / 3, WINDOW_SIZE - LINE_BUFFER);
        StdDraw.line(2*WINDOW_SIZE / 3, LINE_BUFFER, 2*WINDOW_SIZE / 3, WINDOW_SIZE - LINE_BUFFER);     
        StdDraw.line(LINE_BUFFER, WINDOW_SIZE / 3, WINDOW_SIZE - LINE_BUFFER, WINDOW_SIZE / 3);
        StdDraw.line(LINE_BUFFER, 2*WINDOW_SIZE / 3, WINDOW_SIZE - LINE_BUFFER, 2*WINDOW_SIZE / 3);
        StdDraw.show();
        board = new String[3][3];
    }

    private int[] determineSquare(double x, double y)
    {
        int[] coords = new int[2];
        if (y > 2*WINDOW_SIZE / 3)
            coords[0] = 0;
        else if (y > WINDOW_SIZE / 3)
            coords[0] = 1;
        else
            coords[0] = 2;
        if (x < WINDOW_SIZE / 3)
            coords[1] = 0;
        else if (x < 2*WINDOW_SIZE / 3)
            coords[1] = 1;
        else
            coords[1] = 2;
        return coords;
    }

    private void drawShape(String letter, int row, int col)
    {
        double x, y;
        if (col == 0)
            x = WINDOW_SIZE / 2 - TILE_SIZE;
        else if (col == 1)
            x = WINDOW_SIZE / 2;
        else
            x = WINDOW_SIZE / 2 + TILE_SIZE;
        if (row == 0)
            y = WINDOW_SIZE - TILE_SIZE / 2;
        else if (row == 1)
            y = WINDOW_SIZE / 2;
        else
            y = TILE_SIZE / 2;

        if (letter.equalsIgnoreCase("X"))
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x - TILE_SIZE / 2 + LINE_BUFFER, y - TILE_SIZE / 2 + LINE_BUFFER, x + TILE_SIZE / 2 - LINE_BUFFER, y + TILE_SIZE / 2 - LINE_BUFFER);
            StdDraw.line(x - TILE_SIZE / 2 + LINE_BUFFER, y + TILE_SIZE / 2 - LINE_BUFFER, x + TILE_SIZE / 2 - LINE_BUFFER, y - TILE_SIZE / 2 + LINE_BUFFER);
            board[row][col] = "X";
        }
        else
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(x, y, TILE_SIZE / 2 - LINE_BUFFER);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(x, y, TILE_SIZE / 2 - 1.2*LINE_BUFFER);
            board[row][col] = "O";
        }
        StdDraw.show();
    }


    private boolean mouseClicked()
    {
        if (StdDraw.mousePressed())
        {
            while (StdDraw.mousePressed())
            {}
            return true;
        }
        return false;
    }

    /**
     * Plays randomly for the first move, otherwise
     * uses the minimax algorithm to determine the best
     * possible play.
     */
    private void executeAI()
    {
        if(firstTime)
        {
            int playRow = ((int)(Math.random() * 3));
            int playCol = ((int)(Math.random() * 3));
            drawShape("X", playRow, playCol);
            currentNode.play(playRow, playCol, "X");
            firstTime = false;
        }
        else
        {
            MiniMax m = new MiniMax();

            m.main(currentNode); //adds all children, sets scores of leaves

            String[][] arry;
            arry = currentNode.minMax(currentNode.getSuccessors()); //state containing best play

            int[] coords = findPlay(arry);
            drawShape("X", coords[0], coords[1]); //draws play on the board

            currentNode.play(coords[0], coords[1], "X"); //makes the play

            
        }
        String[][] newState = currentNode.getState(); //makes new node to focus on
        currentNode = new Node(newState);
        currentNode.setTurn(false);
    }

    /**
     * Given the board that minimax played on,
     * identifies the space of that new move.
     *
     * @param newState board state containing the AI's newest play
     * @return
     */
    private int[] findPlay(String[][] newState)
    {
        int[] out = new int[2];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(newState[i][j] != null)
                {
                    if(currentNode.getState()[i][j] == null && newState[i][j].equals("X")) {
                        out[0] = i;
                        out[1] = j;
                    }
                }
            }
        }
        return out;
    }
}