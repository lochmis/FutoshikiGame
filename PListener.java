import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class PListener controlling the Show/Hide Problems
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class PListener implements ActionListener 
{
    private FutoshikiPuzzle puzzle;
    private JButton button1;
    
    /**
     * Constructor for PListener
     * 
     * @param b1    Current button
     * @param puzz  The current FutoshikiPuzzle
     */
    public PListener(JButton b1, FutoshikiPuzzle puzz) 
    {
        puzzle = puzz;
        button1 = b1;
    }
    
    /**
     * When button is pressed, check what does it say and change the value of ShowProblems accordingly
     */
    public void actionPerformed(ActionEvent e) 
    {
        if(button1.getText().equals("Show Problems"))
        {
            button1.setText("Hide Problems");
            puzzle.setShowProblems(true);
            puzzle.checkErrors();
        }
        else if(button1.getText().equals("Hide Problems"))
        {
            button1.setText("Show Problems");
            puzzle.setShowProblems(false);
            puzzle.checkErrors();
        }
    }
}