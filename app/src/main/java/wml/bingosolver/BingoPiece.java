package wml.bingosolver;

/**
 * Represents one of the 25 spots on a single Bingo board
 * 
 * @author Billy
 * 
 */
public class BingoPiece
{
    public int value;
    public boolean called;

    public boolean reset()
    {
        called = false;
        return called;
    }

    public BingoPiece(int v, boolean c)
    {
        value = v;
        called = c;
    }

    public BingoPiece(int v)
    {
        value = v;
        called = false;
    }


}
