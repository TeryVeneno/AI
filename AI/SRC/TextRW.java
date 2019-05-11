import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class TextRW {

  private static ArrayList<String> lines = new ArrayList<String>(2);

  public static void print_text () {
    for (int i = 0; i < lines.size(); i++)
      System.out.println(lines.get(i));
  }

  public static void write_to_file () {
    Charset utf8 = StandardCharsets.UTF_8;
    ArrayList<String> t_lines = new ArrayList<String>(Arrays.asList("1st line", "2nd line"));

    try {Files.write(Paths.get("file.txt"), t_lines, utf8);
    } catch(Exception e) {e.printStackTrace();}
  }

  public static void read_from_file (String path) {
    Scanner scan = null;
    try { scan = new Scanner(new File(path));
    } catch(Exception e) {e.printStackTrace();}
    while(scan.hasNextLine())
      lines.add(scan.nextLine());
    scan.close();
  }

  public static void main(String[] args) {
    write_to_file();
    read_from_file("file.txt");
    print_text();
  }
}
