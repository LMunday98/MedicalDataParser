import java.util.ArrayList;

public class Encounter {

    private ArrayList<Grading> gradings;
    private ArrayList<String> encounterHeadings;

    public Encounter() {
        gradings = new ArrayList<Grading>();
        encounterHeadings = new ArrayList<String>();
    }

    public void setHeadings(ArrayList<String> _headings) {
        this.encounterHeadings = _headings;
    }

    public ArrayList<String> getHeaders() {
        return encounterHeadings;
    }
}