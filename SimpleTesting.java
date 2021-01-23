import java.util.*;

public class SimpleTesting {
    static class DateItem {
        String datetime;

        DateItem(String date) {
            this.datetime = date;
        }
    }

    static class SortByDate implements Comparator<DateItem> {
        @Override
        public int compare(DateItem a, DateItem b) {
            return a.datetime.compareTo(b.datetime);
        }
    }

    public static void main(String args[]) {
        List<DateItem> dateList = new ArrayList<>();
        dateList.add(new DateItem("2020-03-25"));
        dateList.add(new DateItem("2019-01-27"));
        dateList.add(new DateItem("2020-03-26"));
        dateList.add(new DateItem("2020-02-26"));
        Collections.sort(dateList, new SortByDate());
        dateList.forEach(date -> {
            System.out.println(date.datetime);
        });
    }
}
