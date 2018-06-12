import java.awt.Graphics;
import java.awt.Color;

class Powerup extends Obstruction {
  /* Powerup list:
   * 0 = speed
   * 1 = max bombs
   * 2 = explosion range
   * 3 = add health
   */

  private int powerupID;

  Powerup(int setPowerup, int gridX, int gridY) {
    super((gridX * 25) + 50, (gridY * 25) + 50);
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
  
  Human usePowerup(Human player) {
    if (this.powerupID == 0) {
      player.addSpeed();
    }
    else if (this.powerupID == 1) {
      player.addBombs();
    }
    else if (this.powerupID == 2) {
      player.addRange();
    }
    else if (this.powerupID == 3) {
      player.addHealth();
    }
    System.out.println(powerupID);
    return player;
  }

  /**
   * setPowerup
   * This method sets the ID of this powerup.
   * @param The ID to set this powerup to.
   */
  void setPowerup(int newPowerup) {
    this.powerupID = newPowerup;
  }
  
  public void draw(Graphics g) {
    g.setColor(Color.YELLOW); //There are many graphics commands that Java can use
    g.fillRect((int)(this.getX()), (int)(this.getY()), 25, 25); //notice the y is a variable that we control from our animate method
  }
  
}
