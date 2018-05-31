//Moving on a Map/Moving a map around a player
//using file I/O to load a large map

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;

/*An Example demonstrating a simple game loop
 * 
 * This version includes time.
 * The old version moves the object across the screen based on frame rate (calls to repaint)
 * This version will move the object based on elapsed time to make it consistent regardless of the framRate
 * We will make a new 'Clock' class to track time
 * 
 * @Author Mangat
 */

//This class is used to start the program and manage the windows
class SimpleCollisionDetection { 
  
  public static void main(String[] args) { 
    GameWindow game= new GameWindow();  
  }
  
}

//This class represents the game window
class GameWindow extends JFrame { 
  
  //Window constructor
  public GameWindow() { 
    setTitle("Simple Collision Example");
    //setSize(1280,1024);  // set the size of my window to 400 by 400 pixels
    setResizable(true);  // set my window to allow the user to resize it
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    
    getContentPane().add(new GamePanel());
    pack(); //makes the frame fit the contents
    setVisible(true);
  }
  
  
  
// An inner class representing the panel on which the game takes place
  static class GamePanel extends JPanel {
    
    MovingBox box, box2;
    FrameRate frameRate;
    Clock clock;
    
    //constructor
    public GamePanel() { 
      setPreferredSize(new Dimension(1024,768));
      frameRate = new FrameRate();
      box = new MovingBox(0, 50, 25, 25);
      box2 = new MovingBox(900, 50, 75, 75);
      clock=new Clock();
    }
    
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g); //required to ensure the panel si correctly redrawn
      
      
      
      //update the content
      clock.update();
      frameRate.update();
      box.update(clock.getElapsedTime()); 
      box2.update(clock.getElapsedTime());
      
      //check for collision
      if (box.boundingBox.intersects(box2.boundingBox))
        System.out.println("Collide!");
      
      //draw the screen
      box.draw(g);
      box2.draw(g);
      frameRate.draw(g,10,10);
      
      //request a repaint
      repaint();
    }
    
    
  }
  
}

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
  double xPosition,yPosition;
  double xSpeed;
  int height,width;
  Rectangle boundingBox; //rectangle that is used for collision detection
  
  public MovingBox(int x, int y, int w, int h) {
    xPosition = x;
    yPosition = y;
    width=w;
    height=h;
    xSpeed=1;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }
  
  public void update(double elapsedTime){
    //update the content
    if (xPosition<0) xSpeed=1;
    else if (xPosition>1000) xSpeed=-1;
    xPosition= xPosition+xSpeed*elapsedTime*100;  //d = d0 + vt
    boundingBox.x=(int)xPosition;
    //System.out.println(elapsedTime*10+"\n");
  }
  
  public void draw(Graphics g) { 
    g.setColor(Color.BLUE); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, width, height); //notice the y is a variable that we control from our animate method          
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