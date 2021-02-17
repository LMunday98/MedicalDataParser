import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.*;

public class Patient {

  // enum, sex, dob, ed_date, ld. gp_num, ethnicity

  private int patientRow;
  private String[] patient_raw_data;
  private int info_index;
  private int max_index;
  private int comparison_index;
  private ArrayList<Integer> raw_header_indexes;

  private ArrayList<String> patientInfo;
  private ArrayList<PatientData> patientData;

  public Patient(int _patientRow, String[] _patient_raw_row, int _info_index, int _max_index, int _comparison_index, ArrayList<Integer> _raw_header_indexes) {
    this.patientRow = _patientRow;
    this.patient_raw_data = _patient_raw_row;
    this.info_index = _info_index;
    this.max_index = _max_index;
    this.comparison_index = _comparison_index - info_index;
    this.raw_header_indexes = _raw_header_indexes;

    patientInfo = new ArrayList<String>();
    patientData = new ArrayList<PatientData>();
  }

  public void parsePatient() {
    parseInfo();
    parseData();
  }

  private void parseInfo() {
    for (int i = 0; i < info_index; i++) {
      patientInfo.add(patient_raw_data[i]);
    }
  }

  private void parseData() {
    int indexCount = info_index;
    int patientMax = patient_raw_data.length;

    for (int i = 1; i < raw_header_indexes.size(); i++) {
      ArrayList<String> parsedData = new ArrayList<String>();
      int lower = indexCount;
      int upper = indexCount + raw_header_indexes.get(i);

      if (upper > patientMax) {
        break;
      }

      for (int j = lower; j < upper; j++) {
        parsedData.add(patient_raw_data[j]);
      }

      if (raw_header_indexes.get(i) != max_index) {
        parsedData.add("NULL");
      }

      PatientData newCluster = new PatientData();
      newCluster.setData(parsedData);
      newCluster.setEncounter(createEncounter(parsedData));
      newCluster.setEncounterId(i);
      newCluster.parseDate(comparison_index);
      patientData.add(newCluster);
      indexCount = indexCount + raw_header_indexes.get(i);

      createEncounter(parsedData);
    }
  }

  private Encounter createEncounter(ArrayList<String> parsedData) {
    String gradingValues[] = new String[4];

    for (int i = 4; i < 8; i++) {
      String gradingValue = parsedData.get(i);
      String gradingString = "";
      if (gradingValue.equals("")) {
        
        if (i <= 5) {
          gradingString += "M";
        } else {
          gradingString += "R";
        }

        gradingString += "0";
      } else {
        gradingString += gradingValue;
      }
      gradingValues[i - 4] = gradingString;
    }
    Encounter newEncounter = new Encounter();
    newEncounter.createPatientGradings(gradingValues);
    return newEncounter;
  }

  public void sortData() {
    Collections.sort(patientData, new Comparator<PatientData>() {
      public int compare(PatientData o1, PatientData o2) {
          return o1.getDateTime().compareTo(o2.getDateTime());
      }
    });
  }

  public boolean checkConsecutive(int consecLimit) {
    ArrayList<Integer> years = getYears();
    int consecYearsCount = 1;

    for (int i = 1; i < years.size(); i++) {
      if (years.get(i) == years.get(i - 1) + 1) {
        consecYearsCount++;

        if (consecYearsCount == consecLimit) {
          return true;
        }

      } else {
        consecYearsCount = 1;
      }
    }
    return false;
  }

  private ArrayList<Integer> getYears() {
    ArrayList<Integer> years = new ArrayList<Integer>();

    for (PatientData cluster : patientData) {
      String date = cluster.getStringDate();
      String[] dateParts = date.split("/");
      String stringYear = dateParts[2];
      int intYear = Integer.parseInt(stringYear);
      years.add(intYear);
    }

    return years;
  }

  public ArrayList<String> getPatientData() {
    ArrayList<String> finalDataArray = new ArrayList<String>();

    for (String info : patientInfo) {
      finalDataArray.add(info);
    }

    for (PatientData dataCluster : patientData) {
      ArrayList<String> cluster = dataCluster.getDataCluster();
      for (String sortedData : cluster) {
        finalDataArray.add(sortedData);
      }
    }

    return finalDataArray;
  }

  public String[] getRawData() {
    return patient_raw_data;
  }

  public ArrayList<PatientData> getPatientClusters() {
    return patientData;
  }

}
