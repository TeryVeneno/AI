package thoughtpad.pad;

public class Line {

  private int x1, y1, x2, y2;

  public Line (int x1, int y1, int x2, int y2) {
    this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
  }

  public int get_x1 () {return x1;} public int get_x2 () {return x2;}
  public int get_y1 () {return y1;} public int get_y2 () {return y2;}
}
