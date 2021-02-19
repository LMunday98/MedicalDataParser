import java.util.ArrayList;

public class Grading {

    private String gradingName;
    private ArrayList<GradingDesc> gradingDescs;

    public Grading(String _gradingName) {
        this.gradingName = _gradingName;
        this.gradingDescs = new ArrayList<GradingDesc>();
    }

    public void createDesc(String descCode) {
        GradingDesc newDesc = new GradingDesc(descCode);
        gradingDescs.add(newDesc);
    }

    public ArrayList<GradingDesc> getDesc() {
        return gradingDescs;
    }
}