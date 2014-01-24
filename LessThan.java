
/**
 * LessThan contains constraint and point to Futoshiki squares that are involved with it.
 * 
 * @author      Radek Lochman
 * @version     1.1
 */
public class LessThan extends Constraint
{
    /**
     * Constructor for objects of class GreaterThan
     * 
     * @param a     A row coordinate of square next to constraint
     * @param b     A column coordinate of square next to constraint
     * @param c     A row coordinate of square next to constraint
     * @param d     A column coordinate of square next to constraint
     * @param str   A String value for constraint
     * @param x     A FutoshikiPuzzle which containts the constraint
     */
    public LessThan(int a, int b, int c, int d, String str, FutoshikiPuzzle x)
    {
        super(a, b, c, d, str, x);
    }
    
    /**
     * Shows violations of current constraint
     * 
     * @return      A string containing violations of current constraint
     */
    public String getProblem()
    {
        return currentProblem;
    }
    
    /**
     * Check for invalid numbers in FutoshikiSquares on both sides of constraint and if they violate the constraint
     * 
     * @return      False if there is any violation of constraint, true otherwise
     */
    public boolean isFine()
    {
        if(getSquareA().getMainValue() > getSquareB().getMainValue() && getSquareA().getMainValue() != 0 && getSquareB().getMainValue() !=0)
        {
            currentProblem += "Number in box [" + getSquareA().getSquareRow() + "][" + getSquareA().getSquareColumn() + "] is greater than number in box [" + getSquareB().getSquareRow() + "][" + getSquareB().getSquareColumn() + "]";
            return false;
        }
        else if(getSquareB().getMainValue() == 1)
        {
            if(!currentProblem.equals(""))
            {
                currentProblem += "\n";
            }
            currentProblem += "Number in box [" + getSquareB().getSquareRow() + "][" + getSquareB().getSquareColumn() + "] must be greater than 1";
            return false;
        }
        else if(getSquareA().getMainValue() == puzzle.getGridSize())
        {
            if(!currentProblem.equals(""))
            {
                currentProblem += "\n";
            }
            currentProblem += "Number in box [" + getSquareA().getSquareRow() + "][" + getSquareA().getSquareColumn() + "] must be smaller than " + puzzle.getGridSize() + "";
            return false;
        }
        else
        {
            return true;
        }
    }
}
