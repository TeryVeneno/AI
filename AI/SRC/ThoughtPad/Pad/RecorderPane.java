package thoughtpad.pad;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.nio.charset.*;
import thoughtpad.input.*;

public class RecorderPane extends JPanel {
  private static final long serialVersionUID = 42l;

  private Dimension size; private ArrayList<Line> lines; private char selection = 'A';
  private ArrayList<String> data_lines = new ArrayList<String>(); private boolean saved = false;
  private String[] block = new String[6]; private Grid grid; private String file_to_be_written = "";
  private Charset utf8 = StandardCharsets.UTF_8; private int counter = 0; private int arcwidth, archeight;
  private boolean recording = false; private Pad pad; private ArrayList<Integer> xs = new ArrayList<Integer>();
  private ArrayList<Integer> ys = new ArrayList<Integer>(); private Robot roro;

  public RecorderPane (int width, int height, Grid g, Pad p) {
    grid = g; pad = p; lines = g.get_lines();
    size = new Dimension(width,height);
    arcwidth = (int)Math.sqrt(width); archeight = (int)Math.sqrt(height);

    setPreferredSize(size);
    setFocusable(false);
    create_t_directories();
  }

  public void update () {
    if (pad.get_input().is_key_pressed(Input.KEY_ENTER)) {
      file_to_be_written = "TrainingData/"+selection+"/"+selection+".txt";
      try {Files.write(Paths.get(file_to_be_written), data_lines, utf8);
      } catch(Exception e) {e.printStackTrace();}
      saved = true;
    }
    int key = pad.get_input().get_key();
    if (key != 0) {
      change_selection(key);
    }
    if (pad.get_input().is_key_pressed(Input.KEY_4)) {
      data_lines.clear(); xs.clear(); ys.clear();
      try {File file = new File("TrainingData/"+selection+"/"+selection+".txt");file.delete();
      } catch(Exception e) {e.printStackTrace();}
    }
    if (pad.get_input().is_key_pressed(Input.KEY_0)) {
      try {playback();} catch(Exception e) {e.printStackTrace();}
    }
    boolean pressed3 = pad.get_input().is_key_pressed(Input.KEY_3);
    if (pressed3 && !recording) {
      data_lines.clear(); xs.clear(); ys.clear();
      recording = true;
    } else if (pressed3) {recording = false;}
    if (recording) {
      if (pad.get_mouse().is_moving()) {
        saved = false;
        int mx = pad.get_mouse().get_x(); int my = pad.get_mouse().get_y();
        String data = mx+" "+my+" "+grid.get_sector(mx, my);
        xs.add(MouseInfo.getPointerInfo().getLocation().x);
        ys.add(MouseInfo.getPointerInfo().getLocation().y);
        int[] ls = line_collision(mx, my);
        for (int s = 0; s < 6; s++)
          data += " "+ls[s];
        data_lines.add(data);
      }
    }
  }

  public void create_t_directories () {
    if (Files.notExists(Paths.get("TrainingData"))) {
      for (int c = 0; c < StringInput.alph.length; c++) {
        File file = new File("TrainingData/"+StringInput.alph[c]);
        file.mkdirs();
      }
    }
  }
  public void change_selection (int key) {
    int k = Input_Codes.keys.indexOf(key);
    if (k != -1) { selection = StringInput.alphabet.get(k);}
  }

  public int[] line_collision (int mx, int my) {
    counter = 0;
    int[] ret = new int[6]; Rectangle m_hit = new Rectangle(mx, my, 11, 18);
    for (int s = 0; s < lines.size(); s++) {
      Line temp = lines.get(s);
      if (m_hit.intersectsLine(temp.get_x1(), temp.get_y1(), temp.get_x2(), temp.get_y2())) {
        ret[counter] = s; counter++;
      }
    }
    return ret;
  }

  public void paintComponent (Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(Color.BLACK);

    g2d.fillRoundRect(0, 0, size.width, size.height, arcwidth, archeight);
    g2d.setColor(Color.WHITE);

    g2d.setFont(new Font("Times New Roman", Font.BOLD, 15));
    g2d.drawString("Display: Screen 2 (Recording)", 0, 15);
    g2d.drawString("Current Selection: "+selection, 0, size.height/3-15);

    g2d.setColor(Color.GREEN);
    if (saved) {
      g2d.drawString("Saved", 0, size.height/3);
    } else {
      g2d.drawString("Not Saved", 0, size.height/3);
    }
    if (recording) {
      g2d.setColor(Color.RED);
      g2d.drawString("Recording", 0, size.height/3+15);
    } else {
      g2d.setColor(Color.BLUE);
      g2d.drawString("Not Recording", 0, size.height/3+15);
    }
  }

  public void playback () throws InterruptedException {
    try {roro = new Robot(getGraphicsConfiguration().getDevice());} catch(Exception e) {e.printStackTrace();}
    for (int s = 0; s < xs.size(); s++) {
      roro.mouseMove(xs.get(s), ys.get(s));
      roro.delay(50);
    }
  }

}
