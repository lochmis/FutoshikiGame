import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class LoadListener controlling the Load Game button
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class LoadListener implements ActionListener 
{

    private FutoshikiPuzzle puzzle;
    private FutoshikiGUI panel;
    
    /**
     * Constructor for LoadListener
     * 
     * @param puzz  The current FutoshikiPuzzle
     * @param a     The GUI window of the game
     */
    public LoadListener(FutoshikiPuzzle puzz, FutoshikiGUI a) 
    {
        puzzle = puzz;
        panel = a;
    }
    
    /**
     * The method calls the function loadGame() of the FutoshikiPuzzle and refresh the GUI
     */
    public void actionPerformed(ActionEvent e) {
        puzzle.loadGame();
        panel.setupGrid();
    }
}