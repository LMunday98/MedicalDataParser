import java.util.ArrayList;
import java.util.Collections;

public class Headers {

  private String start_col;
  private String[] raw_headers;
  private ArrayList<String> headers;
  private ArrayList<Integer> headerCountArray;
  private ArrayList<Integer> rawHeaderIndexes;

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
    calcHeaderIndexes();
    getInfoHeaders();
    //getDataHeaders();
    echoHeaders();
  }

  private void getInfoHeaders() {
    for (int i = 0; i < infoIndex; i++) {
      headers.add(raw_headers[i]);
    }
  }

  private void getDataHeaders() {
      for (int i = infoIndex; i < infoIndex - 1; i++) {
        headers.add(raw_headers[i]);
      }
  }

  private void calcHeaderIndexes() {
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

    rawHeaderIndexes = new ArrayList<Integer>(headerCountArray);

    Collections.sort(headerCountArray);
    infoIndex = headerCountArray.get(0);
    System.out.println("Info header index: " + infoIndex);

    Collections.reverse(headerCountArray);
    maxIndex = headerCountArray.get(0);
    System.out.println("Max header index: " + maxIndex + "\n");
  }

  private void echoHeaders() {
    for (String header : headers) {
      System.out.println(header);
    }

    for (int headerIndex : rawHeaderIndexes) {
      System.out.println(headerIndex);
    }
  }

  public ArrayList<String> getHeaders() {
    return headers;
  }

  public int getInfoIndex() {
    return infoIndex;
  }

  public int getmaxIndex() {
    return maxIndex;
  }

  public ArrayList<Integer> getRawHeaderIndexes() {
    return rawHeaderIndexes;
  }
}
