/**
 * Human object
 */
import java.awt.Graphics;
import java.awt.Color;

class Human extends Thread{
  private int maxBombs;
  private int currentBombs = 0;
  private int blastRange = 1;
  private int speed;
  private int health;
  
  private int xPosition, yPosition;
  private int xDirection, yDirection;
  
  private boolean delayed=true;
  
  Human(int x, int y) {
    this.setHealth(3);
    this.maxBombs = 1;
    this.blastRange = 1;
    this.xPosition = x;
    this.yPosition = y;
    this.xDirection = 0;
    this.yDirection = 0;
    this.speed = 0;
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
<<<<<<< HEAD
=======
   * setSpeed
   * This method sets the speed of this object.
   * @param The integer value to set this object's speed to.
   */
  public void setSpeed(int newSpeed) {
    this.speed = newSpeed;
  }
  
  /**
   * getBombs
   * This method returns this object's number of current bombs.
   * @return The number of bombs this object owns.
   */
  public int getBombs() {
    return this.currentBombs;
  }
  
  /**
   * setBombs
   * This method sets this object's number of current bombs.
   * @param The integer value to set this object's number of current bombs to.
   */
  public void setBombs(int newBombs) {
    this.currentBombs = newBombs;
  }
  
  /**
   * move
   * This method moves the object.
   * @param The elapsed time from the last screen refresh to this screen refresh.
   */
  public void move(Obstruction[][] map) {
    int tempX = this.xPosition + this.xDirection;
    int tempY = this.yPosition + this.yDirection;
    //Check X direction
    if ((this.xDirection!=0) && (map[tempX][this.yPosition] instanceof Wall == false) && (map[tempX][this.yPosition] instanceof Crate == false) && (map[tempX][this.yPosition] instanceof Bomb == false) && (delayed==true)) {
      this.xPosition += this.xDirection;
      new Thread(this).start();
    }
    //Check Y direction
    if ((this.yDirection!=0) && (map[this.xPosition][tempY] instanceof Wall == false) && (map[this.xPosition][tempY] instanceof Crate == false) && (map[this.xPosition][tempY] instanceof Bomb == false) && (delayed==true)) {
      this.yPosition += this.yDirection;
      new Thread(this).start();
    }
  }
  
  public void run(){
    delayed=false;
    try {
      System.out.println(this.speed);
      Thread.sleep(500 - (25 * (this.speed - 1)));
    } catch (InterruptedException e) {}
    delayed=true;
  }
  
  public void addSpeed() {
    if (this.speed < 10) {
      this.speed++;
    }
  }
  
  public void addBombs() {
    if (this.maxBombs < 10) {
      this.maxBombs++;
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
  
  public void draw(Graphics g) {
    g.setColor(Color.RED); //There are many graphics commands that Java can use
    g.fillRect((int)xPosition * 32 + 64, (int)yPosition * 32 + 64, 32, 32); //notice the y is a variable that we control from our animate method
  }
    
  public Bomb placeBomb(int owner) {
    //Make sure player does not exceed maximum number of 
    if (this.currentBombs < this.maxBombs) {
      currentBombs++;
      return (new Bomb(this.blastRange, 3, this.xPosition, this.yPosition, owner));
    }
    return null;
  }
}
