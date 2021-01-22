import java.util.ArrayList;

public class Patient {

  // enum, sex, dob, ed_date, ld. gp_num, ethnicity

  private String start_col;
  private String sort_col;

  private String[] patient_raw_data;
  private ArrayList<String> patientInfo;
  private ArrayList<PatientData> patientData;

  public Patient(String _start_col, String _sort_col, String[] _patient_raw_row) {
    this.patient_raw_data = _patient_raw_row;
    this.start_col = _start_col;
    this.sort_col = _sort_col;
  }

  public void ParseData() {

  }

  public void SortData() {

  }

}
