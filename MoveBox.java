import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MoveBox{
  static JFrame frame;

  public static void main(String[] args){
    frame = new JFrame("Moving Box");
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }


}
