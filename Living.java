/**
 * Living
 */

abstract public class Living{
  private int health;
  private double xPosition, yPosition;
  private int xDirection = 0;
  private int yDirection = 0;
  private double speed = 1;

  Living() {
    this.health = 0;
    this.xPosition = 0;
    this.yPosition = 0;
  }

  Living(int newHealth, double newX, double newY) {
    this.health = newHealth;
    this.xPosition = newX;
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
  public void setSpeed(double newSpeed) {
    this.speed = newSpeed;
  }

  /**
   * move
   * This method moves the object.
   * @param The elapsed time from the last screen refresh to this screen refresh.
   */
  public void move(double elapsedTime) {
    this.xPosition += (this.xDirection * this.speed * elapsedTime * 100);
    this.yPosition += (this.yDirection * this.speed * elapsedTime * 100);
  }

}
