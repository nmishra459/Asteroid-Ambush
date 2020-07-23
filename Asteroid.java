import java.lang.Math;

/**
 * Asteroids that move around the canvas at different speeds. Some are big and some are small, and the 
 * objects have methods to determine asteroid behavior when hit by both a missile and a space ship.
 * 
 * Name: Nishant Mishra
 * Java Version: 1.8.0. 
 * Date: 6/14/2019
 * Period: 7
 * 
 */
public class Asteroid
{
    private double radius;
    private double x;
    private double y;
    private int direction;
    private String fileName;
    private boolean size;
    private int asteroidLives;

    /**
     * Constructor for objects of class Asteroid
     */
    public Asteroid(boolean isBig) //only one parameter: true if asteroid is big
    {
       //randomizes coordinates
        x = Math.random();
        y = Math.random();

        fileName = "Asteroid.png";

        direction = (int)(Math.random() * (5 - 1) + 1); 
        // 1 is up right
        // 2 is down right
        // 3 is down left
        // 4 is up left
        
        if (isBig){ //changes asteroid size if its big
            radius = 0.005;
            asteroidLives = 3;
        }   
        else if (!isBig) { //default asteroid size
            radius = 0.0005;
            asteroidLives = 0;
        }
        size = isBig;
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
     * return the lives a singular asteroid has - 1 for small, 3 for big
     */    
    public int getNumLives() 
    {
        return asteroidLives;
    }

    /**
     * changes the number of lives an asteroid has
     */
    public void setNumLives(int lives) 
    {
        asteroidLives = lives;
    }    

    /**
     * move the asteroids and change their direction if they hit a wall
     */
    public void updateCoord()
    {
        if (direction == 1) {
            if (y > 1 - radius) direction = 2; //if hit top wall go down right
            else if (x > 1 - radius) direction = 4; // if hit right wall go up left
            else { //move disc up right
                double speed = Math.random()*0.005;
                x += speed;
                y += speed;
            }
        } else if (direction == 2) {
            if (y < 0 + radius) direction = 1; //if hit bottom go up right
            else if (x > 1 - radius) direction = 3; //if hit right wall go down left
            else { //move 
                double speed = Math.random()*0.005;
                x += speed;
                y -= speed;
            }
        } else if (direction == 3) {
            if (y < 0 + radius) direction = 4; // if hit bottom go up left
            else if (x < 0 + radius) direction = 2; // if hit left wall go down right
            else {
                double speed = Math.random()*0.005;
                x -= speed;
                y -= speed;
            }
        }
        else if (direction == 4) {
            if (y > 1 + radius) direction = 3; // if hit top wall go down left
            else if (x < 0 + radius) direction = 1; // if hit left wall go up left
            else {
                double speed = Math.random()*0.005;
                x -= speed;
                y += speed;
            }
        }
    }

    /**
     * draw the asteroid using the fileName;
     */
    public void drawDisc()
    {
        if (!size) {
            StdDraw.picture(x, y, fileName, 0.05, 0.05, 0);
        }
        else if (size) {
            StdDraw.picture(x, y, fileName, 0.1, 0.1, 0);        
        }
    }

    /**
     * determine if the space ship comes into contact with an asteroid. if it does,
     * true is returned, so the main driver can deincrement the number of lives the ship has left
     */
    public boolean distFromDisc(SpaceShip spaceShip, boolean done)
    {
        double distanceX = x - spaceShip.getX();
        double distanceY = y - spaceShip.getY();
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if (distance <= radius + spaceShip.getRadius()) 
        {
            done = true;
        }
        return done;
    }

    /**
     * determine if the missile comes into contact with an asteroid. if it does,
     * true is returned, so the main driver can can remove both from the canvas.
     */    
    public boolean distFromMissile(SpaceShip spaceShip, Missile missile)
    {
        double distanceX = x - missile.getX();
        double distanceY = y - missile.getY();
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if (distance <= radius + missile.getRadius()) {
            if(spaceShip.getUpgrade().equals("health"))
            {
                double x = Math.random()*11;
                if (x > 9.5)
                {
                    spaceShip.setLives(spaceShip.getLives()+1);
                }
            }   
            return true;
        }
        return false;
    }
}
