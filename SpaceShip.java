import java.util.ArrayList;

/**
 * A spaceship that can move with the arrow keys and fire with the Q key (firing is not in this class). 
 * The spaceship has four boolean parameteres to determine which upgrade path it is on, with true indicating
 * that the ship is on a specific upgrade path.
 * 
 * Name: Nishant Mishra
 * Version: 1.8.0.
 * Period: 7
 * Date: 6/14/2019
 * 
 */
public class SpaceShip
{
    private double radius;
    private double x;
    private double y;
    private String fileName;
    ArrayList<Integer> keyLog = new ArrayList<Integer>(); //log of inputted keys    

    private boolean shooter;
    private boolean health;
    private boolean crazy;
    private boolean reload;

    private int lives;

    /**
     * Constructor for objects of class SpaceShip. Initializes intial location and file name for the graphic
     * Four parameter determine which upgrade path the ship is doing. All are false if the ship has no parameters
     */
    public SpaceShip(boolean s, boolean h, boolean c, boolean r) 
    {
        x = 0.5;
        y = 0.5;
        radius = 0.025;
        fileName = "Fighter.png";
        keyLog.add(0);//adds 0 as the first element in the space ship's key log

        shooter = s;
        health = h;
        crazy = c;
        reload = r;

        lives = 3; //sets the starter life count to three
    }

    /**
     * return the x coordinate of the center point
     */
    public double getX()
    {
        return x;
    }

    /**
     * return the y coordinate of the center point
     */
    public double getY()
    {
        return y;
    }

    /**
     * return the radius
     */
    public double getRadius()
    {
        return radius;
    }

    /**
     * return the number of lives the ship has.
     */
    public int getLives()
    {
        return lives;
    }

    /**
     * updates the number of lives the ship has.
     */
    public void setLives(int l)
    {
        lives = l;
    }

    /**
     * return a string that says which upgrade a ship has. Returns an empty string if the ship has no upgrades
     */
    public String getUpgrade() 
    {
        if (shooter)
        {
            return "shooter";
        }
        else if (health)
        {
            return "health";
        }
        else if (crazy)
        {
            return "crazy";
        }
        else if (reload)
        {
            return "reload";
        }
        else {
            return "";
        }
    }

    /**
     * returns the key log for the main driver class to use
     */
    public ArrayList<Integer> getArrayList() {
        return keyLog;
    }

    /**
     * //takes in inputted keys to determine new coordinates for the tank
     */
    public void updateCoord(double input) 
    {
        if(StdDraw.isKeyPressed(37)) //37 is the keycode for left arrow
        {
            x-=input; //moves ship left
            keyLog.add(1); //represented by one in the keylog
        }
        if(StdDraw.isKeyPressed(38)) //if they press the the down key
        { 
            y+=input; //moves ship downward
            keyLog.add(0); //represented by 0 in the keylog
        }
        if(StdDraw.isKeyPressed(40)) //if they press the up key
        {
            y-=input; //moves ship up
            keyLog.add(2); //represented by two in the keylog
        }
        if(StdDraw.isKeyPressed(39)) //39 is the keycode for right arrow
        { 
            x+=input; //moves ship to the right
            keyLog.add(3); //represented by three in the keylog
        }
    }

    
    /**
     * draws the tank at various coordinates. Uses keyLog to rotate and match the direction
     */
    public void drawDisc()
    {
        if (keyLog.get(keyLog.size()-1) == 0){
            StdDraw.picture(x, y, fileName, 0.1, 0.1, 0);   
        }     
        if (keyLog.get(keyLog.size()-1) == 1){
            StdDraw.picture(x, y, fileName, 0.1, 0.1, 90);   
        }     
        if (keyLog.get(keyLog.size()-1) == 2){
            StdDraw.picture(x, y, fileName, 0.1, 0.1, 180);   
        }     
        if (keyLog.get(keyLog.size()-1) == 3){
            StdDraw.picture(x, y, fileName, 0.1, 0.1, 270);   
        }             

    }
}