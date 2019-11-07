public class Google implements ISearchable {


    public String getBaseUrl() {
        //Основной урл
        String baseUrl = "https://www.google.ru/search?q=";
        return baseUrl;
    }

    public String getSearchResultLinks() {
        //Ссылки результатов
        String aSelector = ".r>a";
        return aSelector;
    }
}
