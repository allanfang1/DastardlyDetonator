import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Crate
 * Represents an unpassable crate that can be destroyed.
 */
class Crate extends Obstruction {
  
  int height=25;
  int width=25;
  Rectangle boundingBox;

  Crate(){
    this.setX(50);
    super.setY(50);
    boundingBox=new Rectangle((int)(this.getX()), (int)(this.getY()), width, height);
  }

  Crate(int x, int y){
    this.setX(x);
    this.setY(y);
    boundingBox=new Rectangle((int)(this.getX()), (int)(this.getY()), width, height);
  }

  public void draw(Graphics g) {
    g.setColor(Color.GREEN); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 25, 25); //notice the y is a variable that we control from our animate method
  }
}