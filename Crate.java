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
    super.xPosition = 50;
    super.yPosition = 50;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }

  Crate(int x, int y){
    super.xPosition = x;
    super.yPosition = y;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }

  public void draw(Graphics g) {
    g.setColor(Color.GREEN); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }
}