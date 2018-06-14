/** 
 * [Obstruction.java] 
 * Objects that prevent player movement
 * June 14 2018 
 */ 

abstract class Obstruction {
  //Coordinates of this obstruction
  public int xPosition, yPosition;
  int height=32;
  int width=32;
  
  Obstruction(int x, int y) {
    this.xPosition = x;
    this.yPosition = y;
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
}