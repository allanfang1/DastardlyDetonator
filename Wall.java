import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Wall
 * Represents an unpassable wall block.
 */
class Wall extends Obstruction {
  private int height=32;
  private int width=32;
  private BufferedImage image;
  
  Wall(int x, int y){
    super(x, y);
    //Try to load the sprite
    try {
      image = ImageIO.read(new File("img/wall.png"));
    } catch(Exception e){
      System.out.println("Error loading img/wall.png");
    }
  }

  public void draw(Graphics g) {
    try {
        g.drawImage(image, this.getX(), this.getY(), null);
    //If it failed to load the sprite
    } catch(Exception e){
        g.setColor(Color.BLUE);
        g.fillRect((int)(this.getX()), (int)(this.getY()), height, width);
    }
  }
}
