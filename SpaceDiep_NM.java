import java.util.ArrayList;
import java.awt.Font;
import java.awt.*;

/**
 * Main driver class for SpaceDiep, a combination of a traditional space shooter game and diep.io. After destroying asteroids, players will be 
 * able to choose from four upgrade paths to collect the most coins and maximize thenumber of asteroids that they can destroy. The player starts 
 * with three lives, but that can change as the game progresses. Asteroid generation is based off a number of different factors (explained in code
 * documentation), sometimes with only one asteroid and sometimes with multiple. Enjoy!
 */
public class SpaceDiep_NM
{
    public static void main(String[] args) 
    {

        //Draws canvas
        StdDraw.setCanvasSize(1000, 900); //1000 pixels wide, 900 pixels tall

        boolean noKey = true; //keeps the instruction screen open
        while (noKey) {
            StdDraw.clear(StdDraw.WHITE); //background white
            StdDraw.setPenColor(StdDraw.BLACK); //text will now be black

            Font fontA = new Font("Arial", Font.BOLD, 18); //defines font
            StdDraw.setFont(fontA); //sets font

            //Instructions, split up by color
            StdDraw.text(0.5, 0.85, "Hello, and welcome to SpaceDiep, a combination of a traditional space shooter and Diep.io.");

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.text(0.4, 0.75, "You can move around with the arrow keys, and shoot by pressing Q");
            StdDraw.picture(0.8, 0.75, "Fighter.png", 0.1, 0.1, 0); //draws spaceship to show players what it looks like.

            StdDraw.text(0.4, 0.70, "Going to a coin will create asteroids. Destroying them will increase your score.");
            StdDraw.picture(0.83, 0.70, "Coin.png", 0.05, 0.05, 0); //draws coin to show players what it looks like.            

            StdDraw.text(0.4, 0.65, "Occasionally, hitting an asteroid or touching a coin will create multiple asteroids.");
            StdDraw.picture(0.80, 0.65, "Asteroid.png", 0.05, 0.05, 0); //draws asteroid to show playes what it looks like            

            StdDraw.text(0.4, 0.60, "Whenever this happens, you gain a life. (You start with three)");       

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(0.5, 0.5, "Once you destroy enouch asteroids and hit enough coins, you can upgrade your ship. Instructions to appear later.");
            StdDraw.text(0.5, 0.4, "After you upgrade, bigger asteroids that require three hits may spawn.");

            StdDraw.text(0.5, 0.3, "PRESS A TO BEGIN. FLY TO THE COIN TO CREATE ASTEROIDS"); //final instruction           

            if(StdDraw.isKeyPressed('A')) {
                noKey = false; //lets player leave instruction screen 
            } 
            StdDraw.show(5);
        }

        boolean stillPlaying = true; //keeps game active
        while (stillPlaying) { //keeps game active until player says he or she doesen't want to play anymore
            //sets up space ship, the coin, and arraylists of asteroids, missiles, and keys (used to keep track of which keys are pressed in the game)
            SpaceShip spaceShip = new SpaceShip(false, false, false, false); //four parameters explained in spaceShip class
            Coin coin = new Coin();
            ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
            ArrayList<Missile> missiles = new ArrayList<Missile>();
            ArrayList<Integer> keyLog = new ArrayList<Integer>(); //log of inputted keys            

            //initial game condition
            boolean gameOver = false;

            boolean bigAsteroids = false; //true if an asteroid is big and false if an asteroid is not

            int lives = spaceShip.getLives(); //lives that the space ship has throughout the game
            int asteroidsDestroyed = 0; //counter of how many asteroids are destroyed
            int coinsCollected = 0; //counter of how many coins are collected
            StdDraw.enableDoubleBuffering(); //makes animations smoother

            double timePassed; //tracks time passed in milliseconds
            double inSeconds; //will be used to determine how many seconds have passed           

            long startTime = System.currentTimeMillis(); //starts times

            double speed = 0.005; //speed of the ship
            double counter = 0; //arbitrary value that increases when asteroids are destroyed or coins are collected. Helps determine when upgrades should be offered

            double X = 0.1; // length of every object
            double Y = 0.1; // height of every object            

            while (gameOver == false) { 
                StdDraw.clear(StdDraw.WHITE); //sets background

                //Keeps the asteroid count to a bare minimum level as the player destroys more and more asteroids
                if (asteroidsDestroyed > 40) //Level 3
                {
                    while (asteroids.size() < 25)
                    {
                        asteroids.add(new Asteroid(false));                        
                    }
                }
                else if(asteroidsDestroyed > 18) //Level 2
                {
                    while (asteroids.size() < 18)
                    {
                        asteroids.add(new Asteroid(false));                        
                    }
                }
                else if(asteroidsDestroyed > 5) //Level 1
                {
                    while (asteroids.size() < 6)
                    {
                        asteroids.add(new Asteroid(false));                        
                    }
                }

                ArrayList<Integer> log = spaceShip.getArrayList(); //integer log that helps determine the movement and image turning of the ship
                timePassed = System.currentTimeMillis() - startTime; //calculates times passed
                inSeconds = (timePassed/1000) % 60; //calculare timePassed value in terms of seconds

                StdDraw.setPenColor(StdDraw.BLACK);

                spaceShip.updateCoord(speed); //changes coordinates for the tank
                spaceShip.drawDisc(); //draws Tank at new coordinates

                //The code below draws and updates the scoreboard at the top of the canvas
                Font font0 = new Font("Arial", Font.BOLD, 16);
                StdDraw.setFont(font0);

                StdDraw.text(0.12, 0.95, "ASTEROIDS DESTROYED:");
                String scoreText = Integer.toString(asteroidsDestroyed);
                StdDraw.text(0.24, 0.95, scoreText);

                StdDraw.text(0.85, 0.95, "LIVES:");
                String livesText = Integer.toString(spaceShip.getLives());
                StdDraw.text(0.9, 0.95, livesText);

                StdDraw.text(0.5, 0.95, "TIME:");
                String timeText = Integer.toString((int) timePassed/1000);
                StdDraw.text(0.6, 0.95, timeText);
                
                String finalScore = "SCORE: " + (asteroidsDestroyed*50 + 400*coinsCollected); //calculates final score
                StdDraw.text(0.12, 0.05, finalScore);
                
                coin.drawDisc(); //draws a coin at a random location

                String upgrade = spaceShip.getUpgrade(); //returns the upgrade assoicated with the spaceship. If the ship has no upgrade, and empty sting is assigned to upgrade

                //Adds upgrade name to score board so players know what path they have taken
                if (upgrade.equals("shooter")){
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.text(0.12, 0.90, "SHOOTER");
                }

                else if (upgrade.equals("health")) {
                    StdDraw.setPenColor(StdDraw.GREEN);                    
                    StdDraw.text(0.12, 0.90, "HEALTH");    
                }

                else if (upgrade.equals("crazy")){
                    StdDraw.setPenColor(StdDraw.ORANGE);                    
                    StdDraw.text(0.12, 0.90, "CRAZY");
                }

                else if (upgrade.equals("reload")){
                    StdDraw.setPenColor(StdDraw.BLUE);        
                    StdDraw.text(0.12, 0.90, "RELOAD");
                }

                //Code for when the spaceship comes in contact with the coin.
                if (coin.distFromDisc(spaceShip))
                {
                    counter += 2;
                    coinsCollected++; //increments number of coins
                    if (!bigAsteroids) { //adds a normal asteroid if the player hasn't reached the "big asteroid" stage yet
                        asteroids.add(new Asteroid(false));
                    }
                    else if (bigAsteroids) { //runs a probability algorithim to ensure that every once in a while, a big asteroid is formed.
                        double x = Math.random()*11;
                        if (x > 8)
                        {
                            asteroids.add(new Asteroid(true)); //big asteroid - less likely
                        }
                        else {
                            asteroids.add(new Asteroid(false)); //small asteroid - more likely                       
                        }

                    }
                }

                //code to handle the movement of the asteroids and collisions between the asteroid and the space ship
                for (int i = 0; i < asteroids.size(); i++) {
                    asteroids.get(i).updateCoord(); //moves asteroid coordinates
                    asteroids.get(i).drawDisc(); // redraws asteroid at new coordinates
                    if (asteroids.get(i).distFromDisc(spaceShip, gameOver)) //tests if ast. and s.s. are touching 
                    {
                        spaceShip.setLives(spaceShip.getLives()-1);//decrements the number of lives the spaceship has
                        asteroids.remove(i); //removes the asteroid
                        i = asteroids.size() + 1; //effectively breaks out of the loop to prevent indexing errors
                        if(spaceShip.getLives() == 0 ){ //if the spaceship runs out of lives, this loop beings endgame procedure
                            gameOver = true;
                            StdDraw.setPenColor(StdDraw.BLACK);                            
                            StdDraw.text(0.9, 0.95, Integer.toString(0)); //updates the lives scoreboard to become zero
                        }
                    }

                }

                //shooting algorithim. inSeconds part is there to limit shooting capability and prevent an unlimited stream of bullets
                if (spaceShip.getUpgrade().equals("reload")) {
                    if(StdDraw.isKeyPressed('Q') && inSeconds%3 >= .8) //RELOAD MODE SHOOTS FASTER
                    {
                        missiles.add(new Missile(spaceShip.getX(), spaceShip.getY(), spaceShip, true)); //creates a missile
                    }
                }
                
                else if (spaceShip.getUpgrade().equals("shooter")) {
                    if(StdDraw.isKeyPressed('Q') && inSeconds%3 >= 1.6) //RELOAD MODE SHOOTS EVEN SLOWER
                    {
                        missiles.add(new Missile(spaceShip.getX(), spaceShip.getY(), spaceShip, true)); //creates a missile

                        missiles.add(new Missile(spaceShip.getX(), spaceShip.getY(), spaceShip, false));                  

                    }
                }
                
                else {
                    if(StdDraw.isKeyPressed('Q') && inSeconds%3 >= 1.25) //SLOWER RELOAD MODE
                    {

                        missiles.add(new Missile(spaceShip.getX(), spaceShip.getY(), spaceShip, true)); //creates a missile
                    }    
                }

                //missile movement algorithim. 
                for(int i = 0; i < missiles.size();i++) {                 
                    
                        missiles.get(i).draw();
                        missiles.get(i).move();

                    
                }

                if (spaceShip.getUpgrade().equals("crazy"))
                {
                    for (int j = asteroids.size() -1 ; j >= 0; j--) {
                        for(int k = missiles.size() -1 ; k >= 0; k--) { 
                            if (asteroids.get(j).distFromMissile(spaceShip, missiles.get(k))){
                                double x = Math.random()*10;
                                if (x < 1)
                                {
                                    for(int i = 0; i < asteroids.size(); i++)
                                    {
                                        if( i%2 == 0){
                                            asteroids.remove(i);
                                            asteroidsDestroyed++;
                                        }
                                    }
                                    k = -1;
                                    j = -1;
                                }
                            }  
                        }
                    }
                }

                //missiles and asteroid collision algorithim. Tets each asteroid and missile combination with a nested for-lopp
                for (int j = asteroids.size() -1 ; j >= 0; j--) {
                    for(int k = missiles.size() -1 ; k >= 0; k--) {             
                        if (asteroids.get(j).distFromMissile(spaceShip, missiles.get(k))){ // if the two objects under question are touching (psudeocode)
                            int asteroidLives = asteroids.get(j).getNumLives(); //
                            if(asteroids.get(j).getNumLives() > 0) {
                                missiles.remove(k);
                                counter++;
                                asteroids.get(j).setNumLives(asteroidLives - 1);
                                k =-1;
                            }                   
                            else
                            {
                                asteroids.remove(j);
                                missiles.remove(k);
                                asteroidsDestroyed++;
                                j = -1;
                                k = -1;  
                            }
                        }
                    }
                }

                //occasionally adds three asteroids if the counter reaches a certain value  > makes the game harder and more unpredictable
                if ((counter+1)%13 == 0) 
                {
                    for (int i=0;i<3;i++)
                    {
                        asteroids.add(new Asteroid(false)); //all asteroids added are small
                    }
                    spaceShip.setLives(lives+1); //adds a life to compensate for the three added asteroids
                    counter++; //increments the counter to ensure no infinite loop occurs
                }

                //upgrade benchmark screen and selection
                if (counter == 19 || counter == 20) //two choices ensure that the varying increases in counter (+1 or +2) don't miss the upgrade benchmark
                {
                    StdDraw.clear();
                    StdDraw.text(0.5, 0.75, "You have reached upgrade mode. Type the key for the upgrade you want.");   

                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.text(0.5, 0.70, "S - Shooter Mode. Bullets come from both sides of the ship. Slows reload, however");         

                    StdDraw.setPenColor(StdDraw.GREEN);                    
                    StdDraw.text(0.5, 0.65, "H - Health Mode. 20 percent chance for lives to increase from destroying an asteroid.");                                          

                    StdDraw.setPenColor(StdDraw.ORANGE);                    
                    StdDraw.text(0.5, 0.60, "C - Crazy Mode. 15 percent chance half of the asteroids disappear when touching a coin or destroying an asteroid.");          

                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.text(0.5, 0.55, "R - Reload Mode. Able to fire shots more continously.");                              

                    StdDraw.text(0.5, 0.4, "WARNING. BIG ASTEROID MAY NOW SPAWN. THEY TAKE MORE HITS TO DESTROY");                              
                    StdDraw.show(5);

                    boolean choosingUpgrade = true;
                    //takes in key input and updates space ship to reflect the upgrade path taken
                    while (choosingUpgrade) {
                        if(StdDraw.isKeyPressed('S')) {
                            choosingUpgrade = false;   
                            spaceShip = new SpaceShip(true, false, false, false);
                        }
                        else if(StdDraw.isKeyPressed('H')) {
                            choosingUpgrade = false;          
                            spaceShip = new SpaceShip(false, true, false, false);
                        }

                        else if(StdDraw.isKeyPressed('C')) {
                            choosingUpgrade = false;
                            spaceShip = new SpaceShip(false, false, true, false);                                                     
                        }

                        else if (StdDraw.isKeyPressed('R')) {
                            choosingUpgrade = false;  
                            spaceShip = new SpaceShip(false, false, false, true);                                                     
                        }
                    }
                    counter++;
                    bigAsteroids = true; //makes bigger asteroids possible;
                }

                StdDraw.show(5);                
            }

            //Game over screen
            StdDraw.setPenColor(StdDraw.BLACK);

            Font font = new Font("Arial", Font.BOLD, 60);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.6, "GAME OVER");

            String finalScore = "FINAL SCORE: " + (asteroidsDestroyed*50 + 400*coinsCollected); //calculates final score
            StdDraw.text(0.5, 0.5, finalScore);
            Font font2 = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font2);
            StdDraw.text(0.5, 0.4, "Play Again? Y or N");

            StdDraw.show(5);            
            boolean deciding = true;

            //code asking if players
            while (deciding) 
            {
                if(StdDraw.isKeyPressed('Y')) {
                    //sets booleans to play again
                    gameOver = false;
                    stillPlaying = true;
                    deciding = false;
                } 

                if(StdDraw.isKeyPressed('N')) {
                    //sets booleans to end game loop
                    gameOver = true;
                    stillPlaying = false;
                    deciding = false;
                    StdDraw.clear();
                    StdDraw.show(5);
                }
            }

        }
    }
}
