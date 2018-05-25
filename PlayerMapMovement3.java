//Moving on a Map/Moving a map around a player without a tile map (just an image)

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
class PlayerMapMovement3 { 
  
  public static void main(String[] args) { 
    GameWindow game= new GameWindow();  
  }
  
}

//This class represents the game window
class GameWindow extends JFrame { 
  
  //Window constructor
  public GameWindow() { 
    setTitle("Map - movement over image Example");
    //setSize(1280,1024);  // set the size of my window to 400 by 400 pixels
    setResizable(true);  // set my window to allow the user to resize it
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    getContentPane().add( new GamePanel());
    pack(); //makes the frame fit the contents
    setVisible(true);
  }
  
  
  
// An inner class representing the panel on which the game takes place
  static class GamePanel extends JPanel implements KeyListener{
    
    MovingBox box;
    FrameRate frameRate;
    Clock clock;
    Map map;  
    
    //constructor
    public GamePanel() { 
      setPreferredSize(new Dimension(1024,768));
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
      frameRate = new FrameRate();
      clock=new Clock();
      map = new Map(1024,768);
      //box = new MovingBox(map);
    }
    
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g); //required to ensure the panel si correctly redrawn
      //update the content
      //draw the screen
      map.draw(g);
      //request a repaint
     
      repaint();
    }
    
    public void keyTyped(KeyEvent e) {      
      if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
        System.out.println("left");
        map.playerX+=5;
      } else if(e.getKeyChar() == 's' ){
        System.out.println("down");
        map.playerY-=5;
      } else if(e.getKeyChar() == 'd' ){
        System.out.println("right");
        map.playerX-=5;
      } else if(e.getKeyChar() == 'w' ){
        System.out.println("up");
        map.playerY+=5;
      }
    }
    
    public void keyPressed(KeyEvent e) {
      
    }
    
    public void keyReleased(KeyEvent e) {            
    }  
    
  }
  
}


class Map { 
  BufferedImage floor=null;
  int playerX=0;
  int playerY=0;
  
  public Map(int xResolution,int yResolution) { 
    try {
      floor = ImageIO.read(new File("map.png"));
      playerX=floor.getWidth()/2;  //place the player in the center of the map
      playerY=floor.getHeight()/2;
    } catch(Exception E) { 
      System.out.println("error loading map!");
    }
  }
  
  public void draw(Graphics g) { 
    
    g.drawImage(floor,playerX-floor.getWidth(),playerY-floor.getHeight(),null);
    
  }
  
}

