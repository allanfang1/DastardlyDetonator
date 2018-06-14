/**
 * Dastardly Detonator
 * ICS3U6 Final
 * @author Victor Lin and Allan Fang
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.Dimension;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;

class GameFrame extends JFrame {
  
  static GameAreaPanel gamePanel;
  static JFrame gameFrame;
  boolean gameOver = false;
  
  GameFrame() {
    super("Dastardly Detonator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Set screen size
    this.getContentPane().setPreferredSize(new Dimension(800,800));
    this.pack();
    
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
        
    this.requestFocusInWindow();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    gameFrame=this;
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();
    
  }
  
  //the main gameloop - this is where the game state is updated
  public void animate() {
    while(!gameOver){
      this.repaint();
    }
    this.dispose();
    new StartScreen();
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel implements KeyListener{
    
    FrameRate frameRate;
    Clock clock;
    Obstruction[][] map;
    Human[] players;
    int mapSize = 21;
    int xOffset = 64;
    int yOffset = 64;
    int tileSize = 32;
    BufferedImage emptySpace;
    
    GameAreaPanel () {
      frameRate = new FrameRate();
      
      //Load empty space image
      try {
        emptySpace = ImageIO.read(new File("img/empty.png"));
      } catch(Exception e){
        System.out.println("Error loading img/empty.png");
      }
      map = new Obstruction[mapSize][mapSize];
      
      //Create players
      players = new Human[2];
      players[0] = new Human(1, 1);
      players[1] = new Human(mapSize - 2, mapSize - 2);
      
      //Generate walls of map
      for (int x = 0; x < mapSize; x++) {
        for (int y = 0; y < mapSize; y++) {
          //Borders
          if ((x == 0) || (x == mapSize - 1) || (y == 0) || (y == mapSize - 1)) {
            map[x][y] = new Wall((x * tileSize) + xOffset, (y * tileSize) + yOffset);
          }
          //Grid
          else if ((x % 2 == 0) && (y % 2 == 0)) {
            map[x][y] = new Wall((x * tileSize) + xOffset, (y * tileSize) + yOffset);
          }
        }
      }
      
      //Generate crates
      Random rand = new Random();
      //Generate 1 quadrant of the crates first
      for (int x = 0; x < ((mapSize / 2) + 1); x++) {
        for (int y = 0; y < ((mapSize / 2) + 1); y++) {
          //Make sure current spot is not a wall
          if (map[x][y] instanceof Wall == false) {
            //Make sure current spot is not in the corner (for player spawn)
            if ((x > 2) || (y > 2)) {
              //Decide whether or not to put a crate here
              if (rand.nextInt(5) > 0) {
                //X and Y coordinates of opposite sides of the map
                int flipX = mapSize - x - 1;
                int flipY = mapSize - y - 1;
                //Create crates
                map[x][y] = new Crate((x * tileSize) + xOffset, (y * tileSize) + yOffset);
                map[flipX][y] = new Crate((flipX * tileSize) + xOffset, (y * tileSize) + yOffset);
                map[x][flipY] = new Crate((x * tileSize) + xOffset, (flipY * tileSize) + yOffset);
                map[flipX][flipY] = new Crate((flipX * tileSize) + xOffset, (flipY * tileSize) + yOffset);
              }
            }
          }
        }
      }
      clock = new Clock();
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g); //required
      setDoubleBuffered(true);
      g.setColor(Color.RED); //There are many graphics commands that Java can use
      
      //update the content
      clock.update();
      frameRate.update();            
      for (int x = 0; x < ((mapSize + 4)* tileSize); x += tileSize) {
        for (int y = 0; y < ((mapSize + 4)* tileSize); y += tileSize) {
          try {
            g.drawImage(emptySpace, x, y, null);
            //If it failed to load the sprite
          } catch(Exception e){}
        }
      }
      
      //Check current player positions
      for (int i = 0; i < players.length; i++) {
          int playerX = players[i].getX();
          int playerY = players[i].getY();
          //If current position is powerup, use powerup
          if (map[playerX][playerY] instanceof Powerup) {
              ((Powerup)(map[playerX][playerY])).usePowerup(players[i]);
              map[playerX][playerY] = null;
          }
          //If current position is explosion, damage player
          if (map[playerX][playerY] instanceof Explosion) {
              players[0].setHealth(players[0].getHealth() - 1);
          }
      }
      for (int x = 0; x < mapSize; x++) {
        for (int y = 0; y < mapSize; y++) {
          if (map[x][y] instanceof Wall) {
            ((Wall)map[x][y]).draw(g);
          }
          else if (map[x][y] instanceof Crate) {
            ((Crate)map[x][y]).draw(g);
          }
          else if (map[x][y] instanceof Bomb) {
            //Check if bomb has exploded
            ((Bomb)map[x][y]).update();
            if (((Bomb)map[x][y]).hasExploded()) {
              //Reduce player's number of current bombs
              int bombOwner = ((Bomb)map[x][y]).getOwner();
              players[bombOwner].setBombs(players[bombOwner].getBombs() - 1);
              int range = ((Bomb)map[x][y]).getRange();
              map[x][y] = new Explosion(x, y, tileSize, xOffset, yOffset);
              map = ((Explosion)map[x][y]).spread(range, 1, 0, map);
              map = ((Explosion)map[x][y]).spread(range, -1, 0, map);
              map = ((Explosion)map[x][y]).spread(range, 0, 1, map);
              map = ((Explosion)map[x][y]).spread(range, 0, -1, map);
            }
            else {
              ((Bomb)map[x][y]).draw(g);
            }
          }
          else if (map[x][y] instanceof Explosion) {
            ((Explosion)map[x][y]).update();
            if (((Explosion)(map[x][y])).shouldFade()) {
              map[x][y] = null;
            }
            else {
              ((Explosion)map[x][y]).draw(g);
            }
          }
          else if (map[x][y] instanceof Powerup) {
            ((Powerup)map[x][y]).draw(g);
          }
        }
      }
      //Check if players are dead
      for (int i = 0; i < players.length; i++) {
        if (players[i].getHealth() > 0) {
          players[i].move(map);
          players[i].draw(g);
        }
        else {
          gameOver = true;
        }
      }
      
      frameRate.draw(g,10,10);
      
      //request a repaint
      repaint();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
      //Player 1 controls
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //W
        players[0].setYDirection(-1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //D
        players[0].setXDirection(1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //S
        players[0].setYDirection(1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //A
        players[0].setXDirection(-1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("C")) {  //C
        //Make sure player is not trying to place bomb on an existent tile
        if (map[players[0].getX()][players[0].getY()] instanceof Obstruction == false) {
          map[players[0].getX()][players[0].getY()] = players[0].placeBomb(0);
        }
      }
      //Player 2 controls
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //Up
        players[1].setYDirection(-1);
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //Right
        players[1].setXDirection(1);
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //Down
        players[1].setYDirection(1);
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //Left
        players[1].setXDirection(-1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("M")) {  //M
        //Make sure player is not trying to place bomb on an existent tile
        if (map[players[1].getX()][players[1].getY()] instanceof Obstruction == false) {
          map[players[1].getX()][players[1].getY()] = players[1].placeBomb(1);
        }
      }
      //Quit game
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        gameFrame.dispose();
      }
    }
    
    public void keyReleased(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        players[0].setYDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        players[0].setXDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        players[0].setYDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        players[0].setXDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //If 'W' is pressed
        players[1].setYDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //If 'D' is pressed
        players[1].setXDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //If 'S' is pressed
        players[1].setYDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //If 'A' is pressed
        players[1].setXDirection(0);
      }
    }
  }
  
// -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
//A class to track time
  
  class Clock {
    long elapsedTime;
    long lastTimeCheck;
    
    public Clock() {
      lastTimeCheck=System.nanoTime();
      elapsedTime=0;
    }
    
    public void update() {
      long currentTime = System.nanoTime();  //if the computer is fast you need more precision
      elapsedTime=currentTime - lastTimeCheck;
      lastTimeCheck=currentTime;
    }
    
    //return elapsed time in milliseconds
    public double getElapsedTime() {
      return elapsedTime/1.0E9;
    }
  }
  
  
//A class to represent the object moving around on the screen
    
  //Better to abstract the FrameRate stuff
  class FrameRate {
    
    String frameRate; //to display the frame rate to the screen
    long lastTimeCheck; //store the time of the last time the time was recorded
    long deltaTime; //to keep the elapsed time between current time and last time
    int frameCount; //used to cound how many frame occurred in the elasped time (fps)
    
    public FrameRate() {
      lastTimeCheck = System.currentTimeMillis();
      frameCount=0;
      frameRate="0 fps";
    }
    
    public void update() {
      long currentTime = System.currentTimeMillis();  //get the current time
      deltaTime += currentTime - lastTimeCheck; //add to the elapsed time
      lastTimeCheck = currentTime; //update the last time var
      frameCount++; // everytime this method is called it is a new frame
      if (deltaTime>=1000) { //when a second has passed, update the string message
        frameRate = frameCount + " fps" ;
        frameCount=0; //reset the number of frames since last update
        deltaTime=0;  //reset the elapsed time
      }
    }
    
    public void draw(Graphics g, int x, int y) {
      g.drawString(frameRate,x,y); //display the frameRate
    }
  }
}
