package pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryPage {

    private List<String[]> entries;

    public SummaryPage(List<String[]> entries) {
        this.entries = entries;
    }

    public int getGood() {
        return (int) entries.stream()
                .filter(e -> e[2] != null && e[2].equalsIgnoreCase("Good"))
                .count();
    }

    public int getBad() {
        return (int) entries.stream()
                .filter(e -> e[2] != null && e[2].equalsIgnoreCase("Bad"))
                .count();
    }

    public int getTotal() {
        return entries.size();
    }

  
    public int getRecurring() {

        Map<String, Integer> frequency = new HashMap<>();

        for (String[] entry : entries) {

            String dreamName = entry[0];

            frequency.put(dreamName,
                    frequency.getOrDefault(dreamName, 0) + 1);
        }

        int recurring = 0;

        for (int count : frequency.values()) {
            if (count > 1) {
                recurring++;
            }
        }

        return recurring;
    }
}