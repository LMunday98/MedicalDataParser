public class Main {
  public static void main(String[] args) {

    long startTime = System.currentTimeMillis();

    FileHandler fh = new FileHandler("Encounterid", "Photograph_Date", "data.csv");
    fh.parseFile();
    fh.writeFile("parsed/Parsed");

    int numConsecYears = 3;
    fh.analysis(numConsecYears);
    fh.writeFile("analysis/Analysis");

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("\nExecution Time: " + elapsedTime + "(ms)\n");

  }
}
