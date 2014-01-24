import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class SolveListener controlling the solve puzzle button
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class SolveListener implements ActionListener 
{

    private FutoshikiPuzzle puzzle;
    
    /**
     * Constructor for SolveListener
     * 
     * @param puzz  The current FutoshikiPuzzle
     */
    public SolveListener(FutoshikiPuzzle puzz) 
    {
        puzzle = puzz;
    }
    
    /**
     * When button is pressed, it run solve() function of current game and prints message according to result
     */
    public void actionPerformed(ActionEvent e) {
        if(puzzle.solve())
        {
            JOptionPane.showMessageDialog(null, "Congratulations!\nThe puzzle is solved.", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Sorry, current puzzle is unsolvable", "alert", JOptionPane.ERROR_MESSAGE);
        }
    }
}