import java.util.ArrayList;

public class Encounter {

    private ArrayList<Grading> gradings;
    private ArrayList<String> gradingCounts;

    public Encounter() {
        gradings = new ArrayList<Grading>();
        gradingCounts = new ArrayList<String>();
    }

    public void createPatientGradings(String gradingValues[]) {
        createGrading("Left_M", gradingValues[0]);
        createGrading("Right_M", gradingValues[1]);
        createGrading("Left_R", gradingValues[2]);
        createGrading("Right_R", gradingValues[3]);
    }

    private void createGrading(String gradingName, String gradingValue) {
        gradings.add(new Grading(gradingName, gradingValue));
    }

    public void addGrading(Grading grading) {
        gradings.add(grading);
    }

    public ArrayList<Grading> getGradings() {
        return gradings;
    }

    public ArrayList<String> getGradingCount() {
        return gradingCounts;
    }

    public boolean checkGradingExists(String patientGradingName, String patientGradingValue) {
        Boolean found = false;
        for (Grading freqGrading : gradings) {
            String freqName = freqGrading.getGradingName();
            String ferqVal = freqGrading.getGradingValue();

            //System.out.println(patientGradingName + " " + freqName + ", " + patientGradingValue + " " + ferqVal);
            if ((patientGradingName.equals(freqName)) && (patientGradingValue.equals(ferqVal))) {
                freqGrading.incGradingCount();
                System.out.println(" True");
                return true;
            }
        }  
        return false; 
    }

    public void genCounts() {
        for (Grading grading : gradings) {
            gradingCounts.add(grading.getGradingValue());
            String count = Integer.toString(grading.getGradingCount());
            gradingCounts.add(count);
        }
    }
}