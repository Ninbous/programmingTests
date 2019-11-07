import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<String, ISearchable> searchers = new HashMap<String, ISearchable>();
        searchers.put("Yandex", new Yandex());
        searchers.put("Google", new Google());

        //Поисковые слова
        String[] searchStrings = {
                "Казань",
        };

        File file = new File("test.csv");

        for (Map.Entry<String, ISearchable> searcher : searchers.entrySet()) {
            String baseUrl = searcher.getValue().getBaseUrl();
            String searchResultLinks = searcher.getValue().getSearchResultLinks();

            System.out.println(searcher.getKey());

            for (int i = 0; i < searchStrings.length; i++){
                List<SearchItem> searchItems = new ArrayList<SearchItem>();
                Document doc = Jsoup.connect(baseUrl + URLEncoder.encode(searchStrings[i], "UTF-8"))
                        .userAgent("Mozilla")
                        .timeout(5000)
                        .get();
                Elements elements = doc.select(searchResultLinks);
                String link;
                int j = 1;
                for (Element element : elements) {

                    //    link = element.ownText(); текст внутри элемента
                    link = element.attr("href");  //текст внутри аттрибута

                    searchItems.add(new SearchItem(link));

                    System.out.println(Integer.toString(j++));
                    System.out.println(link);
                }

                try {
                    // create FileWriter object with file as parameter
                    FileWriter outputfile = new FileWriter(file);

                    // create CSVWriter object filewriter object as parameter
                    CSVWriter writer = new CSVWriter(outputfile);

                    // adding header to csv
                    String[] header = {"Ссылки с поисковых страниц:"};
                    writer.writeNext(header);

                    // add data to csv
                    for (SearchItem searchItem : searchItems) {
                        String[] data = {searchItem.getHref()};
                        writer.writeNext(data);
                    }

                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Done");
    }
}

