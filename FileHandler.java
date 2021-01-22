import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileHandler {

  private String start_col;
  private String sort_col;
  private String file_path;

  private String line;
  private String splitBy;

  private boolean got_headers;
  private ArrayList<String> col_headers;

  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = "data/" + _file_name;

    this.line = "";
    this.splitBy = ",";

    this.got_headers = false;
    this.col_headers = new ArrayList<String>();
   }

   public void ParseFile() {
     try {
      BufferedReader br = new BufferedReader(new FileReader(file_path));
      while ((line = br.readLine()) != null) {
      String[] row = line.split(splitBy);
        ParseHeaders(row);
        ParsePatient(row);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void ParseHeaders(String[] row) {
     if (!got_headers) {
       for (String data : row) {
         col_headers.add(data);
       }
       got_headers = true;
     }
   }

   private void ParsePatient(String[] row) {
     for (String data : row) {

     }
   }

   public void WriteFile() {
     try (PrintWriter writer = new PrintWriter(new File("data/test.csv"))) {

      StringBuilder sb = new StringBuilder();

      BuildString(sb, col_headers);
      BuildString(sb, col_headers);
      writer.write(sb.toString());

      System.out.println("done!");

    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void BuildString(StringBuilder sb, ArrayList<String> data_array) {
     String prefix = "";
     for (String data : data_array) {
      sb.append(prefix);
      prefix = ", ";
      sb.append(data);
     }
     sb.append('\n');
   }

   public void EchoData() {
     for (String data : col_headers) {
       System.out.println(data);
     }
   }
}
