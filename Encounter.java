public class Encounter {

    private Grading gradings[];

    public Encounter(String gradingValues[]) {
        gradings = new Grading[4];
        createGrading(0, "Left_M", gradingValues[0]);
        createGrading(1, "Right_M", gradingValues[1]);
        createGrading(2, "Left_R", gradingValues[2]);
        createGrading(3, "Right_R", gradingValues[3]);
    }

    private void createGrading(int index, String gradingName, String gradingValue) {
        gradings[index] = new Grading(gradingName, gradingValue);
    }

    public Grading[] getGradings() {
        return gradings;
    }
}