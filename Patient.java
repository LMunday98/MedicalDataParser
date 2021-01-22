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
    //parseData();
  }

  private void parseInfo() {
    for (int i = 0; i < info_index; i++) {
      patientInfo.add(patient_raw_data[i]);
    }
  }

  private void parseData() {

  }

  public void sortData() {

  }

  public ArrayList<String> getPatientData() {
    ArrayList<String> finalDataArray = new ArrayList<String>();

    for (String info : patientInfo) {
      finalDataArray.add(info);
    }
/*
    for () {

    }
*/
    return finalDataArray;
  }

}
