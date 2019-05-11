package thoughtpad.pad;
import javax.swing.*;
import java.awt.*;
import thoughtpad.input.*;

public class Pad extends JPanel {
  private static final long serialVersionUID = 42l;

  private Dimension size;
  private Input input = new Input(); private Mouse mouse = new Mouse();
  private Grid grid;

  public Pad (int width, int height) {
    size = new Dimension(width, height);
    setPreferredSize(size);
    addKeyListener(input);
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    setFocusable(true);
    setBackground(Color.BLACK);
    requestFocusInWindow();

    grid = new Grid(96, 96, width, height);
  }

  public void paintComponent (Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    grid.draw(g2d);
  }

  public Input get_input () {return input;} public Mouse get_mouse () {return mouse;}
  public Grid get_grid () {return grid;}



}
