//Name: Aayan Latif
//Date: 20 Jan, 2025
//Title: ICS4U1 Culminating
//Purpose: Create object oriented game

public class Program
{
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                AllPages.runGUI();      //run AllPages runGUI method to start game
            }
        });
    }
}
