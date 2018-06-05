class Powerup extends Obstruction {
  /* Powerup list:
   * 0 = none
   * 1 = speed
   * 2 = max bombs
   * 3 = explosion range
   * 4 = throw range
   * 5 = temporary crate
   */

  private int powerupID;

  Powerup() {
    this.powerupID = 0;
  }

  Powerup(int setPowerup) {
    this.powerupID = setPowerup;
  }

  /**
   * getPowerup
   * This method returns this powerup's ID.
   * @return The ID of this powerup.
   */
  int getPowerup() {
    return this.powerupID;
  }

  /**
   * setPowerup
   * This method sets the ID of this powerup.
   * @param The ID to set this powerup to.
   */
  void setPowerup(int newPowerup) {
    this.powerupID = newPowerup;
  }
}
