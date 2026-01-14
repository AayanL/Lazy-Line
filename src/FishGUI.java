import java.awt.*;
import java.util.*;

public  class FishGUI
{
    //GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    private int intXCoordinate;     //global int for x coordinate
    private int intYCoordinate;     //global int for y coordinate
    private int intSpeed;       //global in for speed
    private boolean isCaught;       //global boolean for if caught or not
    private static Random rnd = new Random();       //global random object

    //==================================================================================================================
    //==================================================================================================================



    //CONSTRUCTOR
    //==================================================================================================================
    //==================================================================================================================

    public FishGUI(int x, int y)        //constructor
    {
        this.intXCoordinate = x;        //sets x value to parameter value
        this.intYCoordinate = y;        //sets y value to parameter value
        this.isCaught = false;      //sets caught to false

        int chance = rnd.nextInt(2);    //get random number from 0 and 1

        if(chance == 0)     //runs if random number wass 0
        {
            rndPositiveSpeed();     //set random positive speed using rndPositiveSpeed method
        }

        else        //runs if random number was 1
        {
            rndNegativeSpeed();     //set random negative speed using rndNegativeSpeed method
        }
    }

    //==================================================================================================================
    //==================================================================================================================



    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    //method for getting y coordinate
    public int getYCoordinate()
    {
        return this.intYCoordinate;     //returns y coordinate
    }

    //------------------------------------------------------

    //method for getting x coordinate
    public int getXCoordinate()
    {
        return this.intXCoordinate;     //returns x coordinate
    }

    //------------------------------------------------------

    //method for setting random positive speed
    private void rndPositiveSpeed()
    {
        this.intSpeed = (int)(Math.random() * (10-5) + 5);      //sets speed to random number from 5 to 10
    }

    //------------------------------------------------------

    private void rndNegativeSpeed()
    {
        this.intSpeed = (int)((Math.random() * 6) - 10);        //sets speed to random number from -10 to -6
    }

    //------------------------------------------------------

    //method for updating position
    public void updatePosition()
    {
        //runs if x coordinate is greater than or equal to 900
        if(intXCoordinate >= 900)
        {
            this.intXCoordinate = 0;        //set x coordinate to 0
            randomYCoordinate();        //set random y coordinate using randomYCoordinate method
            rndPositiveSpeed();     //set random positive speed using rndPositiveSpeed
            isCaught = false;       //sets is caught to false
        }

        //runs if x coordinate is less than or equal to 0
        else if(intXCoordinate <= 0)
        {
            this.intXCoordinate = 900;      //sets x coordinate to 900
            randomYCoordinate();        //sets random y coordinate using randomYCoordinate method
            rndNegativeSpeed();     //sets random negative speed using rndNegativeSpeed
            this.isCaught = false;      //sets is caught to false
        }

        this.intXCoordinate += intSpeed;        //increment x coordinate with speed
    }

    //------------------------------------------------------

    //method to check if fish is in range of mouse click
    public boolean clickInRange(int xClick, int yClick)
    {
        //returns true if the x and y coordinate are within 50 pixels of the click
        return ((Math.abs(xClick - intXCoordinate)) <= 50 && (Math.abs(yClick - intYCoordinate)) <= 50);
    }

    //------------------------------------------------------

    //method for setting random y coordinte
    public void randomYCoordinate()
    {
        int x = 0;      //set x to 0

        //finding the current y coordinate in the y coordinates array so that y coordinate won't be choosed again
        for(int i = 0; i < Game.fishYCoordinates.length; i++)       //runs for number of fish coordinates
        {
            //runs if this fish's y coordinate is equal to the fish coordinates at index i
            if(intYCoordinate == Game.fishYCoordinates[i])
            {
                Game.takenYCoordinates[i] = false;      //sets this y coordinate as taken
                x = i;      //set x equal to i
            }
        }

        while(true)     //runs until break condition met
        {
            int r = rnd.nextInt(6);     //get random number from 0 to 5

            //runs if coordinate at index r is not taken and r is not equal to x(current y coordinate)
            if (!Game.takenYCoordinates[r] && r != x)
            {
                this.intYCoordinate = Game.fishYCoordinates[r];     //set y coordinate to fish coordinate at index r
                Game.takenYCoordinates[r] = true;       //set y coordinate at index r as taken
                break;      //break
            }
        }
    }

    //------------------------------------------------------

    //method for setting fish as caught
    public void fishCaught()
    {
        this.isCaught = true;       //set is caught to true
    }

    //------------------------------------------------------

    //method for setting fish state
    public void setFishState(boolean b)
    {
        this.isCaught = b;      //set is caught to parameter value
    }

    //------------------------------------------------------

    //method for getting fish state
    public boolean isCaught()
    {
        return this.isCaught;       //return fish state
    }

    //==================================================================================================================
    //==================================================================================================================
}
