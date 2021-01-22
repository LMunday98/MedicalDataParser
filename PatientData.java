public class PatientData {

  // encounterid, grading_date, photograph_date, grading_type
  // left_m, right_m, left_r, right_r, le_photocoagulation, re_photogoagulation
  // disease, le_quality, re_quality, outcome, risk

  private String[] data;

  public PatientData (String[] _data) {
    this.data = _data;
  }

  public String[] getData() {
    return data;
  }
}
