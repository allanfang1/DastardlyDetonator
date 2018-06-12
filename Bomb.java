import java.awt.Graphics;
import java.awt.Color;

/**
 * Bomb
 * Represents a bomb placed by a human.
 */
class Bomb extends Obstruction {
  private int blastRange;
  private double explodeTime;
  private boolean explode = false;
  
  Bomb(int range, int fuse, int gridX, int gridY) {
    super((gridX * 25) + 50, (gridY * 25) + 50);
    this.blastRange = range;
    this.explodeTime = (System.nanoTime() / 1.0E9) + fuse;
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
  
  public void draw(Graphics g) {
    g.setColor(Color.BLACK); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 25, 25); //notice the y is a variable that we control from our animate method
  }
  
}