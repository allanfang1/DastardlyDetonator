/**
 * Wall
 * Represents an unpassable wall block.
 */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

class Wall extends Obstruction {
  int height=25;
  int width=25;
  Rectangle boundingBox;
  
  Wall(){
    super.xPosition = 50;
    super.yPosition = 50;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.BLUE); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }
}