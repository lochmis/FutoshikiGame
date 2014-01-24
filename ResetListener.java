import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class ResetListener controlling the reset button
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class ResetListener implements ActionListener 
{

    private FutoshikiPuzzle puzzle;
    private FutoshikiGUI panel;
    
    /**
     * Constructor for ResetListener
     * 
     * @param puzz  The current FutoshikiPuzzle
     * @param a     The GUI window of the game
     */
    public ResetListener(FutoshikiPuzzle puzz, FutoshikiGUI a) 
    {
        puzzle = puzz;
        panel = a;
    }
    
    /**
     * When button is pressed it restores the grid to its initial state
     */
    public void actionPerformed(ActionEvent e) 
    {
        // Ask user if they are sure about reseting the grid to its initial value and proceed only when OK is pressed
        int confirm = JOptionPane.showConfirmDialog((Component) null, "Are you sure that you want\nto restart current game?","alert", JOptionPane.OK_CANCEL_OPTION);
        if(confirm==0)
        {
            puzzle.restoreBackup();
            panel.setupGrid();
        }
    }
}