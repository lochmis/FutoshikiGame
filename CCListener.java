import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class CCListener controlling the column constraints
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class CCListener implements ActionListener 
{

    private Constraint con; // where to send the output 
    private FutoshikiPuzzle puzzle;
    private FutoshikiGUI panel;
    
    /**
     * Constructor for CCListener
     * 
     * @param c     The current Constraint object
     * @param p     The current FutoshikiPuzzle
     * @param a     The GUI window of the game
     */
    public CCListener(Constraint c, FutoshikiPuzzle p, FutoshikiGUI a) 
    {
        con = c;
        puzzle = p;
        panel = a;
    }
    
    /**
     * When the button is pressed, rotate between "v" "^" and empty constraint
     */
    public void actionPerformed(ActionEvent e) {
        if(con.getEditable())
        {
            // Rotate between GreaterThen, LessThen and EmptyConst
            if(con.getText().equals(" "))
            {
                puzzle.setColumnConstraint(con.getRow(), con.getColumn(), "v");
            }
            else if(con.getText().equals("v"))
            {
                puzzle.setColumnConstraint(con.getRow(), con.getColumn(), "^");
            }
            else if(con.getText().equals("^"))
            {
                puzzle.setColumnConstraint(con.getRow(), con.getColumn(), " ");
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