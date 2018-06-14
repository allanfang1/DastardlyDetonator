/** 
 * [StartScreen.java] 
 * Menu of the game
 * June 14 2018 
 */ 

//imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * StartScreen
 * Represents the starting screen of the game.
 */
class StartScreen extends JFrame {

  public static void main(String[] args) {
    new StartScreen();
  }

  static ImageIcon titleImage;
  static JFrame bFrame;

  StartScreen() {

    bFrame=new JFrame("Dastardly Detonator");
    bFrame.setSize(800, 800);
    bFrame.setLocationRelativeTo(null);
    bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel=new JPanel();

    JButton clickButton=new JButton("Start");
    clickButton.setFont(new Font("Arial", Font.PLAIN, 30));
    clickButton.addActionListener(new clickButtonListener());

    resize();
    JLabel label=new JLabel(titleImage, JLabel.CENTER);

    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(clickButton, BorderLayout.CENTER);
    mainPanel.add(label, BorderLayout.CENTER);
    bFrame.add(mainPanel);

    bFrame.setVisible(true);
  }

  /**
   * resize
   * change image size to fit screen
   */
  static void resize() {
    titleImage = new ImageIcon("img/title.png");
    Image image = titleImage.getImage();
    Image newimg = image.getScaledInstance(800, 800,  java.awt.Image.SCALE_SMOOTH);
    titleImage = new ImageIcon(newimg);
  }

  static class clickButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      bFrame.dispose();
      new DastardlyDetonator();
    }
  }
}
