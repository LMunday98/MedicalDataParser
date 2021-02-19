import java.util.ArrayList;

public class Encounter {

    private ArrayList<Grading> gradings;
    private ArrayList<String> encounterHeadings;

    public Encounter() {
        gradings = new ArrayList<Grading>();
        encounterHeadings = new ArrayList<String>();

        createGradings();
    }

    private void createGradings() {
        Grading g1 = new Grading("LEFT_M");
        g1.createDesc("M0");
        g1.createDesc("M1");
        g1.createDesc("");
        g1.createDesc("");
        g1.createDesc("");
        g1.createDesc("");
        gradings.add(g1);

        Grading g2 = new Grading("RIGHT_M");
        g2.createDesc("M0");
        g2.createDesc("M1");
        g2.createDesc("");
        g2.createDesc("");
        g2.createDesc("");
        g2.createDesc("");
        gradings.add(g2);

        Grading g3 = new Grading("LEFT_R");
        g3.createDesc("R0");
        g3.createDesc("R1");
        g3.createDesc("R2");
        g3.createDesc("R3");
        g3.createDesc("R3A");
        g3.createDesc("R3S");
        gradings.add(g3);

        Grading g4 = new Grading("LEFT_R");
        g4.createDesc("R0");
        g4.createDesc("R1");
        g4.createDesc("R2");
        g4.createDesc("R3");
        g4.createDesc("R3A");
        g4.createDesc("R3S");
        gradings.add(g4);
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