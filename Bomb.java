/** 
 * [Bomb.java] 
 * A bomb placed by human
 * June 14 2018 
 */ 

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class Bomb extends Obstruction {
  private int blastRange;
  private double explodeTime;
  private boolean explode = false;
  private int owner;
  BufferedImage image;
  
  Bomb(int range, int fuse, int gridX, int gridY, int setOwner) {
    super((gridX * 32) + 64, (gridY * 32) + 64);
    this.blastRange = range;
    this.explodeTime = (System.nanoTime() / 1.0E9) + fuse;
    this.owner = setOwner;
    //Try loading sprite
    try {
      image = ImageIO.read(new File("img/bomb.png"));
    } catch(Exception e){
      System.out.println("Error loading img/bomb.png");
    }
  }
  
  /**
   * update
   * explode bomb at the right time
   */ 
  void update() {
    double currentTime = (System.nanoTime() / 1.0E9);  //if the computer is fast you need more precision
    if (currentTime > explodeTime) {
      this.explode = true;
      this.explode();
    }
  }
  
  /**
   * hasExploded
   * @return explode, tells if the bomb has exploded or not
   */ 
  boolean hasExploded() {
    return explode;
  }
  
  /**
   * explode
   * set exploded as true
   */ 
  void explode() {
    this.explode = true;
  }
  
  /**
   * getTime
   * @return explodeTime, return value of time that bomb is set to explode at
   */ 
  double getTime() {
    return explodeTime;
  }
  
  /**
   * getRange
   * @return blastRange, gets integer value of how many tiles bomb reaches
   */ 
  int getRange() { 
    return blastRange;
  }
  
  /**
   * getOwner
   * @return owner, tells which player's bomb it is
   */ 
  int getOwner() {
    return owner;
  }
  
  /**
   * draw
   * @param g, graphics
   */ 
  public void draw(Graphics g) {
    try {
      g.drawImage(image, this.getX(), this.getY(), null);
      //If it failed to load the sprite
    } catch(Exception e){
      g.setColor(Color.BLACK);
      g.fillRect((int)(this.getX()), (int)(this.getY()), height, width);
    }
  }
}