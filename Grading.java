import java.util.HashMap;

public class Grading {

    private String gradingName;
    private String gradingValue;

    public Grading(String _gradingName, String _gradingValue) {
        this.gradingName = _gradingName;
        this.gradingValue = _gradingValue;
    }

    public String getGradingName() {
        return gradingName;
    }

    public String getGradingValue() {
        return gradingValue;
    }
}