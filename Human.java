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
  int blastRange = 1;
  
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
  
  Human(int x, int y, int length/*int newHealth, double newX, double newY*/) {
    //  super(newHealth, newX, newY);
    bombCap = 1;
    kickable = false;
    throwable = false;
    crateCap = 0;
    
    xPosition = x;
    yPosition = y;
    xDirection = 0;
    yDirection = 0;
    speed = 2;
    this.height = length;
    this.width = length;
    rightBox = new Rectangle(((int)xPosition)+23, ((int)yPosition)+1, 1, length);//23
    leftBox = new Rectangle(((int)xPosition)-1, ((int)yPosition)+1, 1, length);
    upBox = new Rectangle(((int)xPosition), ((int)yPosition), length, 1);
    downBox = new Rectangle(((int)xPosition), ((int)yPosition)+23, length, 1);
  }
  
  /**
   * getX
   * This method returns this object's X-position.
   * @return The X-position of this object.
   */
  public double getX() {
    return this.xPosition;
  }

  /**
   * setX
   * This method sets the X-position of this object.
   * @param The value to set this object's X-position to.
   */
  public void setX(double newX) {
    this.xPosition = newX;
  }

  /**
   * getY
   * This method returns this object's Y-position.
   * @return The Y-position of this object.
   */
  public double getY() {
    return this.yPosition;
  }

  /**
   * setY
   * This method sets the Y-position of this object.
   * @param The value to set this object's Y-position to.
   */
  public void setY(double newY) {
    this.yPosition = newY;
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 23, 23); //notice the y is a variable that we control from our animate method
    g.setColor(Color.BLUE);
    g.fillRect(((int)xPosition)+23, ((int)yPosition)+1, 1, 22);
    g.fillRect(((int)xPosition)-1, ((int)yPosition)+1, 1, 22);
    g.fillRect(((int)xPosition), (int)yPosition, 22, 1);
    g.fillRect(((int)xPosition), ((int)yPosition)+23, 22, 1);
  }
  
  public void moveX(double elapsedTime) {
    this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    rightBox.x=((int)xPosition)+23;
    leftBox.x=((int)xPosition)-1;
    upBox.x=((int)xPosition);
    downBox.x=((int)xPosition);
  }
  
  public void moveY(double elapsedTime) {
    this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
    rightBox.y=((int)yPosition)+1;
    leftBox.y=((int)yPosition)+1;
    upBox.y=(int)yPosition;
    downBox.y=((int)yPosition)+23;
  }
  
  public Bomb placeBomb(int x, int y, double elapsedTime) {
    return (new Bomb(this.blastRange, 3, x, y, elapsedTime));
  }
}
