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

  public void parse(ArrayList<Encounter> encounters) {
    parseInfo();
    parseData(encounters);
  }

  private void parseInfo() {
    for (int i = 0; i < info_index; i++) {
      patientInfo.add(patient_raw_data[i]);
    }
  }

  private void parseData(ArrayList<Encounter> encounters) {
    int indexCount = info_index;
    int patientMax = patient_raw_data.length;

    ArrayList<DataCluster> patientClusters = new ArrayList<DataCluster>();

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
      newCluster.parseDate(comparison_index);
      patientData.add(newCluster);
      indexCount = indexCount + raw_header_indexes.get(i);

      DataCluster patientCluster = new DataCluster(i, parsedData);
      patientClusters.add(patientCluster);
    }
    parseGradings(encounters, patientClusters);
  }

  private void parseGradings(ArrayList<Encounter> encounters, ArrayList<DataCluster> patientClusters) {
    //Collections.reverse(patientClusters);
    int iterationCounter = 0;
    for(DataCluster cluster : patientClusters) {
      ArrayList<String> parsedData = cluster.getData();
      String descCodes[] = getDescCodes(parsedData);

      countGradings(iterationCounter, encounters, descCodes);
      iterationCounter++;
    }
  }

  private String[] getDescCodes(ArrayList<String> parsedData) {
    String descCodes[] = new String[4];
    for (int i = 0; i < 4; i++) {
      String descCode = parsedData.get(i+4);
      System.out.println(descCode + " ");
      if (descCode.equals("")) { if (i < 2) { descCode = "M0"; } else { descCode = "R0"; }       }
      descCodes[i] = descCode;
    }
    return descCodes;
  }

  private void countGradings(int encounterIteration, ArrayList<Encounter> encounters, String descCodes[]) {
    int gradingIndex = 0;
    Encounter encounter = encounters.get(encounterIteration);

    for (String descCode : descCodes) {
      int descIndex = getDescIndex(descCode);

      Grading grading = encounter.getGradings().get(gradingIndex);
      GradingDesc desc = grading.getDesc().get(descIndex);

      desc.incDescCount();
      gradingIndex++;
    }
  }

  private int getDescIndex(String descCode) {
    if (descCode.length() == 2) {
      String code = descCode.substring(1);
      return Integer.parseInt(code);
    } else {
      switch (descCode) {
        case "R3A":
          return 4;
        case "R3S":
          return 5;
        default:
          return -1;
      }
    } 
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
