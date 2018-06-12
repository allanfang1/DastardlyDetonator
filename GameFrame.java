/**
 * This template can be used as reference or a starting point
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Random;

class GameFrame extends JFrame {
  
  static GameAreaPanel gamePanel;
  static JFrame gameFrame;
  
  GameFrame() {
    super("Dastardly Detonator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    this.setUndecorated(true);
    
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    /*MyKeyListener keyListener = new MyKeyListener();
     this.addKeyListener(keyListener);
     
     MyMouseListener mouseListener = new MyMouseListener();
     this.addMouseListener(mouseListener);
     */
    
    this.requestFocusInWindow();
    this.setVisible(true);
    gameFrame=this;
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();
    
  }
  
  //the main gameloop - this is where the game state is updated
  public void animate() {
    while(true){
      this.repaint();
    }
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel implements KeyListener{
    
    Human player1;
    Human player2;
    FrameRate frameRate;
    Clock clock;
    Obstruction[][] map;
    int mapSize = 21;
    int xOffset = 50;
    int yOffset = 50;
    int tileSize = 25;
    
    GameAreaPanel () {
      frameRate = new FrameRate();
      player1 = new Human(75, 75, 22);
      player2 = new Human(75, 125, 22);
      map = new Obstruction[mapSize][mapSize];
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
      //player1.update(clock.getElapsedTime());  //you can 'pause' the game by forcing elapsed time to zero
      
      //draw the screen
      player1.draw(g);
      whereCrash();
      if(player1.getWall1() != 2){
        if(player1.getXDirection() > 0){
          player1.moveX(clock.getElapsedTime());
        }
      }
      if(player1.getWall2() != 1){
        if(player1.getYDirection() < 0){
          player1.moveY(clock.getElapsedTime());
        }
      }
      if(player1.getWall2() !=2){
        if(player1.getYDirection() > 0){
          player1.moveY(clock.getElapsedTime());
        }
      }
      if(player1.getWall1() != 1){
        if(player1.getXDirection() < 0){
          player1.moveX(clock.getElapsedTime());
        }
      }
      player2.draw(g);
      player2.move(clock.getElapsedTime());
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
              //TODO: this is probably not a good idea
              map[x][y] = null;
            }
            else {
              ((Explosion)map[x][y]).draw(g);
            }
          }
        }
      }
      frameRate.draw(g,10,10);
      
      //request a repaint
      repaint();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        player1.setYDirection(-1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        player1.setXDirection(1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        player1.setYDirection(1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        player1.setXDirection(-1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("C")) {  //If 'C' is pressed
        /*Bomb newBomb = (player1.placeBomb(tileSize, xOffset, yOffset));
        map[(int)((newBomb.getX() - xOffset) / tileSize)][(int)((newBomb.getY() - yOffset) / tileSize)] = newBomb;*/
        int gridX = (int)Math.round((player1.getX() - xOffset) / tileSize);
        int gridY = (int)Math.round((player1.getY() - yOffset) / tileSize);
        if (map[gridX][gridY] instanceof Obstruction == false) {
          //System.out.println(clock.getElapsedTime());
          map[gridX][gridY] = player1.placeBomb(gridX, gridY);
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //If 'W' is pressed
        player2.setYDirection(-1);
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //If 'D' is pressed
        player2.setXDirection(1);
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //If 'S' is pressed
        player2.setYDirection(1);
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //If 'A' is pressed
        player2.setXDirection(-1);
      }
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        gameFrame.dispose();
      }
    }
    
    public void whereCrash(){
      boolean noWall=true;
      for (int x = 0; x < mapSize; x++) {
        for (int y = 0; y < mapSize; y++) {
          if (map[x][y] instanceof Wall) {
            if ((player1.getRightBox()).intersects(((Wall)map[x][y]).boundingBox)){
              player1.setWall1(2);
              noWall=false;
            }
            if ((player1.getUpBox()).intersects(((Wall)map[x][y]).boundingBox)){
              player1.setWall2(1);
              noWall=false;
            }
            if ((player1.getLeftBox()).intersects(((Wall)map[x][y]).boundingBox)){
              player1.setWall1(1);
              noWall=false;
            }
            if ((player1.getDownBox()).intersects(((Wall)map[x][y]).boundingBox)){
              player1.setWall2(2);
              noWall=false;
            }
          }
        }
      }
      if(noWall==true){
        player1.setWall1(0);
        player1.setWall2(0);
      }
    }
    
    public void keyReleased(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        player1.setYDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        player1.setXDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        player1.setYDirection(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        player1.setXDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //If 'W' is pressed
        player2.setYDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //If 'D' is pressed
        player2.setXDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //If 'S' is pressed
        player2.setYDirection(0);
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //If 'A' is pressed
        player2.setXDirection(0);
      }
    }
  }
  
// -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
//private class MyKeyListener implements KeyListener { } //end of keyboard listener
  
// -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      System.out.println("Mouse Clicked");
      System.out.println("X:"+e.getX() + " y:"+e.getY());
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
  } //end of mouselistener
  
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
  
  //Move player
  
  
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
