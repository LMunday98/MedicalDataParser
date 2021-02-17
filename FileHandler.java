import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
  
  private int consecutiveYears;

  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = "data/" + _file_name;

    this.line = "";
    this.splitBy = ",";

    this.got_headers = false;
    this.patients = new ArrayList<Patient>();
    this.consecutiveYears = 3;
  }

   public void parseFile() {
     try {
      BufferedReader br = new BufferedReader(new FileReader(file_path));
      int patientRow = 1;
      while ((line = br.readLine()) != null) {
        String[] row = line.split(splitBy);
        parsePatient(patientRow, row);
        patientRow++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private void parseHeaders(String[] row) {
     headers = new Headers(start_col, sort_col, row);
     headers.parseHeaders();
     col_headers = headers.getHeaders();
   }

   private void parsePatient(int patientRow, String[] row) {
     if (got_headers) {
       Patient patient = new Patient(patientRow, row, headers.getInfoIndex(), headers.getmaxIndex(), headers.getComparisonIndex(), headers.getRawHeaderIndexes());
       patient.parsePatient();
       patient.sortData();
       patients.add(patient);
     } else {
       parseHeaders(row);
       got_headers = true;
     }
   }

   public void calcFreq(Patient patient) {
    ArrayList<PatientData> patientClusters = patient.getPatientClusters();
    Collections.reverse(patientClusters);

    for (PatientData cluster : patientClusters) {
      Encounter encounter = cluster.getEncounter();
      Grading gradings[] = encounter.getGradings();
      
      for (Grading grading : gradings) {
        String gradingName = grading.getGradingName();
        String gradingValue = grading.getGradingValue();
      }
    }


   }

   public void calcConsec() {
    
   }

   public void analysis() {
    ArrayList<Patient> consecPatients = new ArrayList<Patient>();

     for (Patient patient : patients) {
       Boolean isConsec = patient.checkConsecutive(consecutiveYears);
       if (isConsec) {
         consecPatients.add(patient);
        }
       calcFreq(patient);
     }

     patients = consecPatients;
   }

   public void writePatients(String name) {
    StringBuilder sb = new StringBuilder();
    buildString(sb, col_headers);

    for (Patient patient : patients) {
      buildString(sb, patient.getPatientData());
    }

    writeFile(sb, getFileName(name));
   }

   public String getFileName(String name) {
    return "data/" + name + " " + getDateTime() + ".csv";
   }

   public void writeFile(StringBuilder sb, String fileName) {
     try (PrintWriter writer = new PrintWriter(new File(fileName))) {
      writer.write(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
   }

   private String getDateTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
   }

   private void buildString(StringBuilder sb, ArrayList<String> data_array) {
     String prefix = "";
     for (String data : data_array) {
      sb.append(prefix);
      prefix = ",";
      sb.append(data);
     }
     sb.append('\n');
   }
}
