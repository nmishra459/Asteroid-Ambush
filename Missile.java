import java.util.ArrayList;

class Missile {

    // fields
    private double x;
    private double y;
    private double dy;
    private double dx;
    private double size;
    private String fileName;
    private int lastInput;
    private double radius;
    private boolean direction;

    /**
     * constructor for Missile. assigns x and y-coordinate, spaceships, and direction
     */
    public Missile(double x, double y, SpaceShip spaceShip, boolean dir) {
        this.x = x;
        this.y = y;
        dy = 0.02;
        dx = 0.02;
        size = 0.01;
        fileName = "Laser.png";
        ArrayList<Integer> log = spaceShip.getArrayList();
        lastInput = log.get(log.size()-1);
        direction = dir; //used when upgraded to the shooter class
    }

    /**
     * returns x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * returns y-coordinate
     */
    public double getY() {
        return y; 
    }

    /**
     * returns radius
     */
    public double getRadius() {
        return size;
    }

    /**
     * returns the x-coordinate of the tip of the laser
     */
    public double getTipX() { 
        return x; 
    }

    /**
     * returns the y-coordinate of the tip of the laser
     */
    public double getTipY() {
        return y + size; 
    }

    /**
    * draws the missile
    */ 
    public void draw() {
        if (direction) //standard direction drawing
        {
            if (lastInput == 0) { 
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 90); //creates upward firing missiles
            }

            else if (lastInput == 1) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 180); //creates leftward firing missiles        
            }

            else if (lastInput == 2) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 270); //creates downward firing missiles               
            }

            else if (lastInput == 3) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 0); //creates rightward firing missiles           
            }
        }
        else //backwards shooting for the shooter class
        {
            if (lastInput == 2) { 
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 90); //creates upward firing missiles
            }

            else if (lastInput == 3) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 180); //creates leftward firing missiles        
            }

            else if (lastInput == 0) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 270); //creates downward firing missiles               
            }

            else if (lastInput == 1) {
                StdDraw.picture(x, y, fileName, 0.05, 0.05, 0); //creates rightward firing missiles           
            }
        }
    }

    /**
     * moves the missile
     */ 
    public void move() {
        if (direction){ //standard direction
            if (lastInput == 0) {
                y += dy;
            }
            else if (lastInput == 3) {
                x += dx;           
            }
            else if (lastInput == 2) {
                y -= dy;
            }
            else if (lastInput == 1) {
                x -=dx;
            }
        }
        else { //opposite direction for when shooter class is chosen
            if (lastInput == 0) {
                y -= dy;
            }
            else if (lastInput == 3) {
                x -= dx;           
            }
            else if (lastInput == 2) {
                y += dy;
            }
            else if (lastInput == 1) {
                x +=dx;
            }
        }
    }
} 