/**
 * Explosion
 * Represents the actual explosion of the bomb.
 */
class Explosion extends Obstruction {
  private int range;
  
  Explosion() {
    this.range = 0;
  }
  
  Explosion(int setRange) {
    this.range = setRange;
  }
  
  /**
   * getRange
   * This method returns the range of this explosion.
   * @return The range of the blast.
   */
  int getRange() {
    return this.range;
  }
  
  /**
   * setRange
   * This method sets the range of this explosion.
   * @param The integer value to set this blast/s range to.
   */
  void setRange(int newRange) {
    this.range = newRange;
  }
}