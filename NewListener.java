import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class NewListener controlling the textfield and button for New game
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class NewListener implements ActionListener 
{

    private FutoshikiPuzzle puzzle;
    private FutoshikiGUI page;
    
    /**
     * Constructor for NewListener
     * 
     * @param puzz  The current FutoshikiPuzzle
     * @param p     The GUI window of the game
     */
    public NewListener(FutoshikiPuzzle puzz, FutoshikiGUI p) 
    {
        puzzle = puzz;
        page = p;
    }
    
    /**
     * The method set the number of pre-sets for the game and prefill the puzzle with that ammount
     */
    public void actionPerformed(ActionEvent e) 
    {
        // Pop-up message asking for the number of pre-sets in new game
        String result = JOptionPane.showInputDialog(new JFrame(), "Enter a number of numbers/constraints:\nLeave 0 for new game to be empty.", "5");
        // If CANCEL is pressed, nothing will happen, if OK is pressed assign value
        if(result!=null)
        {
            if(result.equals(""))
            {
                result = "" + 0;
            }
            int confirm = 0;
            // If user inputs any number than 10 he gets warning message that it might slow downt their PC
            if(Integer.parseInt(result)>10)
            {
                confirm = JOptionPane.showConfirmDialog((Component) null, "Any number above 10 can make your\nPC unresponsive for some time!\nDo you want to continue?","alert", JOptionPane.OK_CANCEL_OPTION);
            }
            // If user confirm the warning or no warning was necessary, fill puzzle with chosen number of prefills and reset the grid
            if(confirm==0)
            {
                puzzle.fillPuzzle(Integer.parseInt(result));
                page.setupGrid();
            }
        }
    }
}