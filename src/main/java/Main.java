import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<String, ISearchable> searchers = new HashMap<String, ISearchable>();
        searchers.put("Yandex", new Yandex());
        searchers.put("Google", new Google());

        //Поисковые слова
        String[] keywords = {
                "Казань",
        };

        Parser parser = new Parser();
        List<SearchItem> searchItems = parser.parse(keywords, searchers);

        //Реализация консольного принтера
        Printer printerConsole = ()->{
            for (SearchItem item : searchItems) {
                System.out.println(item.getHref());
            }
        };

        //Реализация csv принтера
        Printer printerCsv = ()-> {
            try {
                File file = new File("test.csv");
                FileWriter outputfile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputfile);
                String[] header = {"Ссылки с поисковых страниц:"};
                writer.writeNext(header);

                for (SearchItem searchItem : searchItems) {
                    String[] data = {searchItem.getHref()};
                    writer.writeNext(data);
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        printerConsole.print();
        printerCsv.print();
        System.out.println("Done");
    }
}

