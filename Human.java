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
  
  public Rectangle upBox;
  public Rectangle downBox;
  public Rectangle leftBox;
  public Rectangle rightBox;

  public double xPosition, yPosition;
  public int xDirection, yDirection;
  public int axis;
  public double speed;
  public int wallWhere1;
  public int wallWhere2;

  Human(int x, int y, int width, int height/*int newHealth, double newX, double newY*/) {
  //  super(newHealth, newX, newY);
    bombCap = 1;
    kickable = false;
    throwable = false;
    crateCap = 0;

    xPosition = x;
    yPosition = y;
    xDirection = 0;
    yDirection = 0;
    speed = 1;
    rightBox = new Rectangle(((int)xPosition)+25, (int)yPosition, 1, 25);
    leftBox = new Rectangle(((int)xPosition), (int)yPosition, 1, 25);
    upBox = new Rectangle((int)xPosition, ((int)yPosition), 25, 1);
    downBox = new Rectangle((int)xPosition, ((int)yPosition)+25, 25, 1);
  }

  public void draw(Graphics g) {
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 25, 25); //notice the y is a variable that we control from our animate method
    g.setColor(Color.BLUE);
    g.fillRect(((int)xPosition)+25, (int)yPosition, 1, 25);
    g.fillRect(((int)xPosition), (int)yPosition, 1, 25);
    g.fillRect((int)xPosition, ((int)yPosition)+25, 25, 1);
    g.fillRect((int)xPosition, ((int)yPosition), 25, 1);
  }

  public void moveRight(double elapsedTime) {
    this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    rightBox.x=((int)xPosition)+25;
  }
  
  public void moveUp(double elapsedTime) {
    this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
    rightBox.y=(int)yPosition;
  }
  
  public void moveDown(double elapsedTime) {
    this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
    rightBox.y=(int)yPosition;
  }
  
  public void moveLeft(double elapsedTime) {
    this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    rightBox.x=((int)xPosition)+25;
  }
}
