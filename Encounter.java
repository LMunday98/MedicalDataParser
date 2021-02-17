public class Encounter {

    private Grading gradings[];

    public Encounter() {
        gradings = new Grading[4];
        createGrading(0, "Left_M");
        createGrading(1, "Right_M");
        createGrading(2, "Left_R");
        createGrading(3, "Right_R");
    }

    private void createGrading(int index, String gradingName) {
        gradings[index] = new Grading(gradingName);
    }
}