import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PatientData implements Comparable<PatientData> {

  // encounterid, grading_date, photograph_date, grading_type
  // left_m, right_m, left_r, right_r, le_photocoagulation, re_photogoagulation
  // disease, le_quality, re_quality, outcome, risk

  private ArrayList<String> data;
  private Date comparisonDate;

  public void setData(ArrayList<String> _data) {
    this.data = _data;
  }

  public void parseDate(int _comparisonIndex) {
    try {
      comparisonDate = new SimpleDateFormat("dd/MM/yyyy").parse(data.get(_comparisonIndex));
    }
    catch (Exception e) {
     System.out.println(e);
    }
  }

  public ArrayList<String> getDataCluster() {
    return data;
  }

  public Date getDateTime() {
    return comparisonDate;
  }

  @Override
  public int compareTo(PatientData o) {
    return getDateTime().compareTo(o.getDateTime());
  }
}
