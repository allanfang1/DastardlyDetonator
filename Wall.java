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
    this.setX(50);
    this.setY(50);
    boundingBox=new Rectangle((int)(this.getX()), (int)(this.getY()), width, height);
  }

  Wall(int x, int y){
    this.setX(x);
    this.setY(y);
    boundingBox=new Rectangle((int)(this.getX()), (int)(this.getY()), width, height);
  }

  public void draw(Graphics g) {
    g.setColor(Color.BLUE); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 25, 25); //notice the y is a variable that we control from our animate method
  }
}
