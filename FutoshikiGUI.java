import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main window for Futoshiki Puzzle showing the grid and buttons that control the grid.
 * 
 * @author Radek Lochman 
 * @version 1.0
 **/
public class FutoshikiGUI extends JFrame
{
    private FutoshikiPuzzle game; 
    private JPanel mainGrid; //JPanel that contains representation of the FutoshikiPuzzle

    /**
     * Constructor for FutoshikiGUI. Doesn't need any input.
     */
    public FutoshikiGUI()
    {
        super("Futoshiki Puzzle by Radek Lochman"); // Give a title to the FutoshikiGUI window
        init(); // Run code to initialize the window
    }
    
    /**
     * Main method for FutoshikiGUI
     * 
     * @param args      Please leave blank as it has no function
     */
    public static void main(String[] args)
    {
        FutoshikiGUI GameGrid = new FutoshikiGUI();
    }
    
    /** 
     * Set up the GUI, creates the panel for the grid and creates control buttons
     */
    private void init() 
    {
        /***********************************************************
         *                  Set up the new game                    *
         ***********************************************************/
        
        game = new FutoshikiPuzzle(5); // Create new game
        game.fillFirstPuzzle(); // Pre-fill the new game with 5 random values 
                
        /***********************************************************
         *          Setup the layout of the FutoshikiGUI           *
         ***********************************************************/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //necesary to exit the program upon closing the window
        this.setSize(620, 500); //set the size of the window
        this.setResizable(false); //lock the size of the window

        // Container for the whole window with border layout
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout()); 
        
        // Panel that will hold controls and legend
        JPanel RMenu = new JPanel(); 
        RMenu.setLayout(new GridLayout(2, 0));
        pane.add("East", RMenu);
        
        // Panel for upper half of RMenu that will contain all the buttons
        JPanel RMenuT = new JPanel();
        RMenuT.setLayout(new GridLayout(8, 0));
        RMenu.add(RMenuT);
        
        // Legend to show what each color means
        Image legendjpg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("legend.jpg"));
        JLabel legend = new JLabel(new ImageIcon(legendjpg));
        RMenu.add(legend);
        
        // Set up JPanel that will hold the Futoshiki Grid
        mainGrid = new JPanel();
        mainGrid.setLayout(new FlowLayout());
        pane.add("Center", mainGrid);
        
        /***********************************************************
         * All the buttons with ActionListeners for the right menu *
         ***********************************************************/
        
        // Text describing the TextField with text aligned to center
        JLabel label = new JLabel("Game Controls");
        label.setHorizontalAlignment(JTextField.CENTER);
        label.setFont(new Font("sansserif",Font.BOLD,18));
        RMenuT.add(label);
        
        // Button to create a new game
        JButton newGame = new JButton("New Random Game");
        newGame.addActionListener(new NewListener(game, this));
        RMenuT.add(newGame);
        
        // Button to restart current game to the initial state
        JButton resetGame = new JButton("Restart Current Game");
        resetGame.addActionListener(new ResetListener(game, this));
        RMenuT.add(resetGame);
        
        // Button to show legality of current game
        JButton shLegal = new JButton("Check Legality");
        shLegal.addActionListener(new LegalListener(shLegal, game));
        RMenuT.add(shLegal);
        
        // Button to toggle showing current problems in the grid
        JButton shProblems = new JButton("Show Problems");
        shProblems.addActionListener(new PListener(shProblems, game));
        RMenuT.add(shProblems);
        
        // Button to save current state of the game
        JButton saveG = new JButton("Save Game");
        saveG.addActionListener(new SaveListener(game));
        RMenuT.add(saveG);
        
        // Button to load last saved state of the game
        JButton loadG = new JButton("Load Game");
        loadG.addActionListener(new LoadListener(game, this));
        RMenuT.add(loadG);
        
        // Button to solve the current game
        JButton solver = new JButton("Solve Current Game");
        solver.addActionListener(new SolveListener(game));
        RMenuT.add(solver);
        
        
        setupGrid(); // Load buttons and Constraints  to GUI
        setVisible(true); // make the window visible
    }
    
    /**
     * Erase the content of mainGrid and fill it up with the actual contents of FutoshikiPuzzle
     */
    public void setupGrid() 
    {
        // Remove all the contents of mainGrid and refresh
        mainGrid.removeAll();
        mainGrid.updateUI();
        
        for(int x=0; x<5; x++)
        {
            // Creates sub-panel for row of numbers and RowConstraints with preffered size
            JPanel panelx = new JPanel();   
            panelx.setLayout(new FlowLayout()); 
            panelx.setPreferredSize( new Dimension( 430, 65 ) ); 
            mainGrid.add(panelx);  
            
            //creates sub-panel for ColumnConstraints with rigid area on the left
            JPanel cpanelx = new JPanel(); 
            cpanelx.setLayout(new BoxLayout(cpanelx, BoxLayout.X_AXIS)); 
            cpanelx.add(Box.createHorizontalGlue()); 
            cpanelx.add(Box.createRigidArea(new Dimension(70, 0)));
            
            // loop to fill up the sub-panels
            for(int y=0; y<5; y++)
            {
                // Add FutoshikiSquare to subpanel
                panelx.add(game.getButton(x, y));
                if(y<4)
                {
                    // Add rowConstraint and it's listener to subpanel
                    panelx.add(game.getRConstraint(x, y));
                    game.getRConstraint(x, y).addActionListener(new RCListener(game.getRConstraint(x, y), game, this));
                }
                if(x<4)
                {
                    // Add columnConstraint and it's listener to subpanel
                    cpanelx.add(game.getCConstraint(x, y));
                    game.getCConstraint(x, y).addActionListener(new CCListener(game.getCConstraint(x, y), game, this));
                    cpanelx.add(Box.createRigidArea(new Dimension(70, 0)));
                }
            }
            // Add subpanels to the mainGrid
            mainGrid.add(panelx);
            mainGrid.add(cpanelx);
        }
    }
    
    /**
     * Set the visibility of FutoshikiGUI
     * 
     * @param v     A boolean value to set visibility. True to make visible, false otherwise
     */
    public void setVisible(boolean v)
    {
        super.setVisible(v);
    }
}