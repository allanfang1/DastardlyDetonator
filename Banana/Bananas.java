import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Bananas{
  static JButton clickButton;
  static ImageIcon bananaimg;
  static JFrame bFrame;
  static JLabel label;
  
  public static void main(String[] args){
    
    bFrame=new JFrame("Banana!");
    bFrame.setSize(600, 600);
    bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel mainPanel=new JPanel();
    
    clickButton=new JButton("Banana?");
    clickButton.setFont(new Font("Arial", Font.PLAIN, 40));
    clickButton.addActionListener(new clickButtonListener());
    
    resize();
    label=new JLabel(bananaimg, JLabel.CENTER);
   
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(clickButton, BorderLayout.SOUTH);
    mainPanel.add(label, BorderLayout.CENTER);
    bFrame.add(mainPanel);
    
    bFrame.setVisible(true);
  }
  
  static String imgfilename(){
    String name;
    name=("banana"+ (String.valueOf((int)(Math.random()* 6)))+".png");
    return name;
  }
  
  static void resize(){
    bananaimg=new ImageIcon(imgfilename());
    Image image = bananaimg.getImage();
    Image newimg = image.getScaledInstance(400, 400,  java.awt.Image.SCALE_SMOOTH);   
    bananaimg = new ImageIcon(newimg);  
  }
  
  static class clickButtonListener implements ActionListener {  
    public void actionPerformed(ActionEvent event)  {
      resize();
      label.setIcon(bananaimg);
      bFrame.repaint();
    }
  }
}