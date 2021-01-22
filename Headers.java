import java.util.ArrayList;
import java.util.Collections;

public class Headers {

  private String start_col;
  private String[] raw_headers;
  private ArrayList<String> headers;
  private ArrayList<String> formattedHeaders;
  private ArrayList<Integer> headerCountArray;
  private ArrayList<Integer> rawHeaderIndexes;

  private int infoIndex;
  private int maxIndex;

  public Headers (String _start_col, String[] _raw_headers) {
    this.raw_headers = _raw_headers;
    this.start_col = _start_col;

    this.headers = new ArrayList<String>();
    this.formattedHeaders = new ArrayList<String>();
    this.headerCountArray = new ArrayList<Integer>();

    this.infoIndex = 0;
    this.maxIndex = 0;
  }

  public void parseHeaders() {
    calcHeaderIndexes();
    getInfoHeaders();
    formatDataHeaders();
    echoFormattedHeaders();
  }

  private void getInfoHeaders() {
    for (int i = 0; i < infoIndex; i++) {
      headers.add(raw_headers[i]);
    }
  }

  private void formatDataHeaders() {
    System.out.println("Raw header indexes: ");
    for (int headerIndex : rawHeaderIndexes) {
      System.out.print(headerIndex + " ");
    }

    int startDataIndex = 0;
    for (int indexCount : rawHeaderIndexes) {
      if (indexCount != maxIndex) {
        startDataIndex = startDataIndex + indexCount;
      } else {
        System.out.println("\n" + "Start data index: " + startDataIndex);
        break;
      }
    }

    for (int j = 0; j < rawHeaderIndexes.size() - 1; j++) {
      for (int i = startDataIndex; i < startDataIndex + maxIndex; i++ ) {
        String formattedHeader = raw_headers[i].substring(0, raw_headers[i].length() - 1) + Integer.toString(j + 1);
        headers.add(formattedHeader);
      }
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

  private void echoFormattedHeaders() {
    System.out.println("\n" + "Formatted headers: ");
    for (String header : headers) {
      System.out.println(header);
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
