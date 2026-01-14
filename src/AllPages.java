import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AllPages
{
    //ALL GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    public static JPanel AllPanels = new JPanel(new CardLayout());      //global jpanel for storing all program panel
    public static CardLayout AllPanelsCardLayout = (CardLayout)(AllPanels.getLayout());     //global cardlayout for all panels panel

    //------------------------------------------------------

    private static Image image = new ImageIcon("Images/homepageBackdrop.jpg").getImage();       //global image for homepage background
    private static Color backroundColor = new Color(198, 235, 246);     //global color for background color

    //------------------------------------------------------

    private static Player currentPlayer;     //global player for current player

    //------------------------------------------------------

    private static JScrollPane fishInventoryScrollPane;     //global scroll pane for fish inventory
    private static JPanel scoresPanel;      //global jpanel for scores panel in scoreboard
    private static int selectedPlayer;      //global int for selected player index
    private static JScrollPane scoreScrollPane;     //global scroll pane for scores in scoreboard
    private static JList jListPlayerScores;     //global jlist for scores in scoreboard

    //------------------------------------------------------

    private static JTextField txtInputUsername;     //global textfield for username input
    private static String strInputUsername;     //global string for username input

    //==================================================================================================================
    //==================================================================================================================



    //method for setting up program
    public static void runGUI()
    {
        AllPanels.add(Homepage(), "homepage");     //add homepage panel to AllPanels
        AllPanels.add(HowToPlay(), "htp");     //add how to play panel to AllPanels
        AllPanels.add(Scoreboard(), "scoreboard");     //add scoreboard panel to AllPanels
        AllPanels.add(Game.GameGUI(), "game");     //add game panel to AllPanels
        AllPanels.add(newPlayer(), "newplayer");       //add new player panel to AllPanels

        JFrame frame = new JFrame();        //create frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //set frame closing operation

        Container container = frame.getContentPane();       //create container
        container.add(AllPanels);      //add AllPanels to container

        frame.pack();
        frame.setSize(900,500);     //set frame dimensions
        frame.setResizable(false);      //make frame not resizeable
        frame.setVisible(true);     //make frame visble
    }



    //METHODS FOR GETTING PANELS
    //==================================================================================================================
    //==================================================================================================================

    //method for getting homepage panel
    private static JPanel Homepage()
    {
        JPanel homepage = new JPanel()     //create homepage jpanel
        {
            protected void paintComponent(Graphics g)       //implement paint component
            {
                super.paintComponent(g);        //updates drawing

                g.drawImage(image,0,0,getWidth(),getHeight(),this);     //adds background image to homepage
            }
        };

        homepage.setLayout(new BoxLayout(homepage, BoxLayout.Y_AXIS));        //set box layout for homepage panel
        homepage.setAlignmentX(Component.CENTER_ALIGNMENT);        //set x alignment to center

        ButtonHandler onClick = new ButtonHandler();        //create button handler object

        //ALL BUTTONS IN THIS SECTION HAVE SAME CODE====================================================================
        JButton btnPlay = new JButton("Play");      //create play button
        btnPlay.setBackground(Color.white);     //set background color to white
        btnPlay.setFont(new Font("Arial", Font.BOLD, 16));      //set font
        btnPlay.setFocusPainted(false);         //removes border inside button
        btnPlay.setActionCommand("HOMEplay");       //adds action command to button
        btnPlay.addActionListener(onClick);     //adds button handler
        btnPlay.setMaximumSize(new Dimension(300,100));     //sets maximum size

        JButton btnHowToPlay = new JButton("How to Play");
        btnHowToPlay.setBackground(Color.white);
        btnHowToPlay.setFont(new Font("Arial", Font.BOLD, 16));
        btnHowToPlay.setFocusPainted(false);
        btnHowToPlay.setActionCommand("HOMEhtp");
        btnHowToPlay.addActionListener(onClick);
        btnHowToPlay.setMaximumSize(new Dimension(300,100));

        JButton btnScoreboard = new JButton("Scoreboard");
        btnScoreboard.setBackground(Color.white);
        btnScoreboard.setFont(new Font("Arial", Font.BOLD, 16));
        btnScoreboard.setFocusPainted(false);
        btnScoreboard.setActionCommand("HOMEscoreboard");
        btnScoreboard.addActionListener(onClick);
        btnScoreboard.setMaximumSize(new Dimension(300,100));

        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(Color.white);
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setFocusPainted(false);
        btnExit.setActionCommand("HOMEexit");
        btnExit.addActionListener(onClick);
        btnExit.setMaximumSize(new Dimension(300,100));

        //==============================================================================================================

        //set x alignment of all button to the center
        btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnScoreboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add vertical spacing and buttons to homepage panel
        homepage.add(Box.createRigidArea(new Dimension(0,50)));
        homepage.add(btnPlay);
        homepage.add(Box.createRigidArea(new Dimension(0,20)));
        homepage.add(btnHowToPlay);
        homepage.add(Box.createRigidArea(new Dimension(0,20)));
        homepage.add(btnScoreboard);
        homepage.add(Box.createRigidArea(new Dimension(0,20)));
        homepage.add(btnExit);
        homepage.add(Box.createRigidArea(new Dimension(0,50)));

        return homepage;       //returns homepage panel
    }

    //------------------------------------------------------


    //method for getting how to play panel
    private static JPanel HowToPlay()
    {
        JPanel HTPpanel = new JPanel();        //create how to play jpanel
        HTPpanel.setLayout(new BoxLayout(HTPpanel, BoxLayout.Y_AXIS));        //set box layout for how to play panel
        HTPpanel.setAlignmentX(Component.CENTER_ALIGNMENT);        //set x alignment of how to play panel to center
        HTPpanel.setBackground(backroundColor);        //set background color to global color variable

        JLabel lblHowToPlay = new JLabel("How to Play");        //create jlabel for panel title
        lblHowToPlay.setFont(new Font("Arial", Font.BOLD, 26));     //set font
        lblHowToPlay.setPreferredSize(new Dimension(300,30));       //set dimensions
        lblHowToPlay.setOpaque(true);       //set to opaque
        lblHowToPlay.setBackground(backroundColor);     //set background color to global color variable

        JLabel lblInstructions = new JLabel();      //create jlabel for instructions
        lblInstructions.setFont(new Font("Arial", Font.BOLD, 14));      //set font
        lblInstructions.setVerticalAlignment(SwingConstants.CENTER);        //set vertical alignment to center
        lblInstructions.setHorizontalAlignment(SwingConstants.LEFT);        //set horizontal alignment to left
        lblInstructions.setOpaque(true);        //set to opaque
        lblInstructions.setBackground(Color.white);     //set background color to white

        //create string for instructions text
        String strInstructions = "<html>1. Press the \"Play\" button<br><br>2. Enter a username and press the \"Enter\" button<br><br>" +
                                 "3. The game will start and the player will have 30 seconds to catch as many fish as possible<br><br>" +
                                 "4. To catch fish, click on a fish and the catch bar will appear on bottom right of the screen<br><br>" +
                                 "5. Try to click the \"Stop\" button when the moving red bar is over the green catch bar<br><br>" +
                                 "6. If the red bar is stopped within the green bar, the fish is caught and a pop up will inform the player<br><br>" +
                                 "Additional Information<br>- Once a fish is caught, it cannot be caught again<br>" +
                                 "- Players can check fish inventories in the scoreboard by selecting a player, then clicking \"Inventory\"<br>" +
                                 "- Scores are calculated by looking at each fish caught, and using the rarity {Common,Uncommon,Rare,Legendary}   and weight to determine score</html>";

        lblInstructions.setText(strInstructions);       //set instructions label text

        lblInstructions.setMaximumSize(new Dimension(800,300));     //set maximum size

        lblHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);     //set how to play label x alignment to center
        lblInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);      //set instructions label alignment to center

        ButtonHandler onClick = new ButtonHandler();        //create button handler object

        JButton btnBack = new JButton("Back");      //create jbutton for going back to homepage
        btnBack.setActionCommand("HTPback");        //set action command
        btnBack.addActionListener(onClick);     //add button handler
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);      //set button x alignment to center

        HTPpanel.add(Box.createRigidArea(new Dimension(0,50)));        //add vertical spacing to how to play panel
        HTPpanel.add(lblHowToPlay);        //add how to play label to how to play panel
        HTPpanel.add(Box.createRigidArea(new Dimension(0,20)));        //add vertical spacing to how to play panel
        HTPpanel.add(lblInstructions);     //add instructions label to how to play panel
        HTPpanel.add(Box.createRigidArea(new Dimension(0,5)));     //add vertical spacing to how to play panel
        HTPpanel.add(btnBack);     //add back button to how to play panel

        return HTPpanel;       //return how to play panel
    }

    //------------------------------------------------------

    //method for getting scoreboard panel
    private static JPanel Scoreboard()
    {
        JPanel topPanel = new JPanel();     //create top jpanel
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));      //set box layout for top scoreboard
        topPanel.setPreferredSize(new Dimension(900, 100));     //set preferred size
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);     //set x alignment to center
        topPanel.setBackground(backroundColor);     //set background color to global color variable

        JLabel lblTitle = new JLabel("Scoreboard");        //create jlabel for title
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));        //set font
        lblTitle.setOpaque(true);      //set opaque
        lblTitle.setBackground(backroundColor);        //set background color to global color variable
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);        //set x alignment to center

        topPanel.add((Box.createRigidArea(new Dimension(0,50))));       //add vertical spacing to top scoreboard
        topPanel.add(lblTitle);        //add title to top scoreboard
        topPanel.add(Box.createRigidArea(new Dimension(0,20)));     //add vertical spacing to top scoreboard

        scoresPanel = new JPanel();     //set global scoreboard scores scoreboard equal to new jpanel
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.X_AXIS));        //set box layout to scores scoreboard
        scoresPanel.setPreferredSize(new Dimension(900,300));       //set preferred size
        scoresPanel.setBackground(backroundColor);      //set background color to global color variable

        scoreScrollPane = new JScrollPane();        //set global score scroll pane to new jscroll pane
        scoreScrollPane.setMaximumSize(new Dimension(330,300));     //set maximum size

        updateScores();     //run method to add current scores to scores scroll pane

        fishInventoryScrollPane = new JScrollPane();        //set global fish inventory scroll pane to new jscroll pane
        fishInventoryScrollPane.setMaximumSize(new Dimension(300,300));     //set maximum size
        fishInventoryScrollPane.setVisible(false);      //set visibility to  false

        scoresPanel.add(Box.createRigidArea(new Dimension(280,0)));     //add horizontal spacing to scores scoreboard
        scoresPanel.add(scoreScrollPane);       //add scores scroll pane to scores scoreboard
        scoresPanel.add(Box.createRigidArea(new Dimension(20,0)));      //add horizontal spacing to scores scoreboard
        scoresPanel.add(fishInventoryScrollPane);       //add fish inventory score pane to scores scoreboard


        JPanel bottomPanel = new JPanel();      //create bottom jpanel
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));        //set box layout for bottom scoreboard
        bottomPanel.setBackground(backroundColor);      //set background color to global color variable

        ButtonHandler onClick = new ButtonHandler();        //create button handler object

        JButton btnBack = new JButton("Back");      //create back button
        btnBack.setActionCommand("SCRback");        //add action command
        btnBack.addActionListener(onClick);     //add button handler

        JButton btnInventory = new JButton("Inventory");        //create inventory button
        btnInventory.setActionCommand("SCRinventory");      //add action command
        btnInventory.addActionListener(onClick);        //add button handler

        bottomPanel.add(btnBack);       //add back button to bottom scoreboard
        bottomPanel.add(Box.createRigidArea(new Dimension(5,0)));       //add horizontal spacing to bottom scoreboard
        bottomPanel.add(btnInventory);      //add inventory button to button scoreboard

        JPanel scoreboard = new JPanel();        //create scoreboard panel to place top, scores, and bottom panels
        scoreboard.setBackground(backroundColor);        //set background color to global color variable

        scoreboard.add(topPanel);        //add top scoreboard to scoreboard
        scoreboard.add(scoresPanel);     //add scores scoreboard to scoreboard
        scoreboard.add(bottomPanel);     //add bottom scoreboard to scoreboard

        return scoreboard;       //return scoreboard panel
    }

    //------------------------------------------------------

    //method for getting new player panel
    private static JPanel newPlayer()
    {
        JPanel topPanel = new JPanel();     //create top jpanel
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));      //set box layout for top allPanels
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);     //set x alignment to center
        topPanel.setBackground(backroundColor);     //set background color to global color variable

        JLabel lblEnterName = new JLabel("Enter username");     //create enter name jlabel
        lblEnterName.setFont(new Font("Arial", Font.BOLD, 26));     //set font
        lblEnterName.setOpaque(true);       //set opaque
        lblEnterName.setBackground(backroundColor);     //set background color to global color variable
        lblEnterName.setAlignmentX(Component.CENTER_ALIGNMENT);     //set x alignment to center

        txtInputUsername = new JTextField();        //set global username input text field to new text field
        txtInputUsername.setMaximumSize(new Dimension(400,50));     //set maximum size
        txtInputUsername.setFont(new Font("Arial", Font.BOLD, 20));     //set font

        //add key listener for keyboard input
        txtInputUsername.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e)
            {
                //runs if text in username input textfield is longer than 11 characters
                //in place to not input of extra long usernames
                if(txtInputUsername.getText().length() > 11)
                {
                    e.consume();        //stops taking input
                }
            }

            public void keyPressed(KeyEvent e) {

            }
            public void keyReleased(KeyEvent e) {

            }

        });

        topPanel.add(Box.createRigidArea(new Dimension(0,50)));     //add vertical spacing to top allPanels
        topPanel.add(lblEnterName);     //add enter name label to top allPanels
        topPanel.add(Box.createRigidArea(new Dimension(0,20)));     //add vertical spacing to top allPanels
        topPanel.add(txtInputUsername);     //add username input textfield to top allPanels
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));        //add vertical spacing to top allPanels


        JPanel bottomPanel = new JPanel();      //create bottom jpanel
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));        //set box layout for bottom allPanels
        bottomPanel.setBackground(backroundColor);      //set background color to global color variable


        ButtonHandler onClick = new ButtonHandler();        //create button handler object

        JButton btnEnter = new JButton("Enter");        //create enter jbutton
        btnEnter.setActionCommand("Nenter");        //set action command
        btnEnter.addActionListener(onClick);        //add button handler
        btnEnter.setAlignmentX(Component.CENTER_ALIGNMENT);     //set x alignment to center

        JButton btnBack = new JButton("Back");      //create back button
        btnBack.setActionCommand("Nback");      //set action command
        btnBack.addActionListener(onClick);     //add button handler
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);      //set x alignment to center

        bottomPanel.add(btnBack);       //add back button to bottom allPanels
        bottomPanel.add(Box.createRigidArea(new Dimension(5, 0)));      //add horizontal spacing to bottom allPanels
        bottomPanel.add(btnEnter);      //add enter button to bottom allPanels


        JPanel newPlayer = new JPanel();        //create new player panels jpanel to place top and bottom panels
        newPlayer.setLayout(new BoxLayout(newPlayer, BoxLayout.Y_AXIS));        //set boxlayout for new player panel
        newPlayer.setBackground(backroundColor);        //set background color to global color variable

        newPlayer.add(topPanel);        //add top panel to new player panel
        newPlayer.add(bottomPanel);     //add bottom panel to new player panel
        newPlayer.add(Box.createRigidArea(new Dimension(0,10)));    //add vertical spacing to new player panel

        return newPlayer;       //return new player panel
    }

    //==================================================================================================================
    //==================================================================================================================



    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    //method for viewing fish inventory on scoreboard panel
    //takes player's index when players are sorted by score
    private static void viewInventory(int index)
    {
        //create fish inventory array list of player's fishes using getInventory method for FileManger class
        ArrayList fishInventory = FileManager.getInventory(index);

        DefaultListModel getAllFish = new DefaultListModel();      //create get all fish default list model

        for(int i = 0; i < (fishInventory.size()); i++)        //runs for number of fish in inventory
        {
            getAllFish.addElement(fishInventory.get(i));       //adds all fishes from fish inventory array list, to get all fish default list
        }

        JList jListFishes = new JList(getAllFish);        //create fish jlist with get all fish default list
        jListFishes.setFont(new Font("Consolas", Font.BOLD, 14));      //set font of jlist

        JScrollPane newScrollPane = new JScrollPane(jListFishes);        //create new scroll pane with fish list jlist
        newScrollPane.setMaximumSize(new Dimension(250,300));       //set dimensions of new scroll pane

        fishInventoryScrollPane.setVisible(true);       //set global fish inventory scroll pane visibility to true
        fishInventoryScrollPane.setViewportView(newScrollPane);     //set new scroll pane visible in fish inventory scroll pane
        fishInventoryScrollPane.setMaximumSize(new Dimension(250,300));     //sets maximum size
        fishInventoryScrollPane.revalidate();       //refresh fish inventory scroll pane
        fishInventoryScrollPane.repaint();      //update fish inventory scroll pane
    }

    //------------------------------------------------------

    //method for displaying scores onto scoreboard panel
    private static void updateScores()
    {
        //create scores array list using getScoreboard method from FileManger class
        ArrayList scores = FileManager.getScoreboard();

        DefaultListModel getAllScores = new DefaultListModel();     //create get all scores list model

        //runs if there is more than 0 scores saved
        if(scores.size() != 0)
        {
            for(int i = 0; i < scores.size(); i++)      //runs for number of scores saved
            {
                getAllScores.addElement(scores.get(i));     //adds score from scores array list, to get all scores default list
            }

            jListPlayerScores = new JList(getAllScores);        //create scores jlist with get all scores default list
            jListPlayerScores.setFont(new Font("Consolas", Font.BOLD, 25));     //set font

            JScrollPane newScrollPane = new JScrollPane(jListPlayerScores);       //create new scroll pane with scores jlist
            newScrollPane.setMaximumSize(new Dimension(330,300));     //set maximum size

            scoreScrollPane.setViewportView(newScrollPane);       //set new scroll pane visible in scores scroll pane
            scoreScrollPane.revalidate();       //refresh scores scroll pane
            scoreScrollPane.repaint();      //update scores scroll pane
        }

    }

    //------------------------------------------------------

    //method for getting the current player
    public static Player getCurrentPlayer()
    {
        return currentPlayer;       //return current player variable
    }

    //==================================================================================================================
    //==================================================================================================================


    //BUTTON HANDLER
    //==================================================================================================================
    //==================================================================================================================

    //create button handler class
    public static class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String c = e.getActionCommand();        //store action command

            //runs if action command contains "HOME", any components in the homepage panel
            if(c.contains("HOME"))
            {
                //runs if play button was clicked
                if(c.equals("HOMEplay"))
                {
                    //shows new player panel
                    AllPanelsCardLayout.show(AllPanels, "newplayer");

                    txtInputUsername.setText("");       //set username input text to nothing
                }

                //runs if how to play button was clicked
                else if(c.equals("HOMEhtp"))
                {
                    //shows how to play panel
                    AllPanelsCardLayout.show(AllPanels, "htp");
                }

                //runs if scoreboard button was clicked
                else if(c.equals("HOMEscoreboard"))
                {
                    updateScores();     //update scores on scores scroll pane
                    scoresPanel.revalidate();       //refresh scores panel
                    scoresPanel.repaint();      //repain scores panel

                    //shows scoreboard panel
                    AllPanelsCardLayout.show(AllPanels, "scoreboard");
                }

                //runs if exit button was clicked
                else if(c.equals("HOMEexit"))
                {
                    System.exit(0);     //exits program
                }
            }

            //runs if action command contains "SCR", any components in the scoreboard panel
            else if(c.contains("SCR"))
            {
                //runs if back button was clicked
                if(c.equals("SCRback"))
                {
                    //shows homepage panel
                    AllPanelsCardLayout.show(AllPanels, "homepage");

                    fishInventoryScrollPane.setVisible(false);      //set fish inventory scroll pane visibility to false
                }

                //runs if inventory button as clicked
                else if(c.equals("SCRinventory"))
                {
                    //runs if there are scores saved
                    if(jListPlayerScores != null)
                    {
                        //selected player is equal to get selected index on the scores jlist
                        //indicate which player the user has selected
                        selectedPlayer = jListPlayerScores.getSelectedIndex();

                        //runs there was a player selected
                        if(selectedPlayer != -1)
                        {
                            //use viewInventory method to view selected player's fish inventory
                            viewInventory(selectedPlayer);

                            scoresPanel.revalidate();       //refresh scores panel
                            scoresPanel.repaint();      //repaint scores panel
                        }
                    }
                }
            }

            //runs if action command contains "back", any back button
            else if(c.contains("back"))
            {
                //runs if how to play back button was clicked
                if(c.equals("HTPback"))
                {
                    //shows homepage panel
                    AllPanelsCardLayout.show(AllPanels, "homepage");
                }

                //runs if new player back button was clicked
                else if(c.equals("Nback"))
                {
                    //shows homepage panel
                    AllPanelsCardLayout.show(AllPanels, "homepage");
                }
            }

            //runs if enter button on new player panel was clicked
            else  if(c.equals("Nenter"))
            {
                //stores username input from textfield
                strInputUsername = txtInputUsername.getText();

                //check if username is valid with validateUsername method in FileManger
                String check = FileManager.validateUsername(strInputUsername);

                //runs while username is not valid
                while(check.equals(""))
                {
                    //creates joptionpane input dialog and prompts for username again, informing user of rules
                    strInputUsername = JOptionPane.showInputDialog("Username already taken or is invalid (Can only a-z, A-Z, 0-9, no spaces, 5-10 character length)");

                    //runs if user clicks cancel on input dialog
                    if(strInputUsername == null)
                    {
                        return;     //closes input dialog
                    }

                    //check if new username is valid
                    check = FileManager.validateUsername(strInputUsername);
                }

                Player player = new Player(strInputUsername);       //creates new player with username
                currentPlayer = player;     //sets current player to player just created

                AllPanelsCardLayout.show(AllPanels, "game");        //shows game panel
                Game.gameStart();       //starts game timer with gameStart method in Game class
            }

        }

    }

    //==================================================================================================================
    //==================================================================================================================

}
