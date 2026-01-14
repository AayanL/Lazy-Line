import java.util.Random;

public class Fish
{
    //GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    private String strName;     //global string for fish name
    private String strType;     //global string for fish type
    private int intWeight;      //global int for fish weight
    private String strRarity;       //global string for fish rarity
    private static int intPoints;      //global int for fish points
    private static String[] rarities = {"Common","Uncommon","Rare","Legendary"};        //global String array for all rarities
    private static String[] types = {"Salmon", "Tuna", "Bass", "Catfish", "Barracuda"};     //global String array for all types

    //==================================================================================================================
    //==================================================================================================================



    //CONSTRUCTOR
    //==================================================================================================================
    //==================================================================================================================

    public Fish(String type, String rarity, int weight)
    {
        this.strType = type;        //sets type to parameter value
        this.intWeight = weight;        //sets weight to parameter value
        this.strRarity = rarity;        //sets rarity to parameter value
        setPoints();        //sets fish points using setPoints method
    }

    //==================================================================================================================
    //==================================================================================================================



    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    //method for setting points
    private void setPoints()
    {
        //declare doubles for total points, rarity score, rarity factor and weight factor
        double dblTotalPoints, dblRarityScore = 0,dblRarityFactor = 2.5, dblWeightFactor = 1.5;

        //declare int for total points
        int intTotalPoints;

        //runs if rarity is common
        if(strRarity.equals("Common"))
        {
            dblRarityScore = 1;     //sets rarity score to 1
        }

        //runs if rarity is uncommon
        else if(strRarity.equals("Uncommon"))
        {
            dblRarityScore = 2;     //sets rarity score to 2
        }

        //runs if rarity is rare
        else if(strRarity.equals("Rare"))
        {
            dblRarityScore = 4;     //sets rarity score to 4
        }

        //runs if rarity is legendary
        else if(strRarity.equals("Legendary"))
        {
            dblRarityScore = 8;     //sets rarity score to 8
        }

        //calculates total points by adding the weight multiplied by the weight factor, and the rarity score multiplied by the rarity factor
        dblTotalPoints = (intWeight * dblWeightFactor) + (dblRarityScore * dblRarityFactor);

        intTotalPoints = (int)(Math.round(dblTotalPoints));        //set total points to int and round the double value

        this.intPoints = intTotalPoints;        //set points to total points
    }

    //------------------------------------------------------

    //method for getting points
    public static int getPoints()
    {
        return intPoints;       //return points
    }

    //------------------------------------------------------

    //method for getting a random fish
    public static Fish getRandomFish()
    {
        Random rnd = new Random();      //create random object

        int randomNum = rnd.nextInt(4);     //create random number from 0 to 3
        int randomNum2 = rnd.nextInt(5);        //create random number from 0 to 4
        int weight = (int)((Math.random() * (20 - 5)) + 5);     //creates random number from 5 to 20

        //creates fish with these random values as indexes and value
        Fish fish = new Fish(types[randomNum2], rarities[randomNum], weight);

        return fish;        //return the random fish
    }

    //------------------------------------------------------

    //method for getting fish description
    public String getDescription()
    {
        String description = strType + "," + strRarity + "," + intWeight + " lbs";      //string equals type + rarity + weight

        return description;     //return string
    }

    //==================================================================================================================
    //==================================================================================================================
}
