import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Crate
 * Represents an unpassable crate that can be destroyed.
 */
class Crate extends Obstruction {
  
  int height=32;
  int width=32;
  Rectangle boundingBox;

  Crate(int x, int y){
    super(x, y);
  }

  public void draw(Graphics g) {
    g.setColor(Color.GREEN); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 32, 32); //notice the y is a variable that we control from our animate method
  }
}