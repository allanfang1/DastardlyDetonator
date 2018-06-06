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
  
  Bomb() {
    this.blastRange = 0;
    this.explodeTime = 0;
  }
  
  Bomb(int range, int fuse, int gridX, int gridY) {
    this.blastRange = range;
    this.xPosition = (gridX * 25) + 50;
    this.yPosition = (gridY * 25) + 50;
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
    System.out.println("boom");
  }
  
  double getTime() {
    return explodeTime;
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.BLACK); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }
  
}