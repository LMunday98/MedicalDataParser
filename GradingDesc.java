public class GradingDesc {

    private String descCode;
    private int count;

    public GradingDesc(String _descCode) {
        this.descCode = _descCode;
        this.count = 0;
    }

    public String getDescCode() {
        return descCode;
    }

    public int getDescCount() {
        return count;
    }
}