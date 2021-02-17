import java.util.HashMap;

public class Grading {

    private String gradingName;
    private String gradingValue;
    private int gradingCount;

    public Grading(String _gradingName, String _gradingValue) {
        this.gradingName = _gradingName;
        this.gradingValue = _gradingValue;
        this.gradingCount = 0;
    }

    public void incGradingCount() {
        gradingCount++;
    }

    public String getGradingName() {
        return gradingName;
    }

    public String getGradingValue() {
        return gradingValue;
    }

    public int getGradingCount() {
        return gradingCount;
    }
    
}