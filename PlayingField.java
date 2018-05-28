/**
 * Bomberman Playing Field
 * 
 **/

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;

class PlayingField extends JFrame{
  
  static JPanel gamePanel;
  
  PlayingField(){
    super("Bomberman?");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    this.setVisible(true);
  }
}