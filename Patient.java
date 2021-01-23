import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
    System.out.println();
    int indexCount = info_index;

    int patientMax = patient_raw_data.length;
    //System.out.print("Patient max: " + patientMax);

    for (int i = 1; i < raw_header_indexes.size(); i++) {

      ArrayList<String> parsedData = new ArrayList<String>();

      int lower = indexCount;
      int upper = indexCount + raw_header_indexes.get(i);

      if (upper > patientMax) {
        break;
      }

      //System.out.print(" (Lower: " + lower + ", Upper: " + upper + ")");

      for (int j = lower; j < upper; j++) {
        //System.out.println(patient_raw_data[j]);
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
    }
  }

  public void sortData() {
    System.out.println("Patient row number: " + patientRow);
    Collections.sort(patientData);
    for (PatientData pd : patientData) {
      System.out.println(pd.getDateTime());
    }
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

}
