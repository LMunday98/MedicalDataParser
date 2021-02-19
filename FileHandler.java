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
  private int consecutiveYears;

  private String start_col;
  private String sort_col;
  private String file_path;

  private String line;
  private String splitBy;

  private boolean got_headers;
  private Headers headers;
  private ArrayList<String> col_headers;

  private ArrayList<Patient> patients;
  private ArrayList<Encounter> encounters;
  
  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.consecutiveYears = 3;

    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = "data/" + _file_name;

    this.line = "";
    this.splitBy = ",";

    this.got_headers = false;
    this.patients = new ArrayList<Patient>();
    this.encounters = new ArrayList<Encounter>();
    genFreqHeaders(7);
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

   public void analysis() {
    ArrayList<Patient> consecPatients = new ArrayList<Patient>();
    int patientCount = 0;
     for (Patient patient : patients) {
       Boolean isConsec = patient.checkConsecutive(consecutiveYears);
       if (isConsec) {
         consecPatients.add(patient);
        }
       patientCount++;
       if (patientCount == -1) {
         break;
       }
     }

     patients = consecPatients;
   }

   public void genFreqHeaders(int encounterCount) {
    String freqHeader = "";
    Boolean lr = false;
    int lrCount = 0;
      for (int n = 0; n < (encounterCount); n++) {
        ArrayList<String> headers = new ArrayList<>();
        lrCount = 0;
        for (int index = 0; index < 8; index++) {
          // Left / Right
            if (lrCount == 2) {
            lrCount -= 2;
            lr = !lr;
          }
          if (lr) { freqHeader += "Right "; } else { freqHeader += "Left "; }
          // M / R
          if (index < 4) { freqHeader += "M "; } else { freqHeader += "R "; }
          // Value / Count
          if (index % 2 == 0) { freqHeader += "Value"; } else { freqHeader += "Count"; }

          headers.add(freqHeader);
          
          freqHeader = "";
          lrCount++;
        }
        Encounter newEncounter = new Encounter();
        newEncounter.setHeadings(headers);
        encounters.add(newEncounter);
      }
   }

   public void writeFrequency(String name) {
    StringBuilder sb = new StringBuilder();

   int iteration = 0;
    for (Encounter encounter : encounters) {
      ArrayList<String> iterationHeadings = encounter.getHeaders();
      sb.append("(n-" + iteration + ")\n");
      buildString(sb, iterationHeadings);

      for (int gradingLine = 0; gradingLine < 6; gradingLine++) {
        ArrayList<Grading> gradings = encounter.getGradings();
        ArrayList<String> gradingLineData = new ArrayList<>();
        for (Grading grading : gradings) {
          ArrayList<GradingDesc> gradingDescs = grading.getDesc();

          GradingDesc currentDesc = gradingDescs.get(gradingLine);

          String descCode = currentDesc.getDescCode();
          String descCount = Integer.toString(currentDesc.getDescCount());

          if (descCode.isEmpty()) {
            descCount = "";
          }
          
          gradingLineData.add(descCode);
          gradingLineData.add(descCount);
        }
        buildString(sb, gradingLineData);
      }
      iteration++;
    }
    
    
    writeFile(sb, getFileName(name));
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
