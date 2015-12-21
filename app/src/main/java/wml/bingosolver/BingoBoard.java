package wml.bingosolver;

import java.util.Set;
import java.util.TreeSet;

public class BingoBoard
{
    public BingoPiece[][] pieces = new BingoPiece[5][5];

    public TreeSet<Integer> allNumbers = new TreeSet<Integer>();

    public BingoBoard(int [][] board_values)
    {
        enterValues(board_values);
    }


    public void enterValues(int[][] pictureValues)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if((i == 2 && j == 2) )
                {
                    allNumbers.add(0);
                    pieces[i][j] = new BingoPiece(0,
                            true);
                } else
                {
                    allNumbers.add(pictureValues[i][j]);
                    pieces[i][j] = new BingoPiece(pictureValues[i][j],
                         false);
                }
                // initialize
                // piece
                // with
                // value
                // and
                // false
                // called
            }

        }
    }

    public boolean call(int val)
    {
        if (contains(val))
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    if (pieces[i][j].value == val)
                    {
                        pieces[i][j].called = true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @param val
     *            - whatever is entered/called
     * @return if board pieces contain specific value
     */
    public boolean contains(int val)
    {
        return allNumbers.contains(val);
    }

    /**
     * Gets booleans in a horizontal row
     * 
     * @param x
     *            Xth row
     * @return array of 5 booleans in that row
     */
    public boolean[] horizontalHelper(int x)
    {
        boolean[] xCalled = new boolean[5];
        for (int c = 0; c < 5; c++)
        {
            xCalled[c] = pieces[x][c].called;
        }
        return xCalled;
    }

    /**
     * Gets booleans in a vertical column
     * 
     * @param y
     *            Yth column
     * @return arrayOf 5 booleans in that column
     */
    public boolean[] verticalHelper(int y)
    {
        boolean[] yCalled = new boolean[5];
        for (int c = 0; c < 5; c++)
        {
            yCalled[c] = pieces[c][y].called;
        }
        return yCalled;
    }

    public boolean bingoChecker(boolean[] checking)
    {
        for (boolean entry : checking)
        {
            if (!entry)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @return if Diagonal Bingo achieved
     */
    public int diagonalBingo()
    {
        if (pieces[0][0].called && pieces[1][1].called && pieces[2][2].called
                && pieces[3][3].called && pieces[4][4].called)
        {
            return 1;
        }
        if (pieces[0][4].called && pieces[1][3].called && pieces[2][2].called
                && pieces[3][1].called && pieces[4][0].called)
        {
            return 2;
        }
        return -1;
    }

    /**
     * return -1 - no bingo return
     * 
     * @return row of bingo
     */
    public int horizontalBingo()
    {
        for (int c = 0; c < 5; c++)
        {
            if (bingoChecker(horizontalHelper(c)))
            {
                return c;
            }
        }
        return -1;
    }

    /**
     * return -1 - no bingo return
     * 
     * @return col of bingo
     */
    public int verticalBingo()
    {
        for (int c = 0; c < 5; c++)
        {
            if (bingoChecker(verticalHelper(c)))
            {
                return c;
            }
        }
        return -1;
    }

	/**
	* 
	* @return [bingo code, location of bingo]
	*/
    public int[] bingoChecking()
    {
        int[] sol = {0,0};
        if (horizontalBingo() != -1)
        {
            sol = new int[]{1, horizontalBingo()};
        }
        else if (verticalBingo() != -1)
        {
            sol = new int[]{2, verticalBingo()};
        }
        else if (diagonalBingo() != -1)
        {
            sol = new int[]{3, diagonalBingo()};
        }
        return sol;
    }

    public void printVal()
    {
        for (BingoPiece[] i : pieces)
        {
            for (BingoPiece j : i)
            {
                System.out.print(j.value + " ");

            }
            System.out.println();
        }

    }

    public void printCalls()
    {
        for (BingoPiece[] i : pieces)
        {
            for (BingoPiece j : i)
            {
                if (j.called)
                    System.out.print("X ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }

    }
    /*
    * return - string representation of values  separated by spaces;
     */
    public String toString()
    {
        String out = "";
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j< 5; j++)
            {
                BingoPiece p = pieces[i][j];
                out += p.value + " ";
            }
            out += "\n";
        }
        return out;
    }

}
