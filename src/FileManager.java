import java.util.*;
import java.io.*;

public class FileManager
{
    //GLOBAL VARIABLES
    //==================================================================================================================
    //==================================================================================================================

    private static String[] fishes;     //global string array for fishes
    private static String[] allusernames;       //global string array for all usernames
    private static int[] allscores;     //global string array for all scores
    private static String[] allfishes;      //global string array for all fishes
    private static File flScores = new File("scores.txt");     //global file for score file

    //==================================================================================================================
    //==================================================================================================================


    //CUSTOM FUNCTIONS
    //==================================================================================================================
    //==================================================================================================================

    //method for adding score to file
    public static void addScore(Player player) throws IOException
    {
        //create print writer
        PrintWriter write = new PrintWriter(new FileWriter("scores.txt", true));

        //set intput to noting
        String input = "";

        //run for length for player's inventory
        for(int i = 0; i < (player.getInventory()).size() - 1 ; i++)
        {
            //input equal to all the fish descriptions
            input += ((Fish)((player.getInventory()).get(i))).getDescription() + ",";
        }

        //last index of player inventory equal to size subtract 1
        int lastIndex = (player.getInventory()).size() - 1;


        if(!(player.getInventory()).isEmpty())      //runs if inventory isn't empty
        {
            //get last fish description
            input += ((Fish)((player.getInventory()).get(lastIndex))).getDescription();
        }

        //write to file, player name, score and fish descriptions
        write.write(player.getName() + "," + player.getScore() + "," + input + "\n");

        write.close();      //close file
    }

    //------------------------------------------------------

    //method for sorting file input
    public static void order()  throws IOException
    {
        //create array list for names, scores, and fish
        ArrayList<String> names = new ArrayList();
        ArrayList<Integer> scores = new ArrayList();
        ArrayList<String> fishes = new ArrayList();

        if(flScores.exists())       //runs if file exists
        {
            String strRead = "";        //set read to nothing

            //create file reader
            BufferedReader read = new BufferedReader(new FileReader("scores.txt"));

            while((strRead = read.readLine()) != null)      //reads until nothing more
            {
                //splits read value into 3 array values based on the commas
                String[] sections = strRead.split(",", 3);

                names.add(sections[0]);     //add section 1 to names
                scores.add(Integer.parseInt(sections[1]));      //add section2 to scores
                fishes.add(sections[2]);        //add section 3 to fishes
            }

            allusernames = names.toArray(new String[0]);        //set global array for all usernames to names array list to array
            allfishes = fishes.toArray(new String[0]);      //set global array for all fishes to fishes array list to array

            allscores = new int[scores.size()];     //sets global array for all scores to new array with scores array list size

            //runs for length of scores array
            for(int i = 0; i < allscores.length; i++)
            {
                allscores[i] = scores.get(i);       //add array list values to array
            }

            read.close();       //close file

            //sort the 3 files based on the score
            quickSort(allusernames, allscores, allfishes, 0, allusernames.length - 1);
        }

        //if file doesn't exist
        else
        {
            return;     //return nothing
        }

    }

    //------------------------------------------------------

    //method for getting scoreboard array list
    public static ArrayList getScoreboard()
    {
        try
        {
            order();        //run order function to get ordered global arrays
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        if(flScores.exists())       //runs if file exits
        {
            //declare input and spaces
            String input, spaces = "";

            //declare number of spaces, int space and int 10 space
            int numSpaces, intspaces = 18, int10spaces = 17;

            ArrayList scoreboard = new ArrayList();     //create array list for scoreboard

            for(int i = 0; i < allusernames.length; i++)        //runs for number of players in file
            {
                if(i >= 9)      //runs if i is on the 9th or greater player
                {
                    intspaces = int10spaces;        //int spaces equal to int 10 spaces
                }

                //set number of spaces to int spaces subtract the length of the scoreboard text length
                numSpaces = intspaces - (allusernames[i].length() + (String.valueOf(allscores[i])).length());

                spaces = "";        //set spaces equal to nothing

                for(int j = 0; j < numSpaces; j++)      //run for number of spaces
                {
                    spaces += (" ");        //keep adding space to spaces
                }

                scoreboard.add(i+1 + ". " + allusernames[i] + spaces + allscores[i]);       //add number, username, spaces and score
            }

            return scoreboard;      //return scoreboard array list
        }

        else        //runs in file doesn't exist
        {
            ArrayList scoreboard = new ArrayList();     //create scoreboard array list

            return scoreboard;      //return empty array list
        }
    }

    //------------------------------------------------------

    //method for getting player's fish
    public static ArrayList getInventory(int index)
    {
        try
        {
            order();        //run order function to have ordered global arrays
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


        ArrayList<String> filteredFish = new ArrayList<>();     //create array list for fish

        if(allfishes[index] != "")      //runs if player at index has fish in inventory
        {
            String data = allfishes[index];     //data is equal to player's whole fish inventory
            String[] sections = data.split(",");        //split the data string up by each comma

            //runs for number of sections, increment i by 3 since there are 3 commas per fish info
            for(int i = 0; i < sections.length; i += 3)
            {
                //input is equal to string join, the next texts within the next 3 commas
                String input = String.join(",", sections[i], sections[i+1], sections[i+2]);

                filteredFish.add(input);        //add input to fish array list
            }
        }

        else        //runs if player has no fish in inventory
        {
            filteredFish.add("");       //add nothing to fish array list
        }


        return filteredFish;        //return fish array list
    }

    //------------------------------------------------------

    //method for checking if username is valid
    public static String validateUsername(String username)
    {
        try
        {
            order();        //run order to get array of all usernames
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        ArrayList<String> names = new ArrayList<>();        //create array list for names

        //checking for same username as other players on file
        if(allusernames != null)        //runs if there are other usernames saves
        {
            for(int i = 0; i < allusernames.length; i++)        //runs for number of usernames
            {
                names.add(allusernames[i]);     //add username to names array list
            }

            if(names.contains(username))        //runs if the array list contains the parameter username
            {
                return "";      //returns nothing
            }
        }

        String validCharacter = "^[a-zA-Z0-9]+$";       //create set of valid character strings, a-z, A-Z, 0-9

        //runs if parameter username has character not in valid character set, or username is less than 4 or greater than 11 characters in length
        if (!username.matches(validCharacter) || username.length() < 4 || username.length() > 11)
        {
            return "";      //returns nothing
        }

        //runs if username is valid
        else
        {
            return username;        //returns username
        }
    }

    //==================================================================================================================
    //==================================================================================================================



    //QUICK SORT METHODS
    //==================================================================================================================
    //==================================================================================================================

    public static void quickSort(String[] names, int[] scores, String[] fishDetails, int low, int high)
    {
        if(low < high)
        {
            int p = partition(names, scores, fishDetails, low, high);

            quickSort(names, scores, fishDetails, low, p - 1);
            quickSort(names, scores, fishDetails, p + 1, high);
        }
    }

    public static int partition(String[] names, int[] scores, String[] fishDetails, int low, int high)
    {
        int pivot = scores[high];
        int j = low - 1;

        for(int i = low; i < high; i++)
        {
            if(scores[i] > pivot)
            {
                j++;

                swap(scores,j,i);
                swap(names, j, i);
                swap(fishDetails, j, i);
            }
        }

        swap(scores, j + 1, high);
        swap(names, j + 1, high);
        swap(fishDetails, j + 1, high);


        return j + 1;
    }

    public static void swap(int[] scores, int j, int i)
    {
        int temp = scores[j];
        scores[j] = scores[i];
        scores[i] = temp;
    }

    public static void swap(String[] strings, int j, int i)
    {
        String temp = strings[j];
        strings[j] = strings[i];
        strings[i] = temp;
    }

    //==================================================================================================================
    //==================================================================================================================

}
