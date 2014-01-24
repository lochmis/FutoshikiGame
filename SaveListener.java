import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class SaveListener controlling the Save Game button
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class SaveListener implements ActionListener 
{

    private FutoshikiPuzzle puzzle;
    
    /**
     * Constructor for SaveListener
     * 
     * @param puzz   The current FutoshikiPuzzle
     */
    public SaveListener(FutoshikiPuzzle puzz) 
    {
        puzzle = puzz;
    }
    
    /**
     * The method calls the function saveGame() of the FutoshikiPuzzle
     */
    public void actionPerformed(ActionEvent e) {
        puzzle.saveGame();
    }
}