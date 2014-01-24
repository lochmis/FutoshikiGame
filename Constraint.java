import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

/**
 * Abstract class Constraint
 * 
 * @author      Radek Lochman
 * @version     1.1
 */
public abstract class Constraint extends JButton
{
    protected int x1; 
    protected int y1;
    protected int x2;
    protected int y2;
    protected String symbol;
    protected FutoshikiPuzzle puzzle;
    protected boolean editable;
    protected String currentProblem;

    /**
     * Constructor for objects of class FutoshikiPuzzle
     * 
     * @param a     A row coordinate of square next to constraint
     * @param b     A column coordinate of square next to constraint
     * @param c     A row coordinate of square next to constraint
     * @param d     A column coordinate of square next to constraint
     * @param str   A String value for constraint
     * @param x     A FutoshikiPuzzle which containts the constraint
     */
    public Constraint(int a, int b, int c, int d, String str, FutoshikiPuzzle x)
    {
        x1 = a;
        y1 = b;
        x2 = c;
        y2 = d;
        symbol = str;
        puzzle = x;
        setPreferredSize(new Dimension(20, 20)); // Default size of the constraint button
        setMargin(new Insets(0,0,0,0)); // Set margin for constraint button so the symbol is visible even on small button
        setText(str);
        editable = true; // boolean representing if the button is locked
        currentProblem = "";
        setOpaque(true);
    }
    
    /**
     * Get column coordinate of current constraint
     * 
     * @return      Return integer column coordinate of current constraint
     */
    public int getColumn()
    {
        return y1;
    }
    
    /**
     * Get the value of variable "editable"
     * 
     * @return  True if the button is editable, False otherwise.
     */
    public boolean getEditable()
    {
        return editable;
    }
    
    /**
     * Shows violations of current constraint
     * 
     * @return      A string containing violations of current constraint
     */
    public abstract String getProblem();
    
    /**
     * Get row coordinate of current constraint
     * 
     * @return      Return integer row coordinate of current constraint
     */
    public int getRow()
    {
        return x1;
    }
    
    /**
     * Get a FutoshikiSquare before the constraint
     * 
     * @return      A FutoshikiSquare before the constraint
     */
    public FutoshikiSquare getSquareA()
    {
        return puzzle.getButton(x1, y1);
    }
    
    /**
     * Get a FutoshikiSquare after the constraint
     * 
     * @return      A FutoshikiSquare after the constraint
     */
    public FutoshikiSquare getSquareB()
    {
        return puzzle.getButton(x2, y2);
    }
    
    /**
     * Get symbol of the constraint
     * 
     * @return      Return String symbol representing the constraint
     */
    public String getSymbol()
    {
        return symbol;
    }
    
    /**
     * Check for invalid numbers in FutoshikiSquares on both sides of constraint and if they violate the constraint
     * 
     * @return      False if there is any violation of constraint, true otherwise
     */
    public abstract boolean isFine();
    
    /**
     * Set the value editable to either True or False. If the editable is false, background color of the button will be grey.
     * 
     * @param a     A boolean value to set variable "editable"
     */
    public void setEditable(boolean a)
    {
        editable = a;
        if(!a)
        {
            setForeground(new Color(150, 150, 150));
            setOpaque(true);
        }
        else
        {
            setForeground(null);
        }
    }
    
    /**
     * Set symbol representing current constraint
     * 
     * @param symb      String to input as symbol in the constraint
     */
    public void setSymbol(String symb)
    {
        symbol = symb;
    }
}
