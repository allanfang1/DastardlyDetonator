/**
 * Obstruction
 * Represents an unpassable block.
 * 
 */
abstract class Obstruction {
  //Coordinates of this obstruction
  private double xPosition, yPosition;
   
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

}