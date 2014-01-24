------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: Futoshiki Puzlle - Part 3
PURPOSE OF PROJECT: Further Programming assessment
VERSION or DATE: 1.3
AUTHOR: Radek Lochman

USER INSTRUCTIONS:

Starting a game:    There are 3 ways to start a game. 
                    1.) Run futoshiki.jar (tested on Windows 7 64x, Mac 10.7.5)
                    2.) Create new instance of FutoshikiGUI
                    3.) Run Main method of FutoshikiGUI
                    
                    
Playing a game:     Game starts with pre-defined setting (3 values and 2 constraints).
                    User can add, change or remove any constraints (apart from initial values with grey background) by clicking on constraint squares .
                    User can change any value of any square (apart from initial values with grey background) by clicking on number squares.
                    
                    There is a menu bar on right side of window, containing 7 controll buttons and explanation of colours. 
                    "New Random Game": Upon pressing the button, dialog window with text-field will pop up.
                    Enter the number of presets (numbers and/or constraints) for the new game and press ok. If you enter more than 10,
                    the warning dialog will pop up to tell you that your computer might become unresponsive and ask if to continue or not.
                    "Restart Current Game": If pressed it will return the game to its' initial layout (if you press ok on confirm dialog).
                    "Check Legality": Check if the current state of the puzzle is legal.
                    "Show Problems": Toggle the function that changes colours of squares when they violate legality of grid
                    "Save Game": Save values of each square and constraint to the text file on computer (gameSave.txt in same folder as the program)
                    "Load Game": Load values of each square and constraint from the text file on computer (gameSave.txt in same folder as the program)
                    "Solve Current Game": If pressed, program will try to find solution to current game. If there is no solution it will pop up appropriate
                    message, otherwise it will show the correct values.
                    
                    Below the control buttons there is explanation for what each color of puzzle means.
                                        
How the code works: Displaying the data: Each FutoshikiSquare and Constraint are extentions of JButton object; therefore the panel on main window just load
                    all the objects from arrays in FutoshikiPuzzle on start. When value of FutoshikiSquare is changed, it set Text of button to show a string
                    representation of value. When contstraint is changed, new object is created depending on value for the constraint. Therefore the old
                    in array is replaced with new one, all buttons get removed from panel loaded from array again. 
                    
                    Editing the data: Each FutoshikiSquare is paired with NumberListener which when user click on it, it check if the button is not editable,
                    it will leave the value it has already, if it is editable it will increase its value until it reach treshold (grid size of the game) and
                    then resets the value to 0. After changing the value it check the grid for errors and if the grid is complete. Each Constraint is paired
                    with Listener which check if the button is editable. If yes, it check what the value is and create new one with different value (it circle
                    through greater then, smaler then and no constraint). After changing Constraint it reinitialize the panel and check for errors.
                    
                    Checking legality and reporting problems: There are two ways to check legality of the puzzle. First is button "Check Legality" which will
                    run function isLegal() from FutoshikiPuzzle. If it return true, message will show that everything is fine, otherwise it will say that there
                    is something wrong in current puzzle, but not show what. The second is that user has a chance to toggle on or off the constant checking for
                    errors. If user click on button "Show Problems" with each change of number of constraint each button gets checked and validity of constraint. 
                    If any of them is violated it changes the background of the button to red. If the button "Hide Problems" is pressed, while changing the values
                    the legality of grid is not being checked. Upon completion of puzzle all buttons become green to let user know.
                    
                    Optional extras: User has an an option to create new random game with any number of random numbers/constraints. When game is created it
                    is backed up so user has an option to restart current game to its initial values. There is also an option to save/load game. And of 
                    course the solving of the game.
                    
Known problems:     Colour of constraints buttons are not changing on MAC computers.
                    EDIT: Background colour of constraints buttons is not changing, instead just a Foreground colour is changing (font colour) and it works fine
                    on MAC. (tested on version 10.7.5)