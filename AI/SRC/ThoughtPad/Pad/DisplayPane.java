package thoughtpad.pad;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import thoughtpad.input.*;
import machine_learning.networks.SNetwork;
import java.io.File;

public class DisplayPane extends JPanel {
  private static final long serialVersionUID = 42l;

  private Dimension size; private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(26); private SNetwork translator;
  private int arcwidth, archeight; private Scanner[] scans = new Scanner[26]; private String text="";
  private double[] inputs = new double[45]; private double[][] desire = new double[26][26];
  private int[][][] num_data = new int[26][100][9]; private Input i; private int st = 0;
  private int en = 5; private int cur_letter = 0; private boolean training = false;
  private boolean data_parsed = false;


  public DisplayPane (int width, int height) {
    size = new Dimension(width, height);
    arcwidth = (int)Math.sqrt(width); archeight = (int)Math.sqrt(height);
    i = new Input();

    setPreferredSize(size);
    setFocusable(false);
    format_desired();

    translator = new SNetwork(100, 10, 0.045, desire[0], 26, 45);
  }

  public void format_desired () {
    int[] holder = new int[26];
    for (int s = 0; s < 26; s++) {holder[s] = 1;
      for (int i = 0; i < 26; i++)
        desire[s][i] = holder[i];
      holder[s] = 0;
    }
  }

  public void read_data () {
    for (int s = 0; s < 26; s++) {
      char temp = StringInput.alph[s]; data.add(s, new ArrayList<String>(100));
      try {
        scans[s] = new Scanner(new File("TrainingData/"+temp+"/"+temp+".txt"));
      } catch(Exception e) {e.printStackTrace();}
      while (scans[s].hasNextLine())
        data.get(s).add(scans[s].nextLine());
      scans[s].close();
    }
    parse_data();
  }

  public void format_inputs (int cur_let, int sn, int en) {
    int counter = 0;
    for (int s = sn; s < en; s++) {
      for (int i = 0; i < num_data[cur_let][s].length; i++) {
        inputs[counter*9+i] = num_data[cur_let][s][i];
      }
      counter++;
    }
  }

  public void train (int cycles) {
    for (int c = 0; c < cycles; c++) {
      for (int s = 0; s < 26; s++) {
        translator.change_desire(desire[s]);
        for (int start = 0; start < 95; start++) {
          format_inputs(s, start, start+5);
          translator.practice(inputs);
        }
      }
    }
  }

  public void parse_data () {
    String cur_string=""; ArrayList<Integer> parts = new ArrayList<Integer>();
    for (int s = 0; s < 26; s++) {
      for (int i = 0; i < 100; i++) {
        for (int c = 0; c < data.get(s).get(i).length(); c++) {
          if (data.get(s).get(i).charAt(c) == ' ') {parts.add(Integer.valueOf(cur_string));cur_string = "";}
          else {cur_string = cur_string+data.get(s).get(i).charAt(c);}
        }
        num_data[s][i] = to_int_array(parts); parts.clear();
      }
    }
  }

  public void update () {
    if (training) {
      train(100);
      training = false;
    }
    if (i.is_key_pressed(Input.KEY_SPACE)) {
      text = ""+StringInput.alph[maximum()];
    }
    if (i.is_key_pressed(Input.KEY_ESCAPE)) {
      read_data();
      data_parsed = true;
    }
    if (i.is_key_pressed(Input.KEY_SHIFT)) {
      training = true;
    }
  }

  private int maximum () {
    format_inputs(cur_letter, st, en);
    cur_letter+=1; st+=5; en+=5;
    double[] vals = translator.output(inputs); int pos1=0;
    System.out.println(Arrays.toString(vals));
    for (int s = 1; s < vals.length; s++) {
      if (vals[pos1] < vals[s]) {
        pos1 = s;
      }
    }
    if (cur_letter >= 26)
      cur_letter = 0;
    if (st >= 95)
      st = 0; en = 5;
    return pos1;
  }

  public void set_in (Input i) {this.i = i;}

  public int[] to_int_array (ArrayList<Integer> ins) {
    int[] info = new int[ins.size()];
    for (int s = 0; s < ins.size(); s++) {
      info[s] = ins.get(s);
    }
    return info;
  }

  public void paintComponent (Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(Color.BLACK);

    g2d.fillRoundRect(0, 0, size.width, size.height, arcwidth, archeight);
    g2d.setColor(Color.WHITE);

    g2d.setFont(new Font("Times New Roman", Font.BOLD, 15));
    g2d.drawString("Display: Screen 1 (Testing)", 15, 15);
    g2d.drawString("Expected value: "+StringInput.alph[cur_letter], 0, size.height/2-15);
    g2d.drawString("Predicted Value: "+text, 0, size.height/2);
    g2d.drawString("Training: "+training, 0, size.height/2+15);
    g2d.drawString("Data Parsed: "+data_parsed, 0, size.height/2+30);
  }
}
