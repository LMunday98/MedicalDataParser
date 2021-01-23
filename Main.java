public class Main {
 public static void main(String[] args) {
   FileHandler fh = new FileHandler("Encounterid", "Photograph_Date", "data.csv");
   fh.parseFile();
   fh.writeFile("Parsed");

   fh.analysis();
   fh.writeFile("Analysis");
 }
}
