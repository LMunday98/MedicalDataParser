import java.util.ArrayList;

public class Patient {

  // enum, sex, dob, ed_date, ld. gp_num, ethnicity

  private String[] patient_raw_data;
  private ArrayList<String> patientInfo;
  private ArrayList<PatientData> patientData;

  public Patient(String[] _patient_raw_row) {
    this.patient_raw_data = _patient_raw_row;
  }

  public void ParseData() {

  }

  public void SortData() {

  }

}
