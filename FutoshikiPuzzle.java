import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Game of FutoshikiPuzzle that holds values of each square, constrains and means to edit them.
 * 
 * @author      Radek Lochman
 * @version     1.2
 */
public class FutoshikiPuzzle
{
    private int gridSize; // Size of game grid
    private String[] rowSigns; // Symbols for row constraints
    private String[] columnSigns; // Symbols for column constraints
    private FutoshikiSquare[][] grid; // 2-dimensional array of FutoshikiSquare objects
    private Constraint[][] rowConstraints; // 2-dimensional array of Constraint objects
    private Constraint[][] columnConstraints; // 2-dimensional array of Constraint objects
    private String allProblems; // String containing all the problems with current game
    private boolean ShowProblems; // Boolean to show whether to look for errors or not
    private FutoshikiPuzzle gameBackup; // backup of the game
    
    /**
     * Constructor for objects of class FutoshikiPuzzle
     * 
     * @param sizeOfGrid    A size of grid for the puzzle
     */
    public FutoshikiPuzzle(int sizeOfGrid)
    {
        if(sizeOfGrid > 999)
        {
            System.out.println("Please choose grid size smaller than 1000 by 1000");
        }
        else
        {
            ShowProblems = false; 
            gridSize = sizeOfGrid; 
            grid = new FutoshikiSquare[gridSize][gridSize];
            rowConstraints = new Constraint[gridSize][gridSize-1];
            columnConstraints = new Constraint[gridSize-1][gridSize];
            allProblems = "";
            gameBackup = null;
            for(int i = 0; i < gridSize; i++)
            {
                for(int j = 0; j < gridSize; j++)
                {
                    grid[i][j] = new FutoshikiSquare(i, j, 0);
                    grid[i][j].addActionListener(new NumberListener(grid[i][j], this));
                }
            }
            for(int x=0; x<gridSize; x++)
            {
                for(int y=0; y<gridSize-1; y++)
                {
                    setRowConstraint(x, y, " ");
                    setColumnConstraint(y, x, " ");
                }
            }
        }
    }
    
    /**
     * Main method for FutoshikiPuzzle
     * 
     * @param args      First number you type in will determine size of grid, second will specify number of pre-filled numbers and/or constraints 
     * For example type 5 and 7 to get 5x5 grid with 7 pre-filled numbers and/or constraints
     */
    public static void main(String[] args)
    {
        FutoshikiPuzzle futoshiki1 = new FutoshikiPuzzle(Integer.parseInt(args[0]));
        futoshiki1.fillPuzzle(Integer.parseInt(args[1]));
    }
    
    /**
     * Go through whole grid, check each square and colour it red if it violate the grid, get default background if its fine
     */
    public void checkErrors()
    {
        for(int i=0; i<gridSize; i++)
        {
            for(int j=0; j<gridSize; j++)
            {
                if(ShowProblems && !validSquare(i,j))
                {
                    // if there is problem with the square, colour it red
                    grid[i][j].setBackground(Color.RED);
                }
                else if(!grid[i][j].getEditable())
                {
                    grid[i][j].setBackground(new Color(180, 180, 180));
                }
                else
                {
                    grid[i][j].setBackground(null);
                }
                if(j<gridSize-1)
                {
                    if(ShowProblems && !getRConstraint(i, j).isFine())
                    {
                        rowConstraints[i][j].setForeground(Color.RED);
                    }
                    else if(!rowConstraints[i][j].getEditable())
                    {
                        rowConstraints[i][j].setForeground(new Color(150, 150, 150));
                    }
                    else
                    {
                        rowConstraints[i][j].setForeground(null);
                    }
                }
                if(i<gridSize-1)
                {
                    if(ShowProblems && !getCConstraint(i, j).isFine())
                    {
                        columnConstraints[i][j].setForeground(Color.RED);
                    }
                    else if(!columnConstraints[i][j].getEditable())
                    {
                        columnConstraints[i][j].setForeground(new Color(150, 150, 150));
                    }
                    else
                    {
                        columnConstraints[i][j].setForeground(null);
                    }
                }
            }
        }
    }
    
    /**
     * Copy one game to another, not just pointers, copy actual values.
     * 
     * @param a     FutoshikiPuzzle that needs to be backed up
     * @param b     FutoshikiPuzzle that is backed up to
     */
    public void copyGames(FutoshikiPuzzle a, FutoshikiPuzzle b)
    {
        for(int x=0; x<5; x++)
        {
            for(int y=0; y<5; y++)
            {
                //get value of grid a and change value of grid b to the same
                b.setSquare(x, y, a.getSquare(x, y));
                if(!a.getButton(x, y).getEditable())
                {
                    b.getButton(x, y).setEditable(false);
                }
                if(y<4 && a.getRConstraint(x, y)!=null)
                {
                    //get value of constraint a and change value of constraint b to the same
                    b.setRowConstraint(x, y, a.getRConstraint(x, y).getSymbol());
                    if(!a.getRConstraint(x, y).getEditable())
                    {
                        b.getRConstraint(x, y).setEditable(false);
                    }
                }
                if(x<4 && a.getCConstraint(x, y)!=null)
                {
                    //get value of constraint a and change value of constraint b to the same
                    b.setColumnConstraint(x, y, a.getCConstraint(x, y).getSymbol());
                    if(!a.getCConstraint(x, y).getEditable())
                    {
                        b.getCConstraint(x, y).setEditable(false);
                    }
                }
            }
        }
    }
    
    /**
     * Create predefined puzzle
     */
    public void fillFirstPuzzle()
    {
        grid[0][3].setMainValue(2);
        grid[0][3].setEditable(false);
        grid[2][1].setMainValue(4);
        grid[2][1].setEditable(false);
        grid[3][4].setMainValue(1);
        grid[3][4].setEditable(false);
        setRowConstraint(1, 3, ">");
        rowConstraints[1][3].setEditable(false);
        setColumnConstraint(1, 1, "^");
        columnConstraints[1][1].setEditable(false);
        gameBackup = new FutoshikiPuzzle(gridSize);
        copyGames(this, gameBackup);
    }
    
    /**
     * Creates a random number of occurences to fill some fields, and/or constraints. 0 will empty the puzzle.
     * 
     * @param  fillNum        A number of random constraints and/or numbers to be pre-filled
     */
    public void fillPuzzle(int fillNum)
    {
        if(fillNum>=0 && fillNum<(1 + (gridSize*gridSize)+(gridSize*(gridSize-1))+(gridSize*(gridSize-1))))
        {
            //every time this method is called, reset the board
            resetPuzzle();
            //this code will create two small arrays containing needed signs
            String[] rowSigns = new String[]{">","<"};
            String[] columnSigns = new String[]{"v","^"};
            //starting with loop that will run fillNum times
            for(int i=0; i<fillNum; i++)
            {
                int r = 0;
                int c = 0;
                //randomly choose which one of three options to use
                //add random number or constraint to random place
                //first switch is in place so that there is 50:50 chance to get either random square or constraint
                //the second switch (in "case 2") is to have 50:50 chance to get either random row or column constraint
                switch ((int)(1+Math.random()*2)) 
                {
                    case 1: r = (int)(Math.random()*gridSize);
                            c = (int)(Math.random()*gridSize);
                            if(grid[r][c].getMainValue()==0)
                            {
                                grid[r][c].setMainValue((int)(Math.random()*gridSize)+1);
                                grid[r][c].setEditable(false);
                                // If grid becomes illegal by adding the last change, remove it and decrease count
                                if(!isLegal())
                                {
                                    grid[r][c].setMainValue(0);
                                    grid[r][c].setEditable(true);
                                    i--;
                                }
                            }
                            else
                            {
                                i--;
                            }
                            break;
                            
                    case 2: switch ((int)(1+Math.random()*2)) 
                            {
                                case 1: r = (int)(Math.random()*gridSize-1);
                                        c = (int)(Math.random()*gridSize);
                                        if(columnConstraints[r][c].getSymbol().equals(" "))
                                        {
                                            // If grid becomes illegal by adding the last change, remove it and decrease count
                                            setColumnConstraint(r, c, columnSigns[(int)(Math.random()*2)]);
                                            columnConstraints[r][c].setEditable(false);
                                            if(!isLegal())
                                            {
                                                setColumnConstraint(r, c, " ");
                                                i--;
                                            }
                                        }
                                        else
                                        {
                                            i--;
                                        }
                                        break;
                                        
                                case 2: r = (int)(Math.random()*gridSize);
                                        c = (int)(Math.random()*gridSize-1);
                                        if(rowConstraints[r][c].getSymbol().equals(" "))
                                        {
                                            // If grid becomes illegal by adding the last change, remove it and decrease count
                                            setRowConstraint(r, c, rowSigns[(int)(Math.random()*2)]);
                                            rowConstraints[r][c].setEditable(false);
                                            if(!isLegal())
                                            {
                                                setRowConstraint(r, c, " ");
                                                i--;
                                            }
                                        }
                                        else
                                        {
                                            i--;
                                        }
                                        break;
                            }
                            break;
                }
            }
            // Backup current game
            gameBackup = new FutoshikiPuzzle(gridSize);
            copyGames(this, gameBackup);
            // If the game is unsolvable, create new game
            if(!solve())
            {
                fillPuzzle(fillNum);
            }
            // If the game is possible to solve, reload it from backup
            else
            {
                copyGames(gameBackup, this);
            }
        }
        else
        {
            System.out.println("You can only input number between 0 and " + (1 + (gridSize*gridSize)+(gridSize*(gridSize-1))+(gridSize*(gridSize-1))));
        }
    }
    
    /**
     * search grid for and square that is empty (square with value 0)
     * 
     * @return      A FutoshikiSquare object with value 0
     */
    public FutoshikiSquare findEmpty()
    {
        for(int x=0; x<gridSize; x++)
        {
            for(int y=0; y<gridSize; y++)
            {
                if(grid[x][y].getMainValue()==0)
                {
                    return grid[x][y];
                }
            }
        }
        // return null if there is no square left with value 0
        return null;
    }
    
    /**
     * Get the object FutoshikiSquare from grid
     * 
     * @param a     An integer coordinate of row
     * @param b     An integer coordinate of column
     */
    public FutoshikiSquare getButton(int a, int b)
    {
        return grid[a][b];
    }
    
    /**
     * Get the object Constraint from columnConstraint
     * 
     * @param a     An integer coordinate of row
     * @param b     An integer coordinate of column
     */
    public Constraint getCConstraint(int a, int b)
    {
        return columnConstraints[a][b];
    }
    
    /**
     * Get a value of gridSize
     * 
     * @return      An integer value of gridSize
     */
    public int getGridSize()
    {
        return gridSize;
    }
    
    /**
     * Prints out all problems in current game
     */
    public void getProblems()
    {
        allProblems = "";
        for(int i=0; i<gridSize; i++)
        {
            for(int j=0; j<(gridSize-1); j++)
            {
                validSquare(i,j);
                if(j<gridSize-1)
                {
                    if(!getRConstraint(i, j).isFine())
                    {
                        allProblems += rowConstraints[i][j].getProblem() + "\n";
                    }
                }
                if(i<gridSize-1)
                {
                    if(!getCConstraint(i, j).isFine())
                    {
                        allProblems += columnConstraints[i][j].getProblem() + "\n";
                    }
                }
            }
        }
        if(allProblems.equals(""))
        {
            System.out.println("So far there are no problems");
        }
        else
        {
            System.out.println(allProblems);
        }
    }
    
    /**
     * Get the object Constraint from rowConstraint
     * 
     * @param a     An integer coordinate of row
     * @param b     An integer coordinate of column
     */
    public Constraint getRConstraint(int a, int b)
    {
        return rowConstraints[a][b];
    }
    
    /**
     * Get a value of ShowProblems
     * 
     * @return      A boolean value of ShowProblems
     */
    public boolean getShowProblems()
    {
        return ShowProblems;
    }
    
    /**
     * Get value of FutoshikiSquare
     * 
     * @param a     An integer coordinate of row
     * @param b     An integer coordinate of column
     */
    public int getSquare(int a, int b)
    {
        return grid[a][b].getMainValue();
    }
    
    /**
     * Check if there is any empty square (square with value of 0) in current grid. 
     * If there is none, it check if there is any problem with current grid
     * If there is none, it returns true, false otherwise
     * 
     * @return      true if grid is full and legal, false otherwise
     */
    public boolean isComplete()
    {
        for(int x=0; x<gridSize; x++)
        {
            for(int y=0; y<gridSize; y++)
            {
                if(grid[x][y].getMainValue()==0)
                {
                    return false;
                }
            }
        }
        if(!isLegal())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Returns true if there are no violations of constraints and no repeating numbers in rows or columns
     * 
     * @return      False if there is more than one number of same value in row or column, or if any of the constraints were violated, true otherwise
     */
    public boolean isLegal()
    {
        boolean stillLegal = true;
        allProblems = "";
        for(int i=0; i<gridSize; i++)
        {
            if(validRow(i)==false)
            {
                stillLegal = false;
            }
            if(validColumn(i)==false)
            {
                stillLegal = false;
            }
        }
        if(validRowConstraints()==false)
        {
            stillLegal = false;
        }
        if(validColumnConstraints()==false)
        {
            stillLegal = false;
        }
        return stillLegal;
    }
    
    /**
     * Load last saved game from the text file
     */
    public void loadGame()
    {
        try
        {
            // Open the gameSave.txt file 
            FileInputStream input = new FileInputStream("gameSave.dat");
            DataInputStream in = new DataInputStream(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Write values from file to squares and constraints
            for(int x=0;x<gridSize;x++)
            {
                for(int y=0;y<gridSize;y++)
                {
                    String square = br.readLine();
                    if(square.equals(" "))
                    {
                        setSquare(x, y, 0);
                    }
                    else
                    {
                        setSquare(x, y, Integer.parseInt(square));
                    }
                    grid[x][y].setEditable(Boolean.valueOf(br.readLine()));
                    if(y<gridSize-1)
                    {
                        setRowConstraint(x, y, br.readLine());
                        rowConstraints[x][y].setEditable(Boolean.valueOf(br.readLine()));
                    }
                    if(x<gridSize-1)
                    {
                        setColumnConstraint(x, y, br.readLine());
                        columnConstraints[x][y].setEditable(Boolean.valueOf(br.readLine()));
                    }
                }
            }
            // Close the reader
            in.close();
            // Print message for user that game was successfuly loaded
            JOptionPane.showMessageDialog(null, "Game loaded", "Load Game", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e)
        {
            // Catch exception for any problem
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Prints out the puzzle
     */
    public void printPuzzle()
    {
        // body of this for-loop will run "gridSize" times
        for(int i=0; i<gridSize; i++)
        {
            //creates line which draws top and bottom edge of each field
            String fieldEdge = "";
            for(int l=0; l<gridSize; l++)
            {
                fieldEdge += "- - -   ";
            }
            //print top line of squares
            System.out.println(fieldEdge);
            //creates line with square sides, square values and rowConstraints
            String currentLine = "";
            for(int j=0; j<gridSize; j++)
            {
                //left side of square
                currentLine +="|";
                //value of square... if 0 than empty space
                if(grid[i][j].getMainValue()==0)
                {
                    currentLine +="   ";
                }
                else
                {
                    if(grid[i][j].getMainValue()<10)
                    {
                        currentLine += " " + grid[i][j].getMainValue() + " ";
                    }
                    else if(grid[i][j].getMainValue()<100)
                    {
                        currentLine += grid[i][j].getMainValue() + " ";
                    }
                    else if(grid[i][j].getMainValue()<1000)
                    {
                        currentLine += grid[i][j].getMainValue();
                    }
                }
                //right side of square
                currentLine += "|";
                //show constraint, if there is none, than empty space
                if(j<gridSize-1)
                {
                    if(rowConstraints[i][j]==null)
                    {
                        currentLine +="   ";
                    }
                    else
                    {
                        currentLine += " " + rowConstraints[i][j].getSymbol() + " ";
                    }
                }
            }
            System.out.println(currentLine);
            //print out bottom line of squares
            System.out.println(fieldEdge);
            //line between rows of boxes containing Constraints
            if(i<gridSize-1)
            {
                currentLine = "";
                for(int k=0; k<gridSize; k++)
                {
                    currentLine +=" ";
                    //print constraint at current location, if null then empty space
                    if(columnConstraints[i][k]==null)
                    {
                        currentLine +="   ";
                    }
                    else
                    {
                        currentLine += " " + columnConstraints[i][k].getSymbol() + " ";
                    }
                    currentLine +="    ";
                }
                //print constraints
                System.out.println(currentLine);
            }
        }
    }
    
    /**
     * Resets all the arrays to default values
     */
    private void resetPuzzle()
    {
        for(int i = 0; i < gridSize; i++)
        {
            for(int j = 0; j < gridSize; j++)
            {
                grid[i][j].setMainValue(0);
                grid[i][j].setEditable(true);
            }
        }
        for(int x=0; x<gridSize; x++)
        {
            for(int y=0; y<gridSize-1; y++)
            {
                setRowConstraint(x, y, " ");
                setColumnConstraint(y, x, " ");
            }
        }
    }
    
    /**
     * Restore grid from the gameBackup
     */
    public void restoreBackup()
    {
        copyGames(gameBackup, this);
    }
    
    /**
     * Save current state of the game to a text file
     */
    public void saveGame()
    {
        try
        {
            // Creates a file with name gameSave.txt 
            FileWriter output = new FileWriter("gameSave.dat");
            BufferedWriter out = new BufferedWriter(output);
            // Write the value of each square and constraint to the file
            for(int x=0;x<gridSize;x++)
            {
                for(int y=0;y<gridSize;y++)
                {
                    out.write(grid[x][y].getSymbol()+"\n");
                    out.write(grid[x][y].getEditable()+"\n");
                    if(y<gridSize-1)
                    {
                        out.write(rowConstraints[x][y].getSymbol()+"\n");
                        out.write(rowConstraints[x][y].getEditable()+"\n");
                    }
                    if(x<gridSize-1)
                    {
                        out.write(columnConstraints[x][y].getSymbol()+"\n");
                        out.write(columnConstraints[x][y].getEditable()+"\n");
                    }
                }
            }
            // Close the writer
            out.close();
            // Inform user that game has been saved
            JOptionPane.showMessageDialog(null, "Game saved", "Save Game", JOptionPane.INFORMATION_MESSAGE);
        }
        // Catch exception for any problem
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Assigns a sign to constraint with coordinates [a][b]
     * 
     * @param  a        A row number
     * @param  b        A column number
     * @param  item     A sign that goes between squares [a][b] and [a][b+1]
     */
    public void setColumnConstraint(int a, int b, String item)
    {
        if(a<0 || a>(gridSize-2))
        {
            System.out.println("The row number has to be between 0 and " + (gridSize-2));
        }
        else if(b<0 || b>(gridSize-1))
        {
            System.out.println("The column number has to be between 0 and " + (gridSize-1));
        }
        else if(item.equals(" "))
        {
            columnConstraints[a][b] = new EmptyConst(a, b, a+1, b, item, this);
        }
        else if(item.equals("v"))
        {
            columnConstraints[a][b] = new GreaterThan(a, b, a+1, b, item, this);
        }
        else if(item.equals("^"))
        {
            columnConstraints[a][b] = new LessThan(a, b, a+1, b, item, this);
        }
        else
        {
            System.out.println("Sorry, the column constraint must be either \"v\" or \"^\"");
        }
    }
    
    /**
     * Assigns a sign to constraint with coordinates [a][b]
     * 
     * @param  a        A row number
     * @param  b        A column number
     * @param  item     A sign that goes between squares [a][b] and [a][b+1]
     */
    public void setRowConstraint(int a, int b, String item)
    {
        if(a<0 || a>(gridSize-1))
        {
            System.out.println("The row number has to be between 0 and " + (gridSize-1));
        }
        else if(b<0 || b>(gridSize-2))
        {
            System.out.println("The column number has to be between 0 and " + (gridSize-2));
        }
        else if(item.equals(" "))
        {
            rowConstraints[a][b] = new EmptyConst(a, b, a, b+1, item, this);
        }
        else if(item.equals(">"))
        {
            rowConstraints[a][b] = new GreaterThan(a, b, a, b+1, item, this);
        }
        else if(item.equals("<"))
        {
            rowConstraints[a][b] = new LessThan(a, b, a, b+1, item, this);
        }
        else
        {
            System.out.println("Sorry, the row constraint must be either \"<\" or \">\"");
        }
    }
    
    /**
     * Set a value for ShowProblems
     * 
     * @param a     A boolean value for ShowProblems
     */
    public void setShowProblems(boolean a)
    {
        ShowProblems = a;
    }
    
    /**
     * Assigns a value to square at coordinates [a][b].
     * Enter 0 to delete the value of square
     * 
     * @param  a        A row number
     * @param  b        A column number
     * @param  item     An integer to put in the array
     */
    public void setSquare(int a, int b, int item)
    {
        if(a<0 || a>(gridSize-1))
        {
            System.out.println("The row number has to be between 0 and " + (gridSize-1));
        }
        else if(b<0 || b>(gridSize-1))
        {
            System.out.println("The column number has to be between 0 and " + (gridSize-1));
        }
        else if(item >= 0 && item <= gridSize)
        {
            grid[a][b] = new FutoshikiSquare(a, b, item);
            grid[a][b].addActionListener(new NumberListener(grid[a][b], this));
        }
        else
        {
            System.out.println("Sorry, the number must be between 1 and " + gridSize + "\nOr enter 0 to delete the value");
        }
    }
    
    /**
     * Check if the grid is complete, if yes then change background colour of each FutoshikiSquare to green
     */
    public void showComplete()
    {
        if(isComplete())
        {
            for(int x=0;x<gridSize;x++)
            {
                for(int y=0;y<gridSize;y++)
                {
                    grid[x][y].setBackground(Color.GREEN);
                    grid[x][y].setOpaque(true);
                }
            }
        }
    }
    
    /**
     * Recursive method that attemps to solve the current state of FutoshikiPuzzle by finding first empty field, increase its value,
     * and run itself again, if it comes back false, increase value again and so on.
     * 
     * @return      Returns False if current game is already not Legal or unsolvable, returns True when solved.
     */
    public boolean solve()
    {
        // Check if current state is legal, if not, return false
        if(!isLegal())
        {
            return false;
        }
        // Check if current state is complete, if yes, set all buttons green and return true
        if(isComplete())
        {
            showComplete();
            return true;
        }
        // Get first available FutoshikiSquare that is empty (value 0)
        FutoshikiSquare cSquare = findEmpty();
        // Increase value in current square and run solve(). If solve() returns true, return true.
        for(int x=1;x<=gridSize;x++)
        {
            cSquare.setMainValue(x);
            if(solve())
            {
                return true;
            }
        }
        cSquare.setMainValue(0); // reverse value to 0
        return false;
    }
        
    /**
     * Returns true if there are no invalid or repeating numbers in specified column
     * 
     * @param whichColumn       Number of column to be checked
     * @return                  False if there is more than one number of same value in column, True otherwise
     */
    public boolean validColumn(int whichColumn)
    {
        for(int i=0; i<gridSize; i++)
        {
            int count = 0;
            //check if number is wrong size (less than 1 or greater than gridSize)
            if(grid[i][whichColumn].getMainValue()>0)
            {
                if(grid[i][whichColumn].getMainValue()<1 || grid[i][whichColumn].getMainValue()>gridSize)
                {
                    return false;
                }
            }
            //count occurences of current number
            for(int j=0; j<gridSize; j++)
            {
                if(i+1==grid[j][whichColumn].getMainValue())
                {
                    count++;
                }
            }
            if(count > 1)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if there are no violation of constraints in specified column
     * 
     * @return      False if there is any violation of column constraints, true otherwise
     */
    private boolean validColumnConstraints()
    {
        boolean isValid = true;
        for(int i=0; i<gridSize-1; i++)
        {
            for(int j=0; j<gridSize; j++)
            {
                if(columnConstraints[i][j]!=null && columnConstraints[i][j].isFine() == false)
                {
                    isValid = false;
                }
            }
        }
        return isValid;
    }
    
    /**
     * Returns true if there are no invalid or repeating numbers in specified row
     * 
     * @param whichRow      Number of column to be checked
     * @return              False if there is more than one number of same value in row, True otherwise
     */
    public boolean validRow(int whichRow)
    {
        for(int i=0; i<gridSize; i++)
        {
            int count = 0;
            //check if number is wrong size (less than 1 or greater than gridSize)
            if(grid[whichRow][i].getMainValue()<0 || grid[whichRow][i].getMainValue()>gridSize)
            {
                return false;
            }
            //count occurences of current number
            for(int j=0; j<gridSize; j++)
            {
                if(i+1==grid[whichRow][j].getMainValue())
                {
                    count++;
                }
            }
            if(count>1)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if there are no violation of constraints in specified row
     * 
     * @return      False if there is any violation of row constraints, true otherwise
     */
    private boolean validRowConstraints()
    {
        boolean isValid = true;
        for(int i=0; i<gridSize; i++)
        {
            for(int j=0; j<(gridSize-1); j++)
            {
                if(rowConstraints[i][j]!=null && rowConstraints[i][j].isFine() == false)
                {
                    isValid = false;
                }
            }
        }
        return isValid;
    }
    
    /**
     * Check validity of one square by checking if the same value is repeated in any other square in the same row or column
     * 
     * @return      Return false if the value of square is repeated in the same row or column, return true otherwise
     */
    public boolean validSquare(int R, int C)
    {
        int ToCompare = grid[R][C].getMainValue(); // Value of current square
        boolean validColumn = true; 
        boolean validRow = true;
        boolean isValid = true;
        if(ToCompare>0)
        {
            for(int i=0; i<gridSize; i++)
            {
                if(i!=C && grid[R][i].getMainValue()==grid[R][C].getMainValue())
                {
                    validRow=false;
                }
                if(i!=R && grid[i][C].getMainValue()==grid[R][C].getMainValue())
                {
                    validColumn=false;
                }
            }
        }
        if(!validColumn)
        {
            allProblems += "Number in square [" + R +"][" + C + "] has duplicates in Column. \n";
            isValid = false;
        }
        if(!validRow)
        {
            allProblems += "Number in square [" + R +"][" + C + "] has duplicates in Row. \n";
            isValid = false;
        }
        return isValid;
    }
}
