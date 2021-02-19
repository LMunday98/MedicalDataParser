import java.util.ArrayList;

public class DataCluster {

    private int iteration;
    private ArrayList<String> parsedData;

    public DataCluster(int _iteration, ArrayList<String> _data) {
        this.iteration = _iteration;
        this.parsedData = _data;
    }

    public int getIteration() {
        return iteration;
    }

    public ArrayList<String> getData() {
        return parsedData;
    }
}