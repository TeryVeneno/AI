package thoughtpad.pad;

import javax.swing.*;
import java.awt.*;
import thoughtpad.input.Input;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("ThoughtPad");
    JPanel displays = new JPanel(new CardLayout());
    Pad pad = new Pad(600, 600);
    DisplayPane pane = new DisplayPane(200, 600);
    RecorderPane pane2 = new RecorderPane(200, 600, pad.get_grid(), pad);
    Input in = pad.get_input(); pane.set_in(in);
    displays.add(pane, "Screen1"); displays.add(pane2, "Screen2");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(pad, BorderLayout.EAST);
    frame.getContentPane().add(displays, BorderLayout.WEST);
    frame.pack();
    frame.setVisible(true);
    CardLayout cl = (CardLayout) displays.getLayout();
    cl.show(displays, "Screen1");
    while (true) {
      if (in.is_key_pressed(Input.KEY_1)) {
        cl.show(displays, "Screen1");
      } else if (in.is_key_pressed(Input.KEY_2)) {
        cl.show(displays, "Screen2");
      }
      pane.update();
      pane2.update();
      frame.repaint();
      Thread.sleep(17);
    }
  }
}
