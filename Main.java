public class Main {
 public static void main(String[] args) {
   FileHandler fh = new FileHandler("Encounterid", "Photograph_Date", "data.csv");
   fh.ParseFile();
   fh.WriteFile();
 }
}
