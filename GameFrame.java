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

class GameFrame extends JFrame {

  static GameAreaPanel gamePanel;
  static JFrame gameFrame;
  
  GameFrame() {
    super("My Game");
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
    Wall wall;
    FrameRate frameRate;
    Clock clock;

    GameAreaPanel () {
      frameRate = new FrameRate();
      player1 = new Human();
      player2 = new Human();
      wall= new Wall();
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
      player1.move(clock.getElapsedTime());
      player2.draw(g);
      player2.move(clock.getElapsedTime());
      wall.draw(g);
      frameRate.draw(g,10,10);

      //request a repaint
      repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        System.out.println("Move up");
        player1.yDirection = -1;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        System.out.println("Move right");
        player1.xDirection = 1;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        System.out.println("Move down");
        player1.yDirection = 1;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        System.out.println("Move left");
        player1.xDirection = -1;
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //If 'W' is pressed
        System.out.println("Move up");
        player2.yDirection = -1;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //If 'D' is pressed
        System.out.println("Move right");
        player2.xDirection = 1;
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //If 'S' is pressed
        System.out.println("Move down");
        player2.yDirection = 1;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //If 'A' is pressed
        System.out.println("Move left");
        player2.xDirection = -1;
      }
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        gameFrame.dispose();
      }
    }


    public void keyReleased(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        System.out.println("Move up");
        player1.yDirection = 0;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        System.out.println("Move right");
        player1.xDirection = 0;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        System.out.println("Move down");
        player1.yDirection = 0;
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        System.out.println("Move left");
        player1.xDirection = 0;
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {  //If 'W' is pressed
        System.out.println("Move up");
        player2.yDirection = 0;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //If 'D' is pressed
        System.out.println("Move right");
        player2.xDirection = 0;
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {  //If 'S' is pressed
        System.out.println("Move down");
        player2.yDirection = 0;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {  //If 'A' is pressed
        System.out.println("Move left");
        player2.xDirection = 0;
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
