import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {

    public List<SearchItem> parse(String[] keywords, Map<String, ISearchable> searchers ) {

        List<SearchItem> searchItems = new ArrayList<SearchItem>();

        for (Map.Entry<String, ISearchable> searcher : searchers.entrySet()) {
            String baseUrl = searcher.getValue().getBaseUrl();
            String searchResultLinks = searcher.getValue().getSearchResultLinks();

            for (int i = 0; i < keywords.length; i++){
                Document doc = null;
                try {
                    doc = Jsoup.connect(baseUrl + URLEncoder.encode(keywords[i], "UTF-8"))
                            .userAgent("Mozilla")
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                Elements elements = doc.select(searchResultLinks);
                String link;

                for (Element element : elements) {

                    //link = element.ownText(); текст внутри элемента
                    link = element.attr("href");  //текст внутри аттрибута
                    searchItems.add(new SearchItem(link));
                }
            }
        }

        return searchItems;
    }
}
