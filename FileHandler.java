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
  private int clusterCount;
  private int createCount;

  private String start_col;
  private String sort_col;
  private String file_path;

  private String line;
  private String splitBy;

  private boolean got_headers;
  private Headers headers;
  private ArrayList<String> col_headers;

  private ArrayList<Patient> patients;
  private Encounter freqEncounters[];
  


  public FileHandler(String _start_col, String _sort_col, String _file_name) {
    this.consecutiveYears = 3;
    this.clusterCount = 0;
    this.createCount = 0;

    this.start_col = _start_col;
    this.sort_col = _sort_col;
    this.file_path = "data/" + _file_name;

    this.line = "";
    this.splitBy = ",";

    this.got_headers = false;
    this.patients = new ArrayList<Patient>();
    this.freqEncounters = new Encounter[7];
    setupFreqEncounters();
  }

  public void setupFreqEncounters() {
    for (int i = 0; i < 7; i++) {
      freqEncounters[i] = new Encounter();
    }
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
    clusterCount = 0;
    ArrayList<PatientData> patientClusters = patient.getPatientClusters();
    for (PatientData cluster : patientClusters) {
      Encounter patientEncounter = cluster.getEncounter();
      ArrayList<Grading> patientClusterGradings = patientEncounter.getGradings();
      for (Grading patientGrading : patientClusterGradings) {
        String patientGradingName = patientGrading.getGradingName();
        String patientGradingValue = patientGrading.getGradingValue();
        Encounter freqEncounter = freqEncounters[clusterCount];

        if (!freqEncounter.checkGradingExists(patientGradingName, patientGradingValue)) {

          System.out.println(" False");
          //System.out.println(createCount + " Create: (n-" + clusterCount + ") " + patientGradingName + " " + patientGradingValue);
          createFreqGrad(patientGradingName, patientGradingValue);
          createCount++;
        } 
      }
      clusterCount++;
    }

    clusterCount = 0;
    for (Encounter freqEncounter : freqEncounters) {
      freqEncounter.genCounts();
      clusterCount++;
    }
   }

   public void createFreqGrad(String patientGradingName, String patientGradingValue) {
    Grading newGrading = new Grading(patientGradingName, patientGradingValue);
    newGrading.incGradingCount();
    freqEncounters[clusterCount].addGrading(newGrading);
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

   public ArrayList<String> genFreqHeaders(int encounterCount) {
    ArrayList<String> freqHeaders = new ArrayList<String>();
    String freqHeader = "";
    Boolean lr = false;
    int lrCount = 0;
      for (int n = 0; n < (encounterCount - 1); n++) {
        lrCount = 0;
        for (int index = 0; index < 8; index++) {
          // (n-0)
          freqHeader += "(n-" + n + ")_";
          // Left / Right
            if (lrCount == 2) {
            lrCount -= 2;
            lr = !lr;
          }
          if (lr) { freqHeader += "Left_"; } else { freqHeader += "Right_"; }
          // M / R
          if (index < 4) { freqHeader += "M_"; } else { freqHeader += "R_"; }
          // Value / Count
          if (index % 2 == 0) { freqHeader += "Value"; } else { freqHeader += "Count"; }

          freqHeaders.add(freqHeader);
          freqHeader = "";
          lrCount++;
        }
      }
      return freqHeaders;
   }

   public void writeFrequency(String name) {
    StringBuilder sb = new StringBuilder();

    ArrayList<String> freqHeaders = genFreqHeaders(7);
    buildString(sb, freqHeaders);

    for (Encounter freqEncounter : freqEncounters) {
      buildString(sb, freqEncounter.getGradingCount());
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
