import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

/** 
 * Class NumberListener controlling each of Futoshiki square
 *
 * @author      Radek Lochman
 * @version     1.0
 */
public class NumberListener implements ActionListener 
{

    private FutoshikiSquare square; 
    private FutoshikiPuzzle puzzle;
    
    /**
     * Constructor for NumberListener
     * 
     * @param sqr   The FutoshikiSquare that has been clicked
     * @param puzz  The FutoshikiPuzzle that contain the square
     */
    public NumberListener(FutoshikiSquare sqr, FutoshikiPuzzle puzz) 
    {
        square = sqr;
        puzzle = puzz;
    }
    
    /**
     * When the button is pressed, increase its value by 1 untill it reach the gridSize
     * When it reach the gridSize return to value 0
     */
    public void actionPerformed(ActionEvent e) {
        if(square.getEditable())
        {
            int currItem = square.getMainValue();
            if(currItem < puzzle.getGridSize())
            {
                currItem++;
                square.setMainValue(currItem);
            }
            else
            {
                square.setMainValue(0);
            }
            puzzle.checkErrors();
            puzzle.showComplete();
        }
    }
}