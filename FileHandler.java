import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

  private String start_col;
  private String sort_col;
  private String file_path;

  private String line;
  private String splitBy;

  private boolean col_headers;

  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = _file_name;

    this.line = "";
    this.splitBy = ",";

    this.col_headers = false;
   }

   public void ParseFile() {
     try   {
      BufferedReader br = new BufferedReader(new FileReader(file_path));
      while ((line = br.readLine()) != null) {
      String[] row = line.split(splitBy);    // use comma as separator
        ParsePatient(row);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void ParsePatient(String[] patient_row) {
     for (String data : row) {

     }
   }
}
