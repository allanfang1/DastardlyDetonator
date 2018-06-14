import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.io.File;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Explosion
 * Represents the actual explosion of the bomb.
 */
class Explosion extends Obstruction {
  private int range;
  private int gridX, gridY;
  private int xOffset, yOffset, tileSize;
  private double fadeTime;
  private boolean fade;
  private BufferedImage image;
  
  Explosion(int x, int y, int setTileSize, int setXOffset, int setYOffset) {
    super((x * setTileSize) + setXOffset, (y * setTileSize) + setYOffset);
    this.tileSize = setTileSize;
    this.xOffset = setXOffset;
    this.yOffset = setYOffset;
    this.gridX = x;
    this.gridY = y;
    //Fade out after 1 second
    this.fadeTime = (System.nanoTime() / 1.0E9) + 1;
    //Try loading sprite
    try {
      image = ImageIO.read(new File("img/explosion.png"));
    } catch(Exception e){
      System.out.println("Error loading img/explosion.png");
    }
  }
  
  public Obstruction[][] spread(int range, int xDirection, int yDirection, Obstruction[][] map) {
    Obstruction[][] tempMap = map;
    if (range > 0) {
      //Only explode in empty spaces and powerups
      if ((tempMap[gridX + xDirection][gridY + yDirection] instanceof Obstruction == false) || (tempMap[gridX + xDirection][gridY + yDirection] instanceof Powerup)) {
        tempMap[gridX + xDirection][gridY + yDirection] = new Explosion(this.gridX + xDirection, this.gridY + yDirection, this.tileSize, this.xOffset, this.yOffset);
        return ((Explosion)(tempMap[gridX + xDirection][gridY + yDirection])).spread(range - 1, xDirection, yDirection, tempMap);
      }
      //Check if it is a crate
      else if (tempMap[gridX + xDirection][gridY + yDirection]  instanceof Crate) {
        Random rand = new Random();
        if (rand.nextInt(4) == 0) { //25% chance to spawn a powerup
          tempMap[gridX + xDirection][gridY + yDirection] = new Powerup(rand.nextInt(4), gridX + xDirection, gridY + yDirection);
          return tempMap;
        }
        else {
          tempMap[gridX + xDirection][gridY + yDirection] = new Explosion(this.gridX + xDirection, this.gridY + yDirection, this.tileSize, this.xOffset, this.yOffset);
          return ((Explosion)(tempMap[gridX + xDirection][gridY + yDirection])).spread(range - 1, xDirection, yDirection, tempMap);
        }
      }
    }
    return tempMap;
  }
  /**
   * getRange
   * This method returns the range of this explosion.
   * @return The range of the blast.
   */
  int getRange() {
    return this.range;
  }
  
  /**
   * setRange
   * This method sets the range of this explosion.
   * @param The integer value to set this blast/s range to.
   */
  void setRange(int newRange) {
    this.range = newRange;
  }
  
  void update() {
    double currentTime = (System.nanoTime() / 1.0E9);  //Returns time in seconds (nanoseconds --> seconds)
    if (currentTime > fadeTime) {
      this.fade = true;
    }
  }
  
  boolean shouldFade() {
    return this.fade;
  }
  
  public void draw(Graphics g) {
    try {
        g.drawImage(image, this.getX(), this.getY(), null);
    //If it failed to load the sprite
    } catch(Exception e){
        g.setColor(Color.RED);
        g.fillRect((int)(this.getX()), (int)(this.getY()), height, width);
    }
  }
}