public class Patient {

  // enum, sex, dob, ed_date, ld. gp_num, ethnicity

  private String[] generalInfo;
  private PatientData[] data;

  public Patient() {
  }

  private String[] getInfo() {
    return generalInfo;
  }

  private PatientData[] getDataArray() {
    return data;
  }

}
