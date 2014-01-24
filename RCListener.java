import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class RCListener controlling the row constraints
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class RCListener implements ActionListener 
{

    private Constraint con; // where to send the output 
    private FutoshikiPuzzle puzzle;
    private FutoshikiGUI panel;
    
    /**
     * Constructor for RCListener
     * 
     * @param c     The current Constraint object
     * @param p     The current FutoshikiPuzzle
     * @param a     The GUI window of the game
     */
    public RCListener(Constraint c, FutoshikiPuzzle p, FutoshikiGUI a) 
    {
        con = c;
        puzzle = p;
        panel = a;
    }
    
    /**
     * When the button is pressed, rotate between ">" "<" and empty constraint
     */
    public void actionPerformed(ActionEvent e) 
    {
        if(con.getEditable())
        {
            // Rotate between GreaterThen, LessThen and EmptyConst
            if(con.getText().equals(" "))
            {
                puzzle.setRowConstraint(con.getRow(), con.getColumn(), ">");
            }
            else if(con.getText().equals(">"))
            {
                puzzle.setRowConstraint(con.getRow(), con.getColumn(), "<");
            }
            else if(con.getText().equals("<"))
            {
                puzzle.setRowConstraint(con.getRow(), con.getColumn(), " ");
            }
            // Reload the main grid
            panel.setupGrid();
            // Check for errors
            if(puzzle.getShowProblems())
            {
                puzzle.checkErrors();
            }
        }
    }
}