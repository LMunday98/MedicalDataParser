import java.util.ArrayList;

public class PatientData {

  // encounterid, grading_date, photograph_date, grading_type
  // left_m, right_m, left_r, right_r, le_photocoagulation, re_photogoagulation
  // disease, le_quality, re_quality, outcome, risk

  private ArrayList<String> data;

  public PatientData (ArrayList<String> _data) {
    this.data = _data;
  }

  public ArrayList<String> getDataCluster() {
    return data;
  }
}
