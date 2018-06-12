/**
 * Human object
 */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

class Human extends Living{
  private int bombCap;
  private boolean kickable;
  private boolean throwable;
  private int crateCap;
  private int blastRange = 1;
  
  private int height = 25;
  private int width = 25;
  
  private Rectangle upBox;
  private Rectangle downBox;
  private Rectangle leftBox;
  private Rectangle rightBox;
  
  private double xPosition, yPosition;
  private int xDirection, yDirection;
  private int axis;
  private double speed;
  private int wallWhere1;
  private int wallWhere2;
  
  Human(int x, int y, int length/*int newHealth, double newX, double newY*/) {
    //  super(newHealth, newX, newY);
    this.setHealth(3);
    this.bombCap = 1;
    this.kickable = false;
    this.throwable = false;
    this.crateCap = 0;
    this.blastRange = 1;
    this.xPosition = x;
    this.yPosition = y;
    this.xDirection = 0;
    this.yDirection = 0;
    this.speed = 2;
    this.height = length;
    this.width = length;
    this.rightBox = new Rectangle(((int)xPosition)+23, ((int)yPosition)+1, 1, length);//22
    this.leftBox = new Rectangle(((int)xPosition)-1, ((int)yPosition)+1, 1, length);
    this.upBox = new Rectangle(((int)xPosition), ((int)yPosition), length+1, 1);
    this.downBox = new Rectangle(((int)xPosition), ((int)yPosition)+23, length+1, 1);
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
  
  public void addSpeed() {
    if (this.speed < 10) {
      this.speed++;
    }
  }
  
  public void addBombs() {
    if (this.bombCap < 10) {
      this.bombCap++;
    }
  }
  
  public void addRange() {
    if (this.blastRange < 10) {
      this.blastRange++;
    }
  }
  
  public void addHealth() {
    if (this.getHealth() < 3) {
      this.setHealth(this.getHealth() + 1);
    }
  }
  
  public int getXDirection() {
    return xDirection;
  }
  
  public int getYDirection() {
    return yDirection;
  }
  
  public void setXDirection(int direction) {
    this.xDirection = direction;
  }
  
  public void setYDirection(int direction) {
    this.yDirection = direction;
  }
  
  public Rectangle getRightBox() {
    return rightBox;
  }
  
  public Rectangle getLeftBox() {
    return leftBox;
  }
  
  public Rectangle getUpBox() {
    return upBox;
  }
  
  public Rectangle getDownBox() {
    return downBox;
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.WHITE); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition, (int)yPosition, 23, 23); //notice the y is a variable that we control from our animate method
    g.setColor(Color.BLUE);
    g.fillRect(((int)xPosition)+23, ((int)yPosition)+1, 1, 22);
    g.fillRect(((int)xPosition)-1, ((int)yPosition)+1, 1, 22);
    g.fillRect(((int)xPosition), (int)yPosition, 23, 1);
    g.fillRect(((int)xPosition), ((int)yPosition)+23, 23, 1);
    g.setColor(Color.RED);
    g.fillRect(((int)xPosition)+23, ((int)yPosition), 1, 1); //top right
    g.fillRect(((int)xPosition)-1, ((int)yPosition), 1, 1); //TOP LEFT
    g.fillRect(((int)xPosition)+23, (int)yPosition+23, 1, 1); //BOTTOM right
    g.fillRect(((int)xPosition)-1, ((int)yPosition)+23, 1, 1); //BOTTOM LEFT
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
  
  public int getWall1() {
    return wallWhere1;
  }
  
  public int getWall2() {
    return wallWhere2;
  }
  
  public void setWall1(int setWall) {
    this.wallWhere1 = setWall;
  }
  
  public void setWall2(int setWall) {
    this.wallWhere2 = setWall;
  }
  
  public Bomb placeBomb(int x, int y) {
    return (new Bomb(this.blastRange, 3, x, y));
  }
}
