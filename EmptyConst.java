
/**
 * EmptyConst contains constraint that has no value.
 * 
 * @author      Radek Lochman
 * @version     1.0
 */
public class EmptyConst extends Constraint
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
    public EmptyConst(int a, int b, int c, int d, String str, FutoshikiPuzzle x)
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
     * @return      True as the Empty constraint can't be violated
     */
    public boolean isFine()
    {
        return true;
    }
}