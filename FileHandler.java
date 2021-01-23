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
  private Headers headers;
  private ArrayList<String> col_headers;

  private ArrayList<Patient> patients;

  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = "data/" + _file_name;

    this.line = "";
    this.splitBy = ",";

    this.got_headers = false;
    this.patients = new ArrayList<Patient>();
   }

   public void ParseFile() {
     try {
      BufferedReader br = new BufferedReader(new FileReader(file_path));
      while ((line = br.readLine()) != null) {
        String[] row = line.split(splitBy);
        ParsePatient(row);
      }
      System.out.println();
    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void ParseHeaders(String[] row) {
     headers = new Headers(start_col, row);
     headers.parseHeaders();
     col_headers = headers.getHeaders();
   }

   private void ParsePatient(String[] row) {
     if (got_headers) {
       Patient patient = new Patient(row, headers.getInfoIndex(), headers.getmaxIndex(), headers.getRawHeaderIndexes());
       patient.parsePatient();
       patient.sortData();
       patients.add(patient);
     } else {
       ParseHeaders(row);
       got_headers = true;
     }
   }

   public void WriteFile() {
     try (PrintWriter writer = new PrintWriter(new File("data/test.csv"))) {

      StringBuilder sb = new StringBuilder();

      BuildString(sb, col_headers);

      for (Patient patient : patients) {
        BuildString(sb, patient.getPatientData());
      }

      writer.write(sb.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void BuildString(StringBuilder sb, ArrayList<String> data_array) {
     String prefix = "";
     for (String data : data_array) {
      sb.append(prefix);
      prefix = ",";
      sb.append(data);
     }
     sb.append('\n');
   }
}
