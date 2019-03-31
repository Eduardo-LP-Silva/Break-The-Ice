package game;

public class GameBoard
{
    private char[][] board;
    private int maxMoves;
    private int optimalMoves;
    private static boolean showMove = false;
    
    public GameBoard(char[][] board, int maxMoves, int optimalMoves)
    {
        this.board = board;
        this.maxMoves = maxMoves;
        this.optimalMoves = optimalMoves;
    }

    public GameBoard movePieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);
        char[][] testBoard = null;

        if (!validateMoveRight(pieceCoords)) 
        {
            System.out.println("Can't move right: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        } 
        else 
        {
            int[] newPieceCoords = pieceCoords.clone();

            newPieceCoords[1] += 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; // Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; // Switch place

            if(showMove)
                printBoard(newBoard);

            // Make piece in question fall if that's the case
            if (newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                
                if(showMove)
                    printBoard(newBoard);
            }
                
            // Make the other pieces above the original one fall if they exist
            if (pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                newBoard = dropColumn(newBoard, pieceCoords[1]); 

                if(showMove)
                    printBoard(newBoard);
            } 
              
            testBoard = explodeAll(newBoard);

            if(testBoard != null)
            {
                newBoard = testBoard;

                if(showMove)
                    printBoard(newBoard);
            }                
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
    }

    public boolean validateMoveRight(int[] pieceCoords)
    {
        return pieceCoords[1] != board[0].length - 1 && this.board[pieceCoords[0]][pieceCoords[1] + 1] == '_';
    }

    public GameBoard movePieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard = null;

        if(!validateMoveLeft(pieceCoords))
        {
            System.out.println("Can't move left: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            int[] newPieceCoords = pieceCoords.clone();
            newPieceCoords[1] -= 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; //Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; //Switch place

            if(showMove)
                printBoard(newBoard);

            //Make piece in question fall if that's the case
            if(newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                
                if(showMove)
                    printBoard(newBoard);
            }
        
            //Make the other pieces above the original one fall if they exist
            if(pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                newBoard = dropColumn(newBoard, pieceCoords[1]);

                if(showMove)
                    printBoard(newBoard);
            }    

            testBoard = explodeAll(newBoard);

            if(testBoard != null)
            {
                newBoard = testBoard;

                if(showMove)
                    printBoard(newBoard);
            } 
               
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
    }

    public boolean validateMoveLeft(int[] pieceCoords)
    {
        return pieceCoords[1] != 0 && this.board[pieceCoords[0]][pieceCoords[1] - 1] == '_';
    }

    public GameBoard switchPieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard = null;

        if(!validateSwitchLeft(pieceCoords))
        {
            System.out.println("Can't switch left: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] - 1])
            {
                if(showMove)
                    printBoard(newBoard);

                return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
            }
                

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] - 1]; 
            newBoard[pieceCoords[0]][pieceCoords[1] - 1] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoard(newBoard);
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

            if (showMove)
                printBoard(newBoard);
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves); 
    }

    public boolean validateSwitchLeft(int[] pieceCoords)
    {
        return pieceCoords[1] != 0 && this.board[pieceCoords[0]][pieceCoords[1] - 1] != '_';
    }

    public GameBoard switchPieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard = null;

        if(!validateSwitchRight(pieceCoords))
        {
            System.out.println("Can't switch right: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] + 1])
            {
                if(showMove)
                    printBoard(newBoard);

                return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1];
            newBoard[pieceCoords[0]][pieceCoords[1] + 1] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoard(newBoard);
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

            if (showMove)
                printBoard(newBoard);
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
    }

    public boolean validateSwitchRight(int[] pieceCoords)
    {
        return pieceCoords[1] != board[0].length - 1 && this.board[pieceCoords[0]][pieceCoords[1] + 1] != '_';
    }

    public GameBoard switchPieceUp(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard = null;

        if(!validateSwitchUp(pieceCoords))
        {
            System.out.println("Can't switch up: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] - 1][pieceCoords[1]])
            {
                if(showMove)
                    printBoard(newBoard);

                return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] - 1][pieceCoords[1]];
            newBoard[pieceCoords[0] - 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]];
            
            if(showMove)
                printBoard(newBoard);
        }

        testBoard = explodeAll(newBoard);
        
        if (testBoard != null) 
        {
            newBoard = testBoard;

            if (showMove)
                printBoard(newBoard);
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
    }

    public boolean validateSwitchUp(int[] pieceCoords)
    {
        return pieceCoords[0] != 0 && this.board[pieceCoords[0] - 1][pieceCoords[1]] != '_';
    }

    public GameBoard switchPieceDown(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board), testBoard = null;

        if(!validateSwitchDown(pieceCoords))
        {
            System.out.println("Can't switch down: (" + pieceCoords[0] + "," + pieceCoords[1] + ")");
            return null;
        }
        else
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] + 1][pieceCoords[1]])
            {
                if(showMove)
                    printBoard(newBoard);

                return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
            }

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] + 1][pieceCoords[1]];
            newBoard[pieceCoords[0] + 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]];

            if(showMove)
                printBoard(newBoard); 
        }

        testBoard = explodeAll(newBoard);

        if(testBoard != null) 
        {
            newBoard = testBoard;

            if (showMove)
                printBoard(newBoard);
        }

        return new GameBoard(newBoard, this.maxMoves, this.optimalMoves);
    }

    public boolean validateSwitchDown(int[] pieceCoords)
    {
        return pieceCoords[0] != board.length - 1 && this.board[pieceCoords[0] + 1][pieceCoords[1]] != '_';
    }

    public char[][] dropPiece(char[][] board, int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(board);
        int[] coords = pieceCoords.clone();
        char piece = newBoard[coords[0]][coords[1]];
        
        newBoard[coords[0]][coords[1]] = '_';

        do
        {
            coords[0] += 1;
        }
        while(coords[0] < board.length - 1 && newBoard[coords[0] + 1][coords[1]] == '_');

        newBoard[coords[0]][coords[1]] = piece;

        return newBoard;
    }

    public int[] getDroppedPieceCoords(int column, char[][] board)
    {
        int[] droppedPieceCoords = new int[] {board.length - 1, column};

        for(int i = 0; i < board.length; i++)
            if(board[i][column] != '_')
            {
                droppedPieceCoords[0] = i;
                droppedPieceCoords[1] = column;

                return droppedPieceCoords;
            }

        //System.out.println("Couldn't find dropped piece at column " + column);

        return droppedPieceCoords;
    }

    public char[][] dropColumn(char[][] board, int column)
    {
        int i;
        char[][] newBoard = copyBoard(board);
        int[] pieceCoords = {0, column};

        for(i = 0; i < board.length; i++)
            if(board[i][column] != '_')
                break;

        for(; i < board.length - 1; i++)
            if(board[i + 1][column] == '_')
            {
                pieceCoords[0] = i;

                while(newBoard[pieceCoords[0]][pieceCoords[1]] != '_')
                {
                    newBoard = dropPiece(newBoard, pieceCoords);
                    pieceCoords[0]--;
                }

                break;
            }
                
        return newBoard;
    }

    public char[][] explodeAll(char[][] board)
    {
        char[][] newBoard = copyBoard(board), testBoard;
        boolean recalculate = false;
        boolean differentBoard = false;
        
        for(int i = 0; i < board.length; i++)
        {
            testBoard = explodeLine(i, newBoard);

            if(testBoard != null)
            {
                newBoard = copyBoard(testBoard);
                recalculate = true;
                differentBoard = true;

                if(showMove)
                    printBoard(newBoard);
            }

            if(i < newBoard[i].length)
            {
                testBoard = explodeColumn(i, newBoard);

                if(testBoard != null)
                {
                    newBoard = copyBoard(testBoard);
                    recalculate = true;

                    if(showMove)
                        printBoard(newBoard);
                }
            }
                
            if(i == board.length - 1 && recalculate)
            {
                recalculate = false;
                i = 0;
                continue;
            }  
        }

        if(differentBoard)
            return newBoard;
        else
            return null;
    }

    public char[][] explodeAround(int line, int column, char[][] board)
    {
        char[][] newBoard = copyBoard(board), testBoard;

        testBoard = explodeLine(line, newBoard);
        
        if(testBoard != null)
            return explodeAll(testBoard);
        else
        {
            testBoard = explodeColumn(column, newBoard);

            if(testBoard != null)
                return explodeAll(testBoard);
            else
                return board;
        }
        
    }

    public char[][] explodeLine(int line, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = 0; i < newBoard[line].length; i++)
        {
            if(newBoard[line][i] != '_')
            {
                if(newBoard[line][i] == currentColor)
                {
                    counter++;

                    if(counter == 3)
                    {
                        foundPattern = true;

                        //Destroy previous blocks
                        if(showMove)
                        {
                            for(int j = 0; j < 3; j++)
                                newBoard[line][i - 2 + j] = '*';
                        }
                        else
                        {
                            for(int j = 0; j < 3; j++)
                            {
                                newBoard[line][i - 2 + j] = '_';
    
                                if(line > 0 && newBoard[line - 1][i - 2 + j] != '_')
                                    newBoard = dropColumn(newBoard, i - 2 + j);
                            }
                        }
                        
                    }
                    else
                        if(counter > 3)
                        {
                            if(showMove)
                            {
                                newBoard[line][i] = '*';
                            }
                            else
                            {
                                newBoard[line][i] = '_';

                                if(line > 0 && newBoard[line - 1][i] != '_')
                                    newBoard = dropColumn(newBoard, i);
                            }
                            
                        } 
                }
                else
                {
                    currentColor = newBoard[line][i];
                    counter = 1;
                }
            }
            else
            {
                currentColor = 'X';
            }
        }

        if(foundPattern)
        {
            if(showMove)
            {
                printBoard(newBoard);

                for(int i = 0; i < newBoard[line].length; i++)
                    if(newBoard[line][i] == '*')
                    {
                        newBoard[line][i] = '_';

                        if(line > 0 && newBoard[line - 1][i] != '_')
                            newBoard = dropColumn(newBoard, i);
                    }
            }

            return newBoard;
        }
        else
            return null;
    }

    public char[][] explodeColumn(int column, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = newBoard.length - 1; i >= 0; i--)
            if(newBoard[i][column] == currentColor && newBoard[i][column] != '_')
            {
                counter++;

                if(counter == 3)
                {
                    foundPattern = true;

                    if(showMove)
                    {
                        newBoard[i + 2][column] = '*';
                        newBoard[i + 1][column] = '*';
                        newBoard[i][column] = '*';
                    }
                    else
                    {
                        newBoard[i + 2][column] = '_';
                        newBoard[i + 1][column] = '_';
                        newBoard[i][column] = '_';
                    }
                        
                }
                else
                    if(counter > 3)
                        if(showMove)
                            newBoard[i][column] = '*';
                        else
                            newBoard[i][column] = '_';
            }
            else 
            {
                if(counter >= 3)
                {
                    if(showMove)
                    {
                        printBoard(newBoard);

                        for(int j = 0; j < newBoard.length; j++)
                            if(newBoard[j][column] == '*')
                                newBoard[j][column] = '_';

                        
                    }

                    newBoard = dropColumn(newBoard, column);
                    i += counter;
                }

                counter = 1;
                currentColor =  newBoard[i][column];
            }

        if(foundPattern)
            return newBoard;
        else
            return null;

    }

    public char[][] copyBoard(char[][] board)
    {
        char newBoard[][] = new char[board.length][board[0].length];

        for(int i = 0; i < board.length; i++)
            newBoard[i] = board[i].clone();

        return newBoard;
    }

    public char[][] getBoard()
    {
        return copyBoard(board);
    }

    public void setBoard(char[][] board)
    {
        this.board = board;
    }

    public int getMaxMoves()
    {
        return maxMoves;
    }

    public static void setShowMove(boolean showMove)
    {
        GameBoard.showMove = showMove;
    }

    public void printBoard()
    {
        printBoard(board);
    }

    public void printBoard(char[][] board)
    {
        for(int i = 0; i < board.length; i++)
        {
            System.out.print("|");

            for(int j = 0; j < board[i].length; j++)
            {
                System.out.print(board[i][j]);
                System.out.print('|');
            }
                

            System.out.print("\n");
        }

        System.out.println("---------------");
    }

    public boolean testPieceCoords(int[] pieceCoords)
    {
        return !(pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length 
        || pieceCoords[0] < 0 || pieceCoords[1] < 0 || this.board[pieceCoords[0]][pieceCoords[1]] == '_');
    }

    public int getOptimalMoves()
    {
        return optimalMoves;
    }

    public int getBlocksLeft()
    {
        return getBlocksLeft(board);
    }
    
    public static int getBlocksLeft(char[][] board)
    {
        int count = 0;

        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                if(board[i][j] != '_') 
                    count++;
            } 
        }

        return count;
    }
}