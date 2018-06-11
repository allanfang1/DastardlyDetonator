import java.awt.Graphics;
import java.awt.Color;

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
  
  Explosion() {
  }
  
  Explosion(int x, int y, int setTileSize, int setXOffset, int setYOffset) {
    this.tileSize = setTileSize;
    this.xOffset = setXOffset;
    this.yOffset = setYOffset;
    this.xPosition = ((x * tileSize) + xOffset);
    this.yPosition = ((y * tileSize) + yOffset);
    this.gridX = x;
    this.gridY = y;
    //Fade out after 1 second
    this.fadeTime = (System.nanoTime() / 1.0E9) + 1;
  }
  
  public Obstruction[][] spread(int range, int xDirection, int yDirection, Obstruction[][] map) {
    Obstruction[][] tempMap = map;
    if (range > 0) {
      if ((tempMap[gridX + xDirection][gridY + yDirection] instanceof Wall == false) && (tempMap[gridX + xDirection][gridY + yDirection] instanceof Bomb == false)){
        tempMap[gridX + xDirection][gridY + yDirection] = new Explosion(this.gridX + xDirection, this.gridY + yDirection, this.tileSize, this.xOffset, this.yOffset);
        return ((Explosion)(tempMap[gridX + xDirection][gridY + yDirection])).spread(range - 1, xDirection, yDirection, tempMap);
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
    double currentTime = (System.nanoTime() / 1.0E9);  //if the computer is fast you need more precision
    if (currentTime > fadeTime) {
      this.fade = true;
    }
  }
  
  boolean shouldFade() {
    return this.fade;
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }
  
}