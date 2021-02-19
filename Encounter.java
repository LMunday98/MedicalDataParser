import java.util.ArrayList;

public class Encounter {

    private ArrayList<Grading> gradings;
    private ArrayList<String> encounterHeadings;

    private String gradingTitles[];
    private String descCodesM[];
    private String descCodesR[];

    public Encounter() {
        gradings = new ArrayList<Grading>();
        encounterHeadings = new ArrayList<String>();

        gradingTitles = new String[] {"LEFT_M", "RIGHT_M", "LEFT_R", "RIGHT_R"};
        descCodesM = new String[] {"M0", "M1", "", "", "", ""};
        descCodesR = new String[] {"R0", "R1", "R2",  "R3", "R3A", "R3S"};

        createGradings();
    }

    private void createGradings() {
        for (int i = 0; i < 4; i++) {
            Grading newGrading = new Grading(gradingTitles[i]);
            if (i < 2) { createDescriptions(newGrading, descCodesM); } else { createDescriptions(newGrading, descCodesR); }
            gradings.add(newGrading);
        }
    }

    private void createDescriptions(Grading grading, String[] descCodes) {
        for (String descCode : descCodes) {
            grading.createDesc(descCode);
        }
    }

    public void setHeadings(ArrayList<String> _headings) {
        this.encounterHeadings = _headings;
    }

    public ArrayList<String> getHeaders() {
        return encounterHeadings;
    }

    public ArrayList<Grading> getGradings() {
        return gradings;
    }
}