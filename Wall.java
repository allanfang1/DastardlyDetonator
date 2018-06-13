/**
 * Wall
 * Represents an unpassable wall block.
 */
import java.awt.Graphics;
import java.awt.Color;

class Wall extends Obstruction {

  Wall(int x, int y){
    super(x, y);
  }

  public void draw(Graphics g) {
    g.setColor(Color.BLUE); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 32, 32); //notice the y is a variable that we control from our animate method
  }
}
