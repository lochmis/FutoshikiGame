import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.io.Serializable;

/**
 * FutoshikiSquare contain value for square of futhoshiki puzzle and its coordinates.
 * 
 * @author      Radek Lochman
 * @version     1.1
 */
public class FutoshikiSquare extends JButton
{
    private int mainValue;
    private int squareRow;
    private int squareColumn;
    private boolean editable;

    /**
     * Constructor for objects of class FutoshikiSquare
     * 
     * @param x         Row coordinate of square
     * @param y         Column coordinate of square
     * @param mValue    Value in square
     */
    public FutoshikiSquare(int x, int y, int mValue)
    {
        squareRow = x; // row coordinate
        squareColumn = y; // column coordinate
        mainValue = mValue; // value of the square
        setPreferredSize(new Dimension(60, 60)); // Size of the FutoshikiSquare button
        // Set text of the button
        if(mValue==0)
        {
            setText(" ");
        }
        else
        {
            setText(""+mValue);
        }
        setFont(new Font("sansserif",Font.BOLD,24)); // Font of the button
        editable = true; // boolean representing if the button is locked
        setOpaque(true);
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
     * Get main value stored in square
     * 
     * @return      A mainValue of square
     */
    public int getMainValue()
    {
        return mainValue;
    }
    
    /**
     * Get column coordinate of square
     * 
     * @return      A column coordinate of square
     */
    public int getSquareColumn()
    {
        return squareColumn;
    }
    
    /**
     * Get row coordinate of square
     * 
     * @return      A row coordinate of square
     */
    public int getSquareRow()
    {
        return squareRow;
    }
    
    /**
     * Get main value in form of String
     * 
     * @return      String representation of main value
     */
    public String getSymbol()
    {
        if(mainValue==0)
        {
            return " ";
        }
        else
        {
            return ""+mainValue;
        }
    }
    
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
            setBackground(new Color(180, 180, 180));
        }
        else
        {
            setBackground(null);
        }
    }
    
    /**
     * Change the main value of square
     * 
     * @param setValue         Main value for the square
     */
    public void setMainValue(int setValue)
    {
        mainValue = setValue;
        if(mainValue==0)
        {
            setText(" ");
        }
        else
        {
            setText(""+mainValue);
        }
    }
}
