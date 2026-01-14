import java.util.Vector;

public class Player
{
    //GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    private static String strName;      //global String for player name
    private static int intScore;        //global int for player score
    private static Vector vctInventory;     //global vector for player's inventory

    //==================================================================================================================
    //==================================================================================================================



    //CONSTRUCTOR
    //==================================================================================================================
    //==================================================================================================================

    public Player(String name)      //constructor
    {
        this.strName = name;        //sets name to parameter value
        vctInventory = new Vector();        //sets vector to new vector
    }

    //==================================================================================================================
    //==================================================================================================================




    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    //method for getting inventory
    public Vector getInventory()
    {
        return vctInventory;        //return vector
    }

    //------------------------------------------------------

    //method for getting name
    public String getName()
    {
        return strName;     //return name
    }

    //------------------------------------------------------

    //get score
    public int getScore()
    {
        return intScore;        //return score
    }

    //------------------------------------------------------

    //method for adding fish
    public static void addFish(Fish fish)
    {
        vctInventory.add(fish);     //add fish in parameter to vector

    }

    //------------------------------------------------------

    //method for updating poitns
    public static void updatePoints()
    {
        for(int i = 0; i < vctInventory.size(); i++)        //runs for number of objects in the vector
        {
            //increments the score by each fish's points using getPoints method in Fish class
            intScore += ((Fish)(vctInventory.get(i))).getPoints();
        }
    }

    //==================================================================================================================
    //==================================================================================================================
}
