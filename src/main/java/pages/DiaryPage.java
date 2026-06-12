package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DiaryPage {

    WebDriver driver;

    By tableRows = By.xpath("//table//tbody//tr");

    public DiaryPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getRows() {

        List<WebElement> rows = driver.findElements(tableRows);

        if (rows.size() < 10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rows = driver.findElements(tableRows);
        }

        return rows;
    }

    public int getRowCount() {
        return getRows().size();
    }

   
    public List<String[]> getAllDreamEntries() {

        List<String[]> entries = new ArrayList<>();

        List<WebElement> rows = getRows();

        for (WebElement row : rows) {

            List<WebElement> cols = row.findElements(By.tagName("td"));

            if (cols.size() < 3) continue; 

            String dreamName = cols.get(0).getText().trim();
            String daysAgo   = cols.get(1).getText().trim();
            String dreamType = cols.get(2).getText().trim(); 

            entries.add(new String[]{dreamName, daysAgo, dreamType});
        }

        return entries;
    }

    public void printRows() {
        System.out.println("ROWS FOUND = " + getRows().size());
        for (WebElement row : getRows()) {
            System.out.println(row.getText());
        }
    }
}