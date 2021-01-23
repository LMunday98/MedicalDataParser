public class Main {
 public static void main(String[] args) {
   FileHandler fh = new FileHandler("Encounterid", "Photograph_Date", "data.csv");
   fh.parseFile();
   fh.writeFile("parsed/Parsed");

   fh.analysis(3);
   fh.writeFile("analysis/Analysis");
 }
}
