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

  //class variable (non-static)
  static GameAreaPanel gamePanel;


  //Constructor - this runs first
  GameFrame() {
    super("My Game");
    // Set the frame to full screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);

    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());

    /*MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);

    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    */

    this.requestFocusInWindow(); //make sure the frame has focus

    this.setVisible(true);

    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();

  } //End of Constructor

  //the main gameloop - this is where the game state is updated
  public void animate() {
    while(true){
      /*this.x = (Math.random()*1024);  //update coords
      this.y = (Math.random()*768);
      try{ Thread.sleep(500);} catch (Exception exc){}  //delay*/
      this.repaint();
    }
  }

  /** --------- INNER CLASSES ------------- **/

  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel implements KeyListener{

    MovingBox box;
    FrameRate frameRate;
    Clock clock;

    GameAreaPanel () {
      frameRate = new FrameRate();
      box = new MovingBox();
      clock = new Clock();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g); //required
      setDoubleBuffered(true);
      g.setColor(Color.BLUE); //There are many graphics commands that Java can use
      //g.fillRect((int)x, (int)y, 50, 50); //notice the x,y variables that we control from our animate method

      //update the content
      clock.update();
      frameRate.update();
      box.update(clock.getElapsedTime());  //you can 'pause' the game by forcing elapsed time to zero

      //draw the screen
      box.draw(g);
      frameRate.draw(g,10,10);

      //request a repaint
      repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        System.out.println("W");
        box.move(0);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        System.out.println("D");
        box.move(1);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        System.out.println("S");
        box.move(2);
      }
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  //If 'A' is pressed
        System.out.println("A");
        box.move(3);
        }
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        System.out.println("Esc"); //close frame & quit
      }
    }


    public void keyReleased(KeyEvent e) {
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
class MovingBox {
  double xPosition;
  double yPosition;
  double xSpeed;

  public MovingBox() {
    xPosition = 0;
    yPosition = 50;
    xSpeed=1;
  }

  public void update(double elapsedTime){
    //update the content
    if (xPosition<0) xSpeed=1;
    else if (xPosition>1000) xSpeed=-1;
    xPosition = xPosition + xSpeed * elapsedTime * 100;  //d = d0 + vt
    //System.out.println(elapsedTime*10+"\n");
  }

  public void draw(Graphics g) {
    g.setColor(Color.BLUE); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }

  public void move(int direction) {
    if (direction == 0) {
      this.yPosition++;
    }
    else if (direction == 1) {
      this.xPosition++;
    }
    else if (direction == 2) {
      this.yPosition--;
    }
    else {
      this.xPosition--;
    }
  }
}

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
