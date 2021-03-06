public class Main {
  public static void main(String[] args) {

    long startTime = System.currentTimeMillis();

    FileHandler fh = new FileHandler("Encounterid", "Photograph_Date", "data.csv");
    fh.parseFile();
    fh.writePatients("parsed/Parsed");

    fh.analysis();
    fh.writePatients("analysis/Analysis");
    fh.writeFrequency("frequency/Frequency");

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("\nExecution Time: " + elapsedTime + "(ms)\n");

  }
}
