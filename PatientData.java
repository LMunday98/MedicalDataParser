import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class PatientData implements Comparable<PatientData> {

  // encounterid, grading_date, photograph_date, grading_type
  // left_m, right_m, left_r, right_r, le_photocoagulation, re_photogoagulation
  // disease, le_quality, re_quality, outcome, risk

  private ArrayList<String> data;
  private Date comparisonDate;
  private String stringDate;

  public void setData(ArrayList<String> _data) {
    this.data = _data;
  }

  public void parseDate(int _comparisonIndex) {
    try {
      stringDate = data.get(_comparisonIndex);
      comparisonDate = new SimpleDateFormat("MM/dd/yyyy").parse(stringDate);
    }
    catch (Exception e) {
     System.out.println(e);
    }
  }

  public ArrayList<String> getDataCluster() {
    return data;
  }

  public String getStringDate() {
    return stringDate;
  }

  public Date getDateTime() {
    return comparisonDate;
  }

  @Override
    public int compareTo(PatientData o) {
      if (getDateTime() == null || o.getDateTime() == null)
        return 0;
      return getDateTime().compareTo(o.getDateTime());
    }
}
