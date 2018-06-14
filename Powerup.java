/** 
 * [Powerup.java] 
 * Changes the stats of player
 * June 14 2018 
 */ 

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class Powerup extends Obstruction {
  /* Powerup list:
   * 0 = speed
   * 1 = max bombs
   * 2 = explosion range
   */

  private int powerupID;
  private BufferedImage image;

  Powerup(int setPowerup, int gridX, int gridY) {
    super((gridX * 32) + 64, (gridY * 32) + 64);
    this.powerupID = setPowerup;
    try {
      image = ImageIO.read(new File("img/powerup" + this.powerupID + ".png"));
    } catch(Exception e){
      System.out.println("Error loading img/powerup" + this.powerupID + ".png");
    }
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
   * usePowerup
   * This method returns this powerup's ID.
   * @param the targeted player of powerup
   * @return the player with changed stats
   */
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
    try {
        g.drawImage(image, this.getX(), this.getY(), null);
    //If it failed to load the sprite
    } catch(Exception e) {
        g.setColor(Color.YELLOW);
        g.fillRect((int)(this.getX()), (int)(this.getY()), height, width);
    }
  }
}
