import java.awt.Graphics;
import java.awt.Color;

/**
 * Bomb
 * Represents a bomb placed by a human.
 */
class Bomb extends Obstruction {
  private int blastRange;
  private int timeLeft;
  
  Bomb() {
    this.blastRange = 0;
    this.timeLeft = 0;
  }
  
  Bomb(int range, int time, int gridX, int gridY) {
    this.blastRange = range;
    this.timeLeft = time;
    this.xPosition = gridX * 25;
    this.yPosition = gridY * 25;
  }
  
  void explode() {
    //boom
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.BLACK); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }
  
}