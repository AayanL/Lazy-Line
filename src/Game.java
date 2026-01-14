import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.QuadCurve2D;
import java.io.IOException;
import java.util.*;

public class Game
{
    //ALL GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    private static FishGUI[] fishGUIs = new FishGUI[5];     //FishGUI object array to store fishes on screen
    private static boolean fishCaught[] = new boolean[5];       //fish caught boolean array to track caught fish
    public static int[] fishYCoordinates = {40, 80,120,170,220,270};        //int array for fish coordinates
    public static boolean[] takenYCoordinates = {false, false, false, false, false, false};     //boolean array for taken y coordinates
    public static Timer fishMovementTimer;      //timer for fish movement
    private static int indexCurrentFish;        //index for current fish

    //------------------------------------------------------

    private static int targetX = (int)((Math.random() * (285 - 5)) + 5);        //int for target x coordinate
    private static int negativeTargetSpeed = (int)((Math.random() * 4 ) - 7);       //int negative target speed
    private static int positiveTargetSpeed = (int)((Math.random() * (10 - 7)) + 7);     //int positive target speed
    private static int targetSpeed;     //int target speed
    private static Timer targetMovement;        //timer for target movement

    //------------------------------------------------------

    private static Timer gameTimer;     //timer for game timer
    private static int remainingTime = 31 * 1000;       //int remaining time

    //------------------------------------------------------

    private static Color brown = new Color(172, 120, 51);       //color for dock background color
    private static Random rnd = new Random();       //random object

    //------------------------------------------------------

    private static CardLayout dockCardLayout = new CardLayout();        //card layout for dock
    private static JPanel rightsideDock = new JPanel();     //jpanel for right side of dock panel
    private static JPanel leftsideDock = new JPanel();      //jpanel for left side of dock panel
    private static JLabel lbltimer = new JLabel();      //jlabel for timer

    //------------------------------------------------------

    public static Player currentPlayer;     //player for current player

    //------------------------------------------------------

    private static JPanel sea = new JPanel();       //jpanel for sea panel

    //==================================================================================================================
    //==================================================================================================================



    //METHOD FOR GETTING PANELS
    //==================================================================================================================
    //==================================================================================================================

    //method for starting and getting whole game panel
    public static JPanel GameGUI()
    {
        startFishGUI();     //start initial fish graphics using startFishGUI method

        return entireScreen();      //return whole panel using entireScreen method
    }

    //------------------------------------------------------

    //method for getting sea panel
    private static JPanel sea()
    {
        sea = new JPanel()      //set global sea panel to new jpanel
        {
            protected void paintComponent(Graphics g)       //implement paint component
            {
                super.paintComponent(g);        //update drawing
                setBackground(Color.BLUE);      //set background to blue

                for(int i = 0; i < fishGUIs.length; i++)        //runs for number of fish graphics there are
                {
                    g.setColor(Color.WHITE);        //sets color to white

                    //draws fish using fish graphic at index x and y coordinate and hard setting fish's width and height
                    g.fillOval(fishGUIs[i].getXCoordinate(), fishGUIs[i].getYCoordinate(), 50, 25);
                }

                //runs if there is a current fish selected
                if(indexCurrentFish != -1)
                {
                    Graphics2D g2 = (Graphics2D) g;     //create 2d graphics object for drawing fishing line

                    g2.setColor(Color.white);       //set color to white
                    QuadCurve2D.Float arc = new QuadCurve2D.Float();        //create new quad curve

                    //get x coordinate by using fishing rod x coordinate, and adjusted current fish graphic x coordinate divided by 2
                    int x = (505 + fishGUIs[indexCurrentFish].getXCoordinate() + 20) / 2;

                    //set y coordinate using fishing rod y coordinate and fish graphics width
                    int y = 350 - 50;

                    //set arc curve using fishing rod, calculated, and fish graphic's x and y coordinates
                    arc.setCurve(505, 350, x, y, fishGUIs[indexCurrentFish].getXCoordinate() + 20, fishGUIs[indexCurrentFish].getYCoordinate() + 10);

                    g2.draw(arc);       //draw arc
                }
            }
        };

        //set fish movement timer to new timer with 60 millisecond delay
        fishMovementTimer = new Timer(60, e ->
        {
            updateFishPosition();       //update the fishes graphics by using updateFishPosition method
            sea.repaint();      //repaint sea panel
        });

        sea.setPreferredSize(new Dimension(900,350));       //set sea panel preferred size


        //add mouse listener to sea panel
        sea.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);      //refresh mouse input

                for(int i = 0; i < fishGUIs.length; i++)        //runs for number of fish graphics
                {
                    //runs if fish is in range of click using clickInRange method and the x and y coordinates of the click
                    //and runs if fish graphic has not been caught, using isCaught method
                    if(fishGUIs[i].clickInRange(e.getX(), e.getY()) && !fishGUIs[i].isCaught())
                    {
                        indexCurrentFish = i;       //set the current fish index to i

                        sea.repaint();

                        dockCardLayout.show(rightsideDock, "catch");        //show bar panel to catch fish
                        rightsideDock.repaint();      //repaint rightsideDock panel

                        targetX = (int)((Math.random() * (285 - 5)) + 5);       //sets random x coordinate for the moving target
                        targetMovement.start();     //starts target movement timer
                    }
                }
            }
        });

        return sea;     //returns sea panel
    }

    //------------------------------------------------------

    //method for getting left side of dock panel
    private static JPanel leftsideDock()
    {
        leftsideDock = new JPanel()     //set leftside of dock equal to new jpanel
        {
            protected void paintComponent(Graphics g)       //implement paint component
            {
                super.paintComponent(g);        //refresh drawing

                g.setColor(Color.WHITE);        //set color to white
                g.fillOval(425, 15, 80, 80);        //draw oval, the player on the dock

                g.setColor(Color.RED);      //set color to red
                g.fillRect(505, 0, 5,90);       //draw rectangle/line, the fishing rod
            }
        };


        //set game timer equal to new timer with 1000 millisecond delay
        gameTimer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateTimer();      //update the game timer using updateTimer method
            }
        });

        leftsideDock.setBackground(brown);      //set leftside of dock background to global color variable
        leftsideDock.setLayout(null);       //set leftside of dock layout to null

        leftsideDock.add(lbltimer);     //add timer label to leftside of dock

        leftsideDock.setPreferredSize(new Dimension(600, 150));     //set preferred size

        return leftsideDock;        //return leftside of dock panel
    }

    //------------------------------------------------------

    //method for getting right side of dock panel
    private static JPanel rightsideDock()
    {
        rightsideDock = new JPanel(new CardLayout());     //set right side dock equal to new jpanel with card layout
        rightsideDock.setPreferredSize(new Dimension(300, 50));       //set preferred size

        dockCardLayout = (CardLayout)(rightsideDock.getLayout());     //set card layout
        rightsideDock.add(subCatchBar(), "sub");       //add sub catch bar to panel
        rightsideDock.add(catchBar(), "catch");        //add catch bar to panel

        return rightsideDock;      //return right side dock
    }

    //------------------------------------------------------

    //method for getting the sub catch bar panel
    private static JPanel subCatchBar()
    {
        JPanel subCatchBar = new JPanel(new CardLayout());      //create sub catch bar jpanel
        subCatchBar.setPreferredSize(new Dimension(300, 150));      //set preferred size
        subCatchBar.setBackground(brown);       //set background to global color variable

        return subCatchBar;     //return sub catch bar
    }

    //------------------------------------------------------

    //method for getting catch bar panel
    public static JPanel catchBar()
    {
        JPanel catchPanel = new JPanel()      //create catch panel jpanel
        {
            protected void paintComponent(Graphics g)       //implement paint component
            {
                super.paintComponent(g);        //refresh drawings

                g.setColor(Color.white);    //set color to white
                g.fillRect(5,45,285,20);        //draw rectangle to contain green and red bar

                g.setColor(Color.green);        //set color to green
                g.fillRect(115,45,75,20);       //draw green rectangle, the catch area

                g.setColor(Color.red);      //set color to red
                g.fillRect(targetX, 45, 50,20);     //draw red rectangle, the moving target
            }
        };

        catchPanel.setPreferredSize(new Dimension(300,150));      //set catch panel preferred size
        catchPanel.setLayout(null);     //set catch panel layout to null

        catchPanel.setFocusable(true);        //set catch panel panel to focusable
        catchPanel.requestFocusInWindow();        //request catch panel focus in window

        JButton btnStop = new JButton("Stop");      //create stop jbutton
        btnStop.setBounds(120,70,60,25);        //set position and size

        catchPanel.add(btnStop);        //add stop button to catch panel

        catchPanel.setBackground(brown);        //set background global color variable
        catchPanel.setPreferredSize(new Dimension(300,50));

        int randomDirection = rnd.nextInt(2);       //get random number from 0 to 1

        if (randomDirection == 0)       //runs if number was 0
        {
            targetSpeed = positiveTargetSpeed;        //target speed is set with positiveTargetSpeed method
        }
        else        //runs if number was 1
        {
            targetSpeed = negativeTargetSpeed;       //target speed is set with negativeTargetSpeed method
        }

        //set target movement to new timer with delay of 60 milliseconds
        targetMovement = new Timer(60, e ->
        {
            //runs if the target has hit the right side of the rectangle it is in
            if ((targetX + 50) >= 290)
            {
                setNegativeSpeed();     //set negative speed, moves left
            }

            //runs if the target has hit the left side of the rectangle it is in
            else if (targetX <= 0)
            {
                setPositiveSpeed();     //set positive speed, moves right
            }

            targetX += targetSpeed;      //increase target position speed

            catchPanel.repaint();       //repaint the catch panel
        });

        //action listener for stop button
        btnStop.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                targetMovement.stop();      //stop target movement

                //runs if red target stopped in bounds of the green catch bar
                if((targetX >= 115) && (targetX <= 190))
                {
                    fishGUIs[indexCurrentFish].fishCaught();        //set the current fish graphic to catch
                    currentPlayer.addFish(Fish.getRandomFish());        //add a random fish to the current player
                    currentPlayer.updatePoints();       //update the current player's score

                    dockCardLayout.show(rightsideDock, "sub");      //show the sub catch panel, hiding the catch panel
                    toggleTimer();      //toggle timer
                }

                //runs if red target stopped outside the bounds of the green catch bar
                else
                {
                    indexCurrentFish = -1;      //sets current fish index to -1
                    dockCardLayout.show(rightsideDock, "sub");      //shows the sub catch panel, hiding the catch panel
                }
            }
        });

        return catchPanel;      //return the catch panel
    }

    //------------------------------------------------------

    //method for getting whole dock panel
    private static JPanel wholeDock()
    {
        JPanel wholeDock = new JPanel();        //create whole dock jpanel
        wholeDock.setPreferredSize(new Dimension(900, 50));     //set preferred size
        wholeDock.setLayout(new BoxLayout(wholeDock, BoxLayout.X_AXIS));        //set box layout for whole dock

        wholeDock.add(leftsideDock());      //add left side dock panel to whole dock
        wholeDock.add(rightsideDock());     //add right side dock panel to whole dock

        return wholeDock;       //return whole dock panel
    }

    //------------------------------------------------------

    public static JPanel entireScreen()
    {
        JPanel entireScreen = new JPanel();     //create entire screen jpanel
        entireScreen.setPreferredSize(new Dimension(900, 500));     //set preferred size
        entireScreen.setLayout(new BoxLayout(entireScreen, BoxLayout.Y_AXIS));      //set box layout for entire sceen

        entireScreen.add(sea());        //add sea panel to entire screen
        entireScreen.add(wholeDock());      //add whole dock panel to entire screen

        return entireScreen;        //return entire screen panel
    }

    //==================================================================================================================
    //==================================================================================================================


    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    private static void startFishGUI()
    {
        for(int i = 0; i < 5; i++)      //runs 5 times to create 5 fish
        {
            int intIndex = rnd.nextInt(6);     //generate random number from 0 to 5
            int intYCoordinate = 0;      //declare and set intYCoordinate coordinate value to 0

            //runs if the coordinate at the random index is already taken
            if(takenYCoordinates[intIndex])
            {
                while(true)     //runs until break condition
                {
                    for(int j = 0; j < takenYCoordinates.length; j++)       //runs for number of y coordinates
                    {
                        if(!takenYCoordinates[j])       //runs if y coordinate at index j is not taken
                        {
                            intYCoordinate = fishYCoordinates[j];        //sets y coordinate to fish coordinates at index j
                            takenYCoordinates[j] = true;        //sets y coordinate as taken
                            j = j + (takenYCoordinates.length - j);     //breaks from loop
                        }
                    }

                    break;      //breaks after setting y coordinate
                }
            }

            else    //runs if y coordinate at random index was not taken
            {
                intYCoordinate = fishYCoordinates[intIndex];     //sets y coordinate to fish coordinate at random index
                takenYCoordinates[intIndex] = true;     //sets y coordinate as taken
            }

            int intXCoordinate = rnd.nextInt(901);      //generates a random x coordinate from 0 to 900
            FishGUI fish = new FishGUI(intXCoordinate, intYCoordinate);     //creates fish gui with x and y coordinates
            fishGUIs[i] = fish;       //adds fish gui to fish gui array
        }
    }

    //------------------------------------------------------

    //method for updating fish graphics postion
    private static void updateFishPosition()
    {
        for (int i = 0; i < fishGUIs.length; i++)       //runs for number of fish graphics
        {
            fishGUIs[i].updatePosition();       //updates fish graphics position with updatePosition method in FishGUI class
        }

        //runs if there is no current fish selected, or the current fish has went off screen
        if(indexCurrentFish == -1 || fishGUIs[indexCurrentFish].getXCoordinate() >= 900 || fishGUIs[indexCurrentFish].getXCoordinate() <= 0)
        {
            indexCurrentFish = -1;      //reset current fish to none
            dockCardLayout.show(rightsideDock, "sub");      //show sub catch panel
        }
    }

    //------------------------------------------------------

    //method for adding timer label
    private static void setLbltimer()
    {
        lbltimer.setVisible(true);      //set timer label visibility to true
        lbltimer.setFont(new Font("Arial", Font.BOLD, 30));     //set font
        lbltimer.setForeground(Color.white);        //set color
        lbltimer.setBounds(50,30,100,50);       //set position and size
        lbltimer.setPreferredSize(new Dimension(100,150));      //set preferred size
        lbltimer.setVerticalAlignment(SwingConstants.CENTER);       //set vertical alignment to center
    }

    //------------------------------------------------------

    //method for setting positive speed
    private static void setPositiveSpeed()
    {
        targetSpeed = (int)((Math.random() + (10 - 5)) + 5);    //set target speed to random number from 10 to 5
    }

    //------------------------------------------------------

    //method for setting negative speed
    private static void setNegativeSpeed()
    {
        targetSpeed = (int)((Math.random() * 6) - 10);      //set target speed to random number from -10 to -5
    }

    //------------------------------------------------------

    //method for setting the current player
    private static void setCurrentPlayer()
    {
        currentPlayer = AllPages.getCurrentPlayer();    //set current player to AllPages class method, getCurrentPlayer
    }

    //------------------------------------------------------

    //method for returning to homepage
    private static void returnToHomepage()
    {
        //show homepage panel
        AllPages.AllPanelsCardLayout.show(AllPages.AllPanels, "homepage");
    }

    //------------------------------------------------------

    //method for starting up game variables
    public static void gameStart()
    {
        setCurrentPlayer();     //set current player
        indexCurrentFish = -1;      //set current fish to -1, none
        remainingTime = 31 * 1000;          //set remaining time to 30 seconds
        gameTimer.start();      //start game timer
        fishMovementTimer.start();      //start fish movement timer
        setAllFishes(false);        //set all fishes to not taken using setAllFishes method
    }

    //------------------------------------------------------

    //method for stopping game
    public static void gameStop()
    {
        gameTimer.stop();       //stop game timer
        fishMovementTimer.stop();       //stop fish movement timer
        setAllFishes(true);     //set all fish as taken using setAllFishes method
        lbltimer.setVisible(false);     //set timer label as not visible
        dockCardLayout.show(rightsideDock, "sub");      //show sub catch panel

        JPanel panel = new JPanel();        //create jpanel
        JButton btnReturn = new JButton("Return to Homepage");      //create return jbutton
        panel.add(btnReturn);       //adds return button to panel

        btnReturn.addActionListener(new ActionListener()        //create action listener for return  jbutton
        {
            public void actionPerformed(ActionEvent e)
            {
                ((JDialog) SwingUtilities.getWindowAncestor(btnReturn)).dispose();      //closes dialog after clicked
                returnToHomepage();     //return to homepage
            }
        });

        //show joptionpane dialog box
        JOptionPane.showOptionDialog(null,panel, "Time's up!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{},null);
    }

    //------------------------------------------------------

    //method for toggling timer
    private static void toggleTimer()
    {
        gameTimer.stop();       //stop game timer
        fishMovementTimer.stop();       //stop fish movement timer
        remainingTime = getRemainingTime();     //set remaining time to remaining time using getRemainingTime method
        indexCurrentFish = -1;      //set current fish to -1, none

        JPanel panel = new JPanel();        //create jpanel
        JButton btnBackToGame = new JButton("Ok");      //create back to game jbutton
        JLabel lblFishCaught = new JLabel("Fish caught!");       //create caught fish jlabel

        //add action listener for back to game button
        btnBackToGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ((JDialog) SwingUtilities.getWindowAncestor(btnBackToGame)).dispose();      //closes dialog after clicked
                gameTimer.start();      //start game timer
                fishMovementTimer.start();      //start fish movement timer
                remainingTime = getRemainingTime();     //set remaining time to remaining time using getRemainingTime method
            }
        });

        panel.add(lblFishCaught);        //add fish caught label to panel
        panel.add(btnBackToGame);       //add back to game button to panel

        //show joptionpane dialog box
        JOptionPane.showOptionDialog(null,panel, "Fish Caught!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{},null);
    }

    //------------------------------------------------------

    //method for updating target timer
    private static void updateTimer()
    {
        remainingTime -= 1000;      //subtract 1 second from the time

        //find minutes and seconds remaining
        int minutes = (remainingTime / 1000) / 60;
        int seconds = (remainingTime / 1000) % 60;

        //set minutes and seconds to formatted string
        String strMinutes = String.format("%02d", minutes);
        String strSeconds = String.format("%02d", seconds);

        lbltimer.setText(strMinutes + ": " + strSeconds);       //set timer label text
        setLbltimer();      //update timer label

        leftsideDock.add(lbltimer);     //add timer label to left side of dock panel


        if(remainingTime <= 0)      //runs if time is over
        {
            gameStop();     //stop game timer
            try
            {
                //add current player to file using addScore method in FileManager class
                FileManager.addScore(currentPlayer);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    //------------------------------------------------------

    //method for setting all fish as taken or free
    private static void setAllFishes(boolean b)
    {
        for(int i = 0; i < fishGUIs.length; i++)        //runs through all fish graphics
        {
            fishGUIs[i].setFishState(b);        //set fish state using setFishState method in FishGUI class
        }
    }

    //------------------------------------------------------

    //method for getting remaining time
    private static int getRemainingTime()
    {
        return remainingTime;       //return remaining time
    }

    //==================================================================================================================
    //==================================================================================================================
}

