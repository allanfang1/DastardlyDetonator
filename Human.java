/**
 * Human object
 */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

class Human extends Thread{
  private int bombCap;
  private int blastRange = 1;
  private int health = 3;
  private int speed;
  
  private int height = 25;
  private int width = 25;
  
  private Rectangle upBox;
  private Rectangle downBox;
  private Rectangle leftBox;
  private Rectangle rightBox;
  
  private int xPosition, yPosition;
  private int xDirection, yDirection;
  private int gridX, gridY;
  private int axis;
  private int wallWhere1;
  private int wallWhere2;
  
  private boolean delayed=true;
  
  Human(int x, int y, int length/*int newHealth, double newX, double newY*/) {
    //  super(newHealth, newX, newY);
    this.setHealth(3);
    this.bombCap = 1;
    this.blastRange = 1;
    this.xPosition = x;
    this.yPosition = y;
    this.xDirection = 0;
    this.yDirection = 0;
    this.speed = 1;
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
  public int getX() {
    return this.xPosition;
  }
  
  /**
   * setX
   * This method sets the X-position of this object.
   * @param The value to set this object's X-position to.
   */
  public void setX(int newX) {
    this.xPosition = newX;
  }
  
  /**
   * getY
   * This method returns this object's Y-position.
   * @return The Y-position of this object.
   */
  public int getY() {
    return this.yPosition;
  }
  
  /**
   * setY
   * This method sets the Y-position of this object.
   * @param The value to set this object's Y-position to.
   */
  public void setY(int newY) {
    this.yPosition = newY;
  }
  
  /**
   * getHealth
   * This method returns this object's health.
   * @return The integer health of this object.
   */
  public int getHealth() {
    return this.health;
  }
  
  /**
   * setHealth
   * This method sets the health of this object.
   * @param The integer value to set this object's health to.
   */
  public void setHealth(int newHealth) {
    this.health = newHealth;
  }
  
  /**
   * getSpeed
   * This method returns this object's speed.
   * @return The integer speed of this object.
   */
  public double getSpeed() {
    return this.speed;
  }
  
  /**
   * setSpeed
   * This method sets the speed of this object.
   * @param The integer value to set this object's speed to.
   */
  public void setSpeed(int newSpeed) {
    this.speed = newSpeed;
  }
  
  /**
   * move
   * This method moves the object.
   * @param The elapsed time from the last screen refresh to this screen refresh.
   */
  public void move(Obstruction[][] map) {
    //this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    //this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
    Thread myCounter = new Thread(this);
    int tempX = this.xPosition + this.xDirection;
    int tempY = this.yPosition + this.yDirection;
    //Check X direction
    if ((map[tempX][this.yPosition] instanceof Wall == false) && (map[tempX][this.yPosition] instanceof Crate == false) && (map[tempX][this.yPosition] instanceof Bomb == false) && (delayed==true)) {
      this.xPosition += this.xDirection;
      new Thread(this).start();
    }
    //Check Y direction
    if ((map[this.xPosition][tempY] instanceof Wall == false) && (map[this.xPosition][tempY] instanceof Crate == false) && (map[this.xPosition][tempY] instanceof Bomb == false) && (delayed==true)) {
      this.yPosition += this.yDirection;
      new Thread(this).start();
    }
  }
  
  public void run(){
    delayed=false;
    try {
      Thread.sleep(500 - (25 * this.speed));
    } catch (InterruptedException e) {}
    delayed=true;
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
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition * 32 + 64, (int)yPosition * 32 + 64, 32, 32); //notice the y is a variable that we control from our animate method
  }
  
  /*public void moveX(double elapsedTime) {
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
   }*/
  
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
  
  public Bomb placeBomb() {
    return (new Bomb(this.blastRange, 3, this.xPosition, this.yPosition));
  }
}
