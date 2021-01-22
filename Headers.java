import java.util.ArrayList;
import java.util.Collections;

public class Headers {

  private String start_col;
  private String[] raw_headers;
  private ArrayList<String> headers;
  private ArrayList<Integer> headerCountArray;

  private int infoIndex;
  private int maxIndex;

  public Headers (String _start_col, String[] _raw_headers) {
    this.raw_headers = _raw_headers;
    this.start_col = _start_col;

    this.headers = new ArrayList<String>();
    this.headerCountArray = new ArrayList<Integer>();

    this.infoIndex = 0;
    this.maxIndex = 0;
  }

  public void parseHeaders() {

    int headerCounter = 0;

    for (String header : raw_headers) {
      if (header.contains(start_col)) {
        addHeader(headerCounter);
        headerCounter = 0;
      }
      headerCounter++;
      System.out.println("Header: " + header);
    }
    addHeader(headerCounter);
    getHeaderIndexes();
  }

  private void addHeader(int curHeader) {
    System.out.println("Header count: " + curHeader + "\n");
    headerCountArray.add(curHeader);
  }

  private void getHeaderIndexes() {
    Collections.sort(headerCountArray);
    infoIndex = headerCountArray.get(0);
    System.out.println("Info header index: " + infoIndex);

    Collections.reverse(headerCountArray);
    maxIndex = headerCountArray.get(0);
    System.out.println("Max header index: " + maxIndex);
  }

  public ArrayList<String> getHeaders() {
    return headers;
  }
}
