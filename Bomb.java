import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Bomb
 * Represents a bomb placed by a human.
 */
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
  
  void update() {
    double currentTime = (System.nanoTime() / 1.0E9);  //if the computer is fast you need more precision
    if (currentTime > explodeTime) {
      this.explode = true;
      this.explode();
    }
  }
  
  boolean hasExploded() {
    return explode;
  }
  
  void explode() {
    this.explode = true;
  }
  
  double getTime() {
    return explodeTime;
  }
  
  int getRange() { 
    return blastRange;
  }
  
  int getOwner() {
    return owner;
  }
  
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