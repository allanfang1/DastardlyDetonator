/** 
 * [Human.java] 
 * The player characters
 * June 14 2018 
 */ 

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Human
 * Represents a player in the game.
 */
class Human extends Thread{
  private int maxBombs;
  private int currentBombs = 0;
  private int blastRange = 1;
  private int speed;
  private int health;
  private int player;
  private BufferedImage image;
  
  private int xPosition, yPosition;
  private int xDirection, yDirection;
  
  private boolean delayed=true;
  
  Human(int x, int y, int playerNum) {
    this.setHealth(3);
    this.maxBombs = 1;
    this.blastRange = 1;
    this.xPosition = x;
    this.yPosition = y;
    this.xDirection = 0;
    this.yDirection = 0;
    this.speed = 0;
    this.player = playerNum;
    try {
      image = ImageIO.read(new File("img/player" + this.player + ".png"));
    } catch(Exception e){
      System.out.println("Error loading img/player" + this.player + ".png");
    }
    
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
   * @param the current condition of the map.
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
  
  /**
   * run
   * allows movements after time delay
   */
  public void run(){
    delayed=false;
    try {
      Thread.sleep(500 - (60 * (this.speed - 1)));
    } catch (InterruptedException e) {}
    delayed=true;
  }
  
  /**
   * addSpeed
   * Increase speed
   */
  public void addSpeed() {
    if (this.speed < 6) {
      this.speed++;
    }
  }
  
  /**
   * addBombs
   * increase bomb cap
   */
  public void addBombs() {
    if (this.maxBombs < 10) {
      this.maxBombs++;
    }
  }
  
  /**
   * addRange
   * Increases player bomb radius
   */
  public void addRange() {
    if (this.blastRange < 10) {
      this.blastRange++;
    }
  }
  
  /**
   * getXDirection
   * @return give the x movement
   */
  public int getXDirection() {
    return xDirection;
  }
  
  /**
   * getYDirection
   * @return give the y movement
   */
  public int getYDirection() {
    return yDirection;
  }
  
  /**
   * setXDirection
   * @param set x movement
   */
  public void setXDirection(int direction) {
    this.xDirection = direction;
  }
  
  /**
   * getYDirection
   * @param set the y movement
   */
  public void setYDirection(int direction) {
    this.yDirection = direction;
  }
  
  public void draw(Graphics g) {
    try {
      g.drawImage(image, this.xPosition * 32 + 64, this.yPosition * 32 + 64, null);
      //If it failed to load the sprite
    } catch(Exception e){
      g.setColor(Color.RED);
      g.fillRect(this.xPosition * 32 + 64, this.yPosition * 32 + 64, 32, 32);
    }
  }
   
  /**
   * placeBomb
   * @param player that owns the bomb
   * @return generate new bomb
   */
  public Bomb placeBomb(int owner) {
    //Make sure player does not exceed maximum number of 
    if (this.currentBombs < this.maxBombs) {
      currentBombs++;
      return (new Bomb(this.blastRange, 3, this.xPosition, this.yPosition, owner));
    }
    return null;
  }
}
