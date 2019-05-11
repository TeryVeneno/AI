package thoughtpad.input;

import java.util.ArrayList;
import java.util.Arrays;

public class StringInput {

  private int key;
  public static char[] alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  public static Character[] alphabetical = convert_char(alph);
  public static ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList(alphabetical));

  public StringInput (int k) {
    k = key;
  }

  public char get_letter () {return alphabetical[key];}
  public int get_key () {return key;}

  private static Character[] convert_char (char[] a) {
    Character[] list = new Character[a.length];
    for (int c = 0; c < a.length; c++) {
      list[c] = Character.valueOf(a[c]);
    }
    return list;
  }
}
