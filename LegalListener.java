import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/**
 * Listener for button "Check Legality" that invokes a isLegal() function in underlying puzzle
 * 
 * @author      Radek Lochman
 * @version     1.0
 */
public class LegalListener implements ActionListener
{
    private FutoshikiPuzzle puzzle;
    private JButton button1;
    
    /**
     * Constructor for LegalListener
     * 
     * @param b1    Current button
     * @param puzz  The current FutoshikiPuzzle
     */
    public LegalListener(JButton b1, FutoshikiPuzzle puzz)
    {
        puzzle = puzz;
        button1 = b1;
    }

    /**
     * When button is pressed, run isLegal() to check legality of the game
     */
    public void actionPerformed(ActionEvent e) 
    {
        if(puzzle.isLegal())
        {
            JOptionPane.showMessageDialog(null, "So far there are no problems", "Game Legality", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Current state of the game is not legal", "Game Legality", JOptionPane.ERROR_MESSAGE);
        }
    }
}
