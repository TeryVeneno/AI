package thoughtpad.pad;

import java.awt.*;
import java.util.*;

public class Grid {

  private int r, c, dist_x, dist_y, w, h;
  private ArrayList<Line> lines = new ArrayList<Line>();

  public Grid (int rows, int columns, int width, int height) {
    r = rows; c = columns;
    w = width; h = height;
    calc_lines();
  }

  public void draw (Graphics2D g2d) {
    g2d.setColor(Color.WHITE);
    dist_x = w/c; dist_y = h/r;
    for (int x = 0; x <= w; x += dist_x) {
      g2d.drawLine(x, 0, x, h);
      if (x == dist_x*(c/2)) {
        g2d.drawLine(x+1, 0, x+1, h);
        g2d.drawLine(x-1, 0, x-1, h);
      }
    }
    for (int y = 0; y <= h; y += dist_y) {
      g2d.drawLine(0, y, w, y);
      if (y == dist_y*(r/2)) {
        g2d.drawLine(0, y+1, w, y+1);
        g2d.drawLine(0, y-1, w, y-1);
      }
    }
  }

  public void calc_lines () {
    lines.clear();
    double x_mid = dist_x*(c/2); double y_mid = dist_y*(r/2);
    dist_x = w/c; dist_y = h/r;
    for (int x = 0; x <= w; x += dist_x) {
      Line temp = new Line(x, 0, x, h);
      lines.add(temp);
    }
    for (int y = 0; y <= h; y += dist_y) {
      Line temp = new Line(0, y, w, y);
      lines.add(temp);
    }
  }


  public void set_rows (int rows) {r = rows; calc_lines();}
  public void set_columns (int columns) {c = columns; calc_lines();}
  public ArrayList<Line> get_lines () {return lines;}

  public int get_sector (int x, int y) {
    Rectangle s0 = new Rectangle(0, 0, w/2, h/2); Rectangle s1 = new Rectangle(w/2, 0, w/2, h/2);
    Rectangle s2 = new Rectangle(0, h/2, w/2, h/2); Rectangle s3 = new Rectangle(w/2, h/2, w/2, h/2);
    if (s0.contains(x, y))
      return 0;
    if (s1.contains(x, y))
      return 1;
    if (s2.contains(x, y))
      return 2;
    if (s3.contains(x, y))
      return 3;
    return -1;
  }
}
