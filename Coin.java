/**
 * Coin object that increases the players score and generates more asteroids to destroy
 * 
 * Name: Nishant Mishra
 * Java Version: 1.8.0.
 * Data: 6/14/19
 * Period: 7
 * 
 */
public class Coin //coin object
{
    private double radius;
    private double x;
    private double y;
    private String fileName;

    /**
     * Constructor for objects of class BlueDisc
     */
    public Coin()
    {
        //sets coordinates to a random location
        x = Math.random();
        y = Math.random();
        
        //file name
        fileName = "Coin.png";
        
        //radius of Coin
        radius = 0.03;
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
     * return the x coordinate of the center point
     */
    public void updateCoord()
    {
        int i = 0;
    }
    
    /**
     * draw the coins
     */
    public void drawDisc()
    {
        StdDraw.picture(x, y, fileName, 0.1, 0.1, 0);       
    }
    
    /**
     * determine the distance from the coin from the spaceship. if they are touching,
     * the co is moved to a random spot in the unit square
     */
    public boolean distFromDisc(SpaceShip spaceShip)
    {
        double distanceX = x - spaceShip.getX();
        double distanceY = y - spaceShip.getY();
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        boolean contact;
        if (distance <= radius * 2) {
            x = Math.random();
            y = Math.random();
            contact = true;
        } 
        else { 
            contact = false;
        }
        return contact;
    }
}
