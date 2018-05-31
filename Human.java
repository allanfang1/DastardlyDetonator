/**
 * Human object
 */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

class Human extends Living{
  int bombCap;
  boolean kickable;
  boolean throwable;
  int crateCap;

  int height = 25;
  int width = 25;
  Rectangle boundingBox;

  public double xPosition, yPosition;
  public int xDirection, yDirection;
  public double speed;

  Human(/*int newHealth, double newX, double newY*/) {
  //  super(newHealth, newX, newY);
    bombCap = 1;
    kickable = false;
    throwable = false;
    crateCap = 0;

    xPosition = 0;
    yPosition = 50;
    xDirection = 0;
    yDirection = 0;
    speed = 1;
    boundingBox = new Rectangle((int)xPosition, (int)yPosition, width, height);
  }

 /*public void update(double elapsedTime){
   //update the content
   if (xPosition<0) xSpeed=1;
   else if (xPosition>1000) xSpeed=-1;
   xPosition = xPosition + xSpeed * elapsedTime * 100;  //d = d0 + vt
   //System.out.println(elapsedTime*10+"\n");
   }*/

  public void draw(Graphics g) {
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
  }

  public void move(double elapsedTime) {
    this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
  }

  public Rectangle hitbox() {
    return this.boundingBox;
  }
}
