import java.util.ArrayList;

public class Patient {

  // enum, sex, dob, ed_date, ld. gp_num, ethnicity

  private String[] patient_raw_data;
  private int info_index;
  private int max_index;
  private ArrayList<Integer> raw_header_indexes;

  private ArrayList<String> patientInfo;
  private ArrayList<PatientData> patientData;

  public Patient(String[] _patient_raw_row, int _info_index, int _max_index, ArrayList<Integer> _raw_header_indexes) {
    this.patient_raw_data = _patient_raw_row;
    this.info_index = _info_index;
    this.max_index = _max_index;
    this.raw_header_indexes = _raw_header_indexes;

    patientInfo = new ArrayList<String>();
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
    for (int i = 1; i < raw_header_indexes.size(); i++) {
      System.out.println("Index count: " + indexCount);

      int lowerIndex = indexCount;
      int upperIndex = indexCount + raw_header_indexes.get(i) - 1;
      System.out.println("Cluster index range: " + lowerIndex + " - " + upperIndex);
      indexCount = upperIndex + 1;
    }
  }

  public void sortData() {

  }

  public ArrayList<String> getPatientData() {
    ArrayList<String> finalDataArray = new ArrayList<String>();

    for (String info : patientInfo) {
      finalDataArray.add(info);
    }
/*
    for (PatientData dataCluster : patientData) {
      ArrayList<String> cluster = dataCluster.getDataCluster();
      for (String sortedData : cluster) {
        finalDataArray.add(sortedData);
      }
    }
*/
    return finalDataArray;
  }

}
